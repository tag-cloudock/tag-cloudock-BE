package gachonherald.domain.news.presentation;

import org.springframework.security.core.Authentication;
import gachonherald.domain.news.application.NewsService;
import gachonherald.domain.news.dto.req.AddNewsRequest;
import gachonherald.domain.news.dto.res.NewsResponse;
import gachonherald.domain.news.dto.res.NewsResponses;
import gachonherald.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import gachonherald.domain.news.presentation.constant.ResponseMessage;

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
    public ResponseDto<NewsResponses> getAll(@RequestParam Long cursor, Authentication authentication) {
        NewsResponses response = newsService.getAll(authentication.getName(), cursor);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Long id) {
        newsService.delete(id);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}
