package pagether.domain.feed.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pagether.domain.feed.application.FeedService;
import pagether.domain.feed.dto.res.FeedDTO;
import pagether.domain.feed.presentation.constant.ResponseMessage;
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
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/popular")
    public ResponseDto<List<FeedDTO>> getPopularFeeds(Authentication authentication) {
        List<FeedDTO> response = feedService.getPopularFeeds(authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/recommended")
    public ResponseDto<List<FeedDTO>> getRecommendedFeeds(Authentication authentication) {
        List<FeedDTO> response = feedService.getRecommendedFeeds(authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }
}
