package pagether.domain.news.presentation;

import org.springframework.security.core.Authentication;
import pagether.domain.news.application.NewsService;
import pagether.domain.news.dto.req.AddNewsRequest;
import pagether.domain.news.dto.res.NewsResponse;
import pagether.domain.news.dto.res.SeparatedNewsResponse;
import pagether.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pagether.domain.news.presentation.constant.ResponseMessage;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsApiController {

    private final NewsService newsService;

    @PostMapping
    public ResponseDto<NewsResponse> save(@RequestBody AddNewsRequest request) {
        NewsResponse response = newsService.save(request);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @GetMapping()
    public ResponseDto<SeparatedNewsResponse> getAll(Authentication authentication) {
        SeparatedNewsResponse response = newsService.getAll(authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Integer id) {
        newsService.delete(id);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}
