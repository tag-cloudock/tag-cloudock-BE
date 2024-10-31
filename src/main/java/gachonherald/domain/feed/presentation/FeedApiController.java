package gachonherald.domain.feed.presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import gachonherald.domain.feed.application.FeedService;
import gachonherald.domain.feed.domain.FetchFeedType;
import gachonherald.domain.feed.dto.res.FeedsResponse;
import gachonherald.domain.feed.presentation.constant.ResponseMessage;
import gachonherald.global.config.dto.ResponseDto;

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
