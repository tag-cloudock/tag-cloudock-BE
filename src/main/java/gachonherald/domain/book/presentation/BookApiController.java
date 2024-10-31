package gachonherald.domain.book.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import gachonherald.domain.book.application.BookService;
import gachonherald.domain.book.dto.req.AddBookRequest;
import gachonherald.domain.book.dto.res.BookDetailResponse;
import gachonherald.domain.book.dto.res.BookResponse;
import gachonherald.domain.book.dto.res.BookSearchResponse;
import gachonherald.domain.book.presentation.constant.ResponseMessage;
import gachonherald.global.config.dto.ResponseDto;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookApiController {

    private final BookService bookService;

    @PostMapping
    public ResponseDto<BookResponse> save(@RequestPart AddBookRequest request, @RequestPart(required = false) MultipartFile pic) {
        BookResponse response = bookService.save(request, pic);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @GetMapping
    public ResponseDto<BookDetailResponse> get(@RequestParam String isbn) {
        BookDetailResponse response = bookService.getBook(isbn);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/search")
    public ResponseDto<List<BookSearchResponse>> search(@RequestParam String keyword, @RequestParam Long page) {
        List<BookSearchResponse> responses = bookService.searchFromAladin(keyword, page);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), responses);
    }
}
