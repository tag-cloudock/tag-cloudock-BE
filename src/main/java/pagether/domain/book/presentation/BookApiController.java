package pagether.domain.book.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pagether.domain.book.application.BookService;
import pagether.domain.book.dto.req.AddBookRequest;
import pagether.domain.book.dto.res.BookResponse;
import pagether.domain.book.presentation.constant.ResponseMessage;
import pagether.global.config.dto.ResponseDto;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookApiController {

    private final BookService bookService;

    @PostMapping
    public ResponseDto<BookResponse> save(@RequestBody AddBookRequest request) {
        BookResponse response = bookService.save(request);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}
