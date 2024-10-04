package pagether.domain.feed.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pagether.domain.feed.application.FeedService;
import pagether.domain.feed.dto.FeedDTO;
import pagether.domain.feed.dto.res.FeedsResponse;
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
    public ResponseDto<FeedsResponse> getFollowFeeds(@RequestParam int cursor, Authentication authentication) throws JsonProcessingException {
        FeedsResponse response = feedService.getFollowFeeds(cursor, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/popular")
    public ResponseDto<FeedsResponse> getPopularFeeds(@RequestParam int cursor, Authentication authentication) throws JsonProcessingException {
        FeedsResponse response = feedService.getPopularFeeds(cursor, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/recommended")
    public ResponseDto<List<FeedDTO>> getRecommendedFeeds(Authentication authentication) {
        List<FeedDTO> response = feedService.getRecommendedFeeds(authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }
}
