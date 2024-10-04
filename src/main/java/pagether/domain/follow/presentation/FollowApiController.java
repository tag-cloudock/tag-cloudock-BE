package pagether.domain.follow.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pagether.domain.follow.application.FollowService;
import pagether.domain.follow.dto.req.AddFollowRequest;
import pagether.domain.follow.dto.res.FollowCountResponse;
import pagether.domain.follow.dto.res.FollowListResponse;
import pagether.domain.follow.dto.res.FollowResponse;
import pagether.domain.follow.presentation.constant.ResponseMessage;
import pagether.global.config.dto.ResponseDto;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowApiController {
    private final FollowService followService;
    @PostMapping
    public ResponseDto<FollowResponse> save(@RequestBody AddFollowRequest request, Authentication authentication) {
        FollowResponse response = followService.save(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }
    @GetMapping("/followings")
    public ResponseDto<FollowListResponse> getFollowingList(Authentication authentication, Long cursor) {
        FollowListResponse response = followService.getFollowingList(authentication.getName(), cursor);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/followers")
    public ResponseDto<FollowListResponse> getFollowerList(Authentication authentication, Long cursor) {
        FollowListResponse response = followService.getFollowerList(authentication.getName(), cursor);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/count")
    public ResponseDto<FollowCountResponse> getCount(Authentication authentication) {
        FollowCountResponse response = followService.getCount(authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }
    @PatchMapping("/accept/{followId}")
    public ResponseDto accept(@PathVariable Long followId) {
        followService.accept(followId);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_UPDATE.getMessage());
    }
    @DeleteMapping("/reject/{followId}")
    public ResponseDto reject(@PathVariable Long followId) {
        followService.reject(followId);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
    @DeleteMapping("/following/{followeeId}")
    public ResponseDto deleteFollowing(@PathVariable String followeeId, Authentication authentication) {
        followService.delete(followeeId, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
    @DeleteMapping("/follower/{followerId}")
    public ResponseDto deleteFollower(@PathVariable String followerId, Authentication authentication) {
        followService.delete(authentication.getName(), followerId);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}