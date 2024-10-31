package gachonherald.domain.follow.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import gachonherald.domain.follow.application.FollowService;
import gachonherald.domain.follow.domain.FetchFollowType;
import gachonherald.domain.follow.dto.req.AddFollowRequest;
import gachonherald.domain.follow.dto.res.FollowCountResponse;
import gachonherald.domain.follow.dto.res.FollowListResponse;
import gachonherald.domain.follow.dto.res.FollowResponse;
import gachonherald.domain.follow.presentation.constant.ResponseMessage;
import gachonherald.global.config.dto.ResponseDto;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/follows")
@RequiredArgsConstructor
public class FollowApiController {
    private final FollowService followService;
    @PostMapping
    public ResponseDto<FollowResponse> save(@RequestBody AddFollowRequest request, Authentication authentication) {
        FollowResponse response = followService.save(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }
    @GetMapping("/all")
    public ResponseDto<FollowListResponse> getList(@RequestParam FetchFollowType type, Long cursor, Authentication authentication) {
        FollowListResponse response = followService.getList(type, cursor, authentication.getName());
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