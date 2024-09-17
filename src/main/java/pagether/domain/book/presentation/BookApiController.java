package pagether.domain.book.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pagether.domain.book.application.BookService;
import pagether.domain.book.dto.req.AddBookRequest;
import pagether.domain.book.dto.res.BookDetailResponse;
import pagether.domain.book.dto.res.BookResponse;
import pagether.domain.book.dto.res.BookSearchResponse;
import pagether.domain.book.presentation.constant.ResponseMessage;
import pagether.global.config.dto.ResponseDto;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookApiController {

    private final BookService bookService;

    @GetMapping("/search")
    public ResponseDto<List<BookSearchResponse>> search(@RequestParam String keyword) {
        List<BookSearchResponse> responses = bookService.searchFromAladin(keyword);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), responses);
    }

    @GetMapping("/look-up")
    public ResponseDto<BookDetailResponse> lookUp(@RequestParam String isbn) {
        BookDetailResponse response = bookService.lookUpFromAladin(isbn);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

}
