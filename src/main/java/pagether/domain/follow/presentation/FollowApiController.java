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

    @GetMapping("/accept/{followId}")
    public ResponseDto accept(@PathVariable Long followId) {
        followService.accept(followId);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage());
    }

    @GetMapping("/reject/{followId}")
    public ResponseDto reject(@PathVariable Long followId) {
        followService.reject(followId);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage());
    }

    @GetMapping("/list")
    public ResponseDto<FollowListResponse> getList(Authentication authentication) {
        FollowListResponse response = followService.getUsers(authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }
    @GetMapping("/count")
    public ResponseDto<FollowCountResponse> getCount(Authentication authentication) {
        FollowCountResponse response = followService.getCount(authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }
    @DeleteMapping("/following/{followeeId}")
    public ResponseDto deleteFollowing(@PathVariable String followeeId, Authentication authentication) {
        followService.deleteFollowing(followeeId, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }

    @DeleteMapping("/follower/{followerId}")
    public ResponseDto deleteFollower(@PathVariable String followerId, Authentication authentication) {
        followService.deleteFollower(followerId, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}