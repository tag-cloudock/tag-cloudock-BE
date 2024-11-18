package tagCloudock.domain.news.presentation;

import tagCloudock.domain.news.dto.res.NewsResponse;
import tagCloudock.domain.tag.presentation.constant.ResponseMessage;
import tagCloudock.domain.news.application.NewsService;
import tagCloudock.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping("search/{tag}")
    public ResponseDto<NewsResponse> get(@PathVariable String tag) throws IOException {
        NewsResponse response = newsService.getNews(tag, 1, 10);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }


}