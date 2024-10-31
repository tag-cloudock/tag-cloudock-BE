package gachonherald.domain.book.application;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import gachonherald.domain.book.domain.Book;
import gachonherald.domain.book.dto.req.AddBookRequest;
import gachonherald.domain.book.dto.res.BookDetailResponse;
import gachonherald.domain.book.dto.res.BookResponse;
import gachonherald.domain.book.dto.res.BookSearchResponse;
import gachonherald.domain.book.exception.BookNotFoundException;
import gachonherald.domain.book.repository.BookRepository;
import gachonherald.domain.category.domain.Category;
import gachonherald.domain.category.exception.CategoryNotFoundException;
import gachonherald.domain.category.repository.CategoryRepository;
import gachonherald.domain.image.application.ImageService;
import gachonherald.global.config.exception.LastPageReachedException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@EnableAsync
public class BookService {

    @Value("${aladin.ttb-key}")
    private String TTB_KEY;
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final ImageService imageService;
    public static final int PAGE_SIZE = 10;

    public BookResponse save(AddBookRequest request, MultipartFile pic) {
        Category category = categoryRepository.findById(1L).orElseThrow(CategoryNotFoundException::new);
        String imgName = imageService.save(pic, true);
        Book book = Book.builder()
                .isbn(request.getIsbn())
                .title(request.getTitle())
                .pageCount(request.getPageCount())
                .isBookAddedDirectly(true)
                .createdAt(LocalDateTime.now())
                .category(category)
                .coverImgName(imgName)
                .weight(request.getWeight())
                .author(request.getAuthor())
                .publisher(request.getPublisher())
                .description(request.getDescription())
                .sizeWidth(request.getSizeWidth())
                .sizeDepth(request.getSizeDepth())
                .sizeHeight(request.getSizeHeight())
                .build();
        book = bookRepository.save(book);
        return new BookResponse(book);
    }

    public BookResponse save(AddBookRequest request) {
        Category category = categoryRepository.findById(1L).orElseThrow(CategoryNotFoundException::new);
        Book book = Book.builder()
                .isbn(request.getIsbn())
                .title(request.getTitle())
                .pageCount(request.getPageCount())
                .isBookAddedDirectly(false)
                .createdAt(LocalDateTime.now())
                .category(category)
                .coverImgName(request.getCoverImgName())
                .weight(request.getWeight())
                .author(request.getAuthor())
                .publisher(request.getPublisher())
                .description(request.getDescription())
                .sizeWidth(request.getSizeWidth())
                .sizeDepth(request.getSizeDepth())
                .sizeHeight(request.getSizeHeight())
                .build();
        book = bookRepository.save(book);
        return new BookResponse(book);
    }

    private String makeSearchUrl(String keyword, Long page){
        return "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx?ttbkey="+TTB_KEY+"&Query="+keyword+"&QueryType=Title&MaxResults="+PAGE_SIZE+"&start="+page+"&SearchTarget=Book&output=js&Version=20131101";
    }

    private String makeLookUpUrl(String isbn){
        return "https://www.aladin.co.kr/ttb/api/ItemLookUp.aspx?ttbkey="+TTB_KEY+"&itemIdType=ISBN&ItemId="+isbn+"&output=js&Version=20131101&OptResult=packing";
    }

    public List<BookSearchResponse> searchFromAladin(String keyword, Long page) {
        List<BookSearchResponse> responses = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<?> entity = new HttpEntity<>(new HttpHeaders());
        ResponseEntity<Map> resultMap = restTemplate.exchange(makeSearchUrl(keyword, page), HttpMethod.GET, entity, Map.class);
        Map<String, Object> body = resultMap.getBody();
        if (body == null) throw new LastPageReachedException();

        List<Map<String, Object>> items = (List<Map<String, Object>>) body.get("item");
        for (Map<String, Object> itemMap : items) {
            responses.add(makeSearchItemResponse(itemMap));
        }
        return responses;
    }

    public BookSearchResponse makeSearchItemResponse(Map<String, Object> itemMap) {
        String isbn = itemMap.get("isbn13").toString();
        String title = removeSubtitle(itemMap.get("title").toString());
        String bookCoverImgName = replaceCoverUrl(itemMap.get("cover").toString());
        String author = extractAuthors(itemMap.get("author").toString());
        String publisher = itemMap.get("publisher").toString();
        return new BookSearchResponse(isbn, title, bookCoverImgName, author, publisher);
    }

    public BookDetailResponse getBook(String isbn) {
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
        if (body == null) throw new BookNotFoundException();

        List<Map<String, Object>> items = (List<Map<String, Object>>) body.get("item");
        Map<String, Object> item = items.get(0);
        String title = item.get("title").toString();
        String bookCoverImgName = replaceCoverUrl(item.get("cover").toString());
        String author = extractAuthors(item.get("author").toString());
        String publisher = item.get("publisher").toString();
        String description = item.get("description").toString();
        Long categoryId = Long.parseLong(item.get("categoryId").toString());
        Map<String, Object> subInfo = (Map<String, Object>) item.get("subInfo");
        Long itemPage = Long.parseLong(subInfo.get("itemPage").toString());
        String subTitle = subInfo.get("subTitle").toString();
        Map<String, Object> packing = (Map<String, Object>) subInfo.get("packing");
        Integer weight = Integer.parseInt(packing.get("weight").toString());
        Integer sizeDepth = Integer.parseInt(packing.get("sizeDepth").toString());
        Integer sizeHeight = Integer.parseInt(packing.get("sizeHeight").toString());
        Integer sizeWidth = Integer.parseInt(packing.get("sizeWidth").toString());
        title = removeSubtitle2(title, subTitle);

        AddBookRequest request = AddBookRequest.builder()
                .isbn(isbn)
                .title(title)
                .author(author)
                .publisher(publisher)
                .pageCount(itemPage)
                .categoryId(categoryId)
                .description(description)
                .coverImgName(bookCoverImgName)
                .weight(weight)
                .sizeDepth(sizeDepth)
                .sizeHeight(sizeHeight)
                .sizeWidth(sizeWidth)
                .build();
        this.save(request);
        return new BookDetailResponse(isbn, title, bookCoverImgName, author, publisher, description, itemPage);
    }

    public static String replaceCoverUrl(String original) {
        return original.replace("coversum", "cover500");
    }

    public static String removeSubtitle(String original) {
        int hyphenIndex = original.indexOf('-');
        if (hyphenIndex != -1) {
            return original.substring(0, hyphenIndex-1).trim();
        }
        return original;
    }

    public static String removeSubtitle2(String title, String subTitle) {
        int titleLength = title.length();
        int subTitleLength = subTitle.length();
        if (subTitleLength == 0){
            return title;
        }
        return title.substring(0, titleLength-subTitleLength-3).trim();
    }

    public static String extractAuthors(String original) {
        String[] parts = original.split(",");
        StringBuilder authors = new StringBuilder();

        for (String part : parts) {
            part = part.trim();
            if (!part.contains("(")) {
                authors.append(part).append(", ");
            } else {
                int bracketIndex = part.indexOf('(');
                authors.append(part.substring(0, bracketIndex).trim()).append(", ");
                break;
            }
        }
        if (authors.length() > 0) {
            authors.setLength(authors.length() - 2);
        }
        return authors.toString();
    }


}