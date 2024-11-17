package tagCloudock.domain.news.presentation;

import tagCloudock.domain.news.dto.res.NewsResponse;
import tagCloudock.domain.tag.presentation.constant.ResponseMessage;
import tagCloudock.domain.news.application.NewsService;
import tagCloudock.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/{tag}")
    public ResponseDto<NewsResponse> getList(@PathVariable String tag, @RequestParam int pageNumber) {
        NewsResponse response = newsService.getList(tag, pageNumber);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }
}