package pagether.domain.book.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import pagether.domain.book.domain.Book;
import pagether.domain.book.dto.BookSaveDTO;
import pagether.domain.book.dto.req.AddBookRequest;
import pagether.domain.book.dto.res.BookDetailResponse;
import pagether.domain.book.dto.res.BookResponse;
import pagether.domain.book.dto.res.BookSearchResponse;
import pagether.domain.book.exception.BookNotFoundException;
import pagether.domain.book.repository.BookRepository;
import pagether.domain.category.domain.Category;
import pagether.domain.category.exception.CategoryNotFoundException;
import pagether.domain.category.repository.CategoryRepository;
import pagether.domain.news.domain.News;
import pagether.domain.news.dto.req.AddNewsRequest;
import pagether.domain.news.dto.res.NewsResponse;
import pagether.domain.news.dto.res.SeparatedNewsResponse;
import pagether.domain.news.exception.NewsNotFoundException;
import pagether.domain.news.repository.NewsRepository;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@EnableAsync
public class BookService {

    @Value("${aladin.ttb-key}")
    private String TTB_KEY;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    @Async
    public CompletableFuture<Void> save(AddBookRequest request) {
        Category category = categoryRepository.findById(1L).orElseThrow(CategoryNotFoundException::new);
        Book book = Book.builder()
                .isbn(request.getIsbn())
                .title(request.getTitle())
                .author(request.getAuthor())
                .publisher(request.getPublisher())
                .pageCount(request.getPageCount())
                .coverImgName(request.getCoverImgName())
                .category(category)
                .description(request.getDescription())
                .createdAt(LocalDateTime.now())
                .build();
        bookRepository.save(book);
        return CompletableFuture.completedFuture(null);
    }

    private String makeSearchUrl(String keyword, Long page){
        return "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey="+TTB_KEY+"&Query="+keyword+"&QueryType=Title&MaxResults=10&start="+page+"&SearchTarget=Book&output=js&Version=20131101";
    }

    private String makeLookUpUrl(String isbn){
        return "https://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey="+TTB_KEY+"&itemIdType=ISBN&ItemId="+isbn+"&output=js&Version=20131101";
    }

    public List<BookSearchResponse> searchFromAladin(String keyword) {
        List<BookSearchResponse> responses = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Map> resultMap = restTemplate.exchange(makeSearchUrl(keyword, 1L), HttpMethod.GET, entity, Map.class);
        Map<String, Object> body = resultMap.getBody();

        if (body != null) {
            List<Map<String, Object>> items = (List<Map<String, Object>>) body.get("item");

            for (Map<String, Object> itemMap : items) {
                String isbn = itemMap.get("isbn13").toString();
                String title = itemMap.get("title").toString();
                String bookCoverImgName = replaceCoverUrl(itemMap.get("cover").toString());
                String author = itemMap.get("author").toString();
                String publisher = itemMap.get("publisher").toString();
                BookSearchResponse response = BookSearchResponse.builder()
                        .isbn(isbn)
                        .title(title)
                        .bookCoverImgName(bookCoverImgName)
                        .author(author)
                        .publisher(publisher)
                        .build();
                responses.add(response);
            }
        }
        return responses;
    }

    public BookDetailResponse get(String isbn) {
        if (bookRepository.existsByIsbn(isbn)){
            Book book = bookRepository.findByIsbn(isbn).orElseThrow(BookNotFoundException::new);
            return new BookDetailResponse(book);
        }
        return lookUpFromAladin(isbn);
    }

    public BookDetailResponse lookUpFromAladin(String isbn) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Map> resultMap = restTemplate.exchange(makeLookUpUrl(isbn), HttpMethod.GET, entity, Map.class);
        Map<String, Object> body = resultMap.getBody();
        System.out.println(body);
        if (body != null) {
            List<Map<String, Object>> items = (List<Map<String, Object>>) body.get("item");
            Map<String, Object> item = items.get(0);
            String title = item.get("title").toString();
            String bookCoverImgName = replaceCoverUrl(item.get("cover").toString());
            String author = item.get("author").toString();
            String publisher = item.get("publisher").toString();
            String description = item.get("description").toString();
            Long categoryId = Long.parseLong(item.get("categoryId").toString());
            Map<String, Object> subInfo = (Map<String, Object>) item.get("subInfo");
            Long itemPage = Long.parseLong(subInfo.get("itemPage").toString());

            AddBookRequest request = AddBookRequest.builder()
                    .isbn(isbn)
                    .title(title)
                    .author(author)
                    .publisher(publisher)
                    .pageCount(itemPage)
                    .categoryId(categoryId)
                    .description(description)
                    .coverImgName(bookCoverImgName)
                    .build();
            this.save(request);

            BookDetailResponse response = BookDetailResponse.builder()
                    .isbn(isbn)
                    .title(title)
                    .bookCoverImgName(bookCoverImgName)
                    .author(author)
                    .pageCount(itemPage)
                    .description(description)
                    .publisher(publisher)
                    .build();
            return response;
        }
        throw new BookNotFoundException();
    }

    public static String replaceCoverUrl(String original) {
        return original.replace("coversum", "cover500");
    }

}