package pagether.domain.feed.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pagether.domain.book.application.BookService;
import pagether.domain.book.dto.req.AddBookRequest;
import pagether.domain.book.dto.res.BookResponse;
import pagether.domain.feed.application.FeedService;
import pagether.domain.feed.dto.res.FeedDTO;
import pagether.domain.feed.presentation.constant.ResponseMessage;
import pagether.domain.news.dto.res.SeparatedNewsResponse;
import pagether.global.config.dto.ResponseDto;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedApiController {
    private final FeedService feedService;

    @GetMapping("/follow")
    public ResponseDto<List<FeedDTO>> getFollowFeeds(Authentication authentication) {
        List<FeedDTO> response = feedService.getFollowFeeds(authentication.getName());
        return ResponseDto.of(OK.value(), pagether.domain.news.presentation.constant.ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/popular")
    public ResponseDto<List<FeedDTO>> getPopularFeeds(Authentication authentication) {
        List<FeedDTO> response = feedService.getPopularFeeds(authentication.getName());
        return ResponseDto.of(OK.value(), pagether.domain.news.presentation.constant.ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/recommended")
    public ResponseDto<List<FeedDTO>> getRecommendedFeeds(Authentication authentication) {
        List<FeedDTO> response = feedService.getRecommendedFeeds(authentication.getName());
        return ResponseDto.of(OK.value(), pagether.domain.news.presentation.constant.ResponseMessage.SUCCESS_READ.getMessage(), response);
    }
}
