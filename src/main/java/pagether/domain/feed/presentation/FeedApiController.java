package pagether.domain.feed.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pagether.domain.feed.application.FeedService;
import pagether.domain.feed.domain.FetchFeedType;
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

    @GetMapping
    public ResponseDto<FeedsResponse> getFeeds(@RequestParam FetchFeedType type, @RequestParam int cursor, Authentication authentication) throws JsonProcessingException {
        FeedsResponse response = feedService.getFeeds(type, cursor, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }
}
