package pagether.domain.user.presentation;

import pagether.domain.oauth.dto.req.KakaoSignUpRequest;
import pagether.domain.report.dto.req.AddReportRequest;
import pagether.domain.user.application.UserService;
import pagether.domain.user.dto.req.*;
import pagether.domain.user.dto.res.UserInfoResponse;
import pagether.domain.user.dto.res.UserResponse;
import pagether.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static pagether.domain.oauth.presentation.constant.ResponseMessage.SUCCESS_REGISTER;
import static pagether.domain.user.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping(value = "/guest")
    public ResponseDto<UserResponse> guestSignUp(@RequestBody GuestSignUpRequest request) {
        UserResponse response = userService.guestRegister(request);
        return ResponseDto.of(OK.value(), SUCCESS_REGISTER.getMessage(), response);
    }

    @GetMapping(value = "/email")
    public ResponseDto<UserResponse> emailSignIn(@RequestBody EmailSignInRequest request) {
        UserResponse response = userService.emailLogin(request);
        return ResponseDto.of(OK.value(), SUCCESS_LOGIN.getMessage(), response);
    }

    @PostMapping(value = "/email")
    public ResponseDto<UserResponse> emailSignUp(@RequestBody EmailSignUpRequest request) {
        UserResponse response = userService.emailRegister(request);
        return ResponseDto.of(OK.value(), SUCCESS_REGISTER.getMessage(), response);
    }

    @PatchMapping(value = "/password")
    public ResponseDto changePassword(@RequestBody ChangePasswordRequest request, Authentication authentication) {
        userService.changePassword(request, authentication.getName());
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @GetMapping
    public ResponseDto<UserInfoResponse> getInfo(@RequestParam String id) {
        UserInfoResponse response = userService.get(id);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/search")
    public ResponseDto<List<UserInfoResponse>> search(@RequestParam String keyword) {
        List<UserInfoResponse> responses = userService.search(keyword);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @PatchMapping("/profile-img")
    public ResponseDto<UserResponse> updateProfileImg(@RequestPart(required = false) MultipartFile pic, Authentication authentication) {
        UserResponse response = userService.updateProfileImg(authentication.getName(), pic);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage(), response);
    }

    @PatchMapping("/profile-img/default")
    public ResponseDto<UserResponse> updateProfileImgToDefault(Authentication authentication) {
        UserResponse response = userService.deleteProfileImg(authentication.getName());
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage(), response);
    }

    @PatchMapping("/nickname")
    public ResponseDto<UserResponse> updateNickname(@RequestBody UpdateUserRequest request, Authentication authentication) {
        UserResponse response = userService.updateNickName(authentication.getName(), request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage(), response);
    }

    @PatchMapping("/account-name")
    public ResponseDto<UserResponse> updateAccountName(@RequestBody UpdateUserRequest request, Authentication authentication) {
        UserResponse response = userService.updateAccountName(authentication.getName(), request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage(), response);
    }

    @PatchMapping("/bio")
    public ResponseDto<UserResponse> updateBio(@RequestBody UpdateUserRequest request, Authentication authentication) {
        UserResponse response = userService.updateBio(authentication.getName(), request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage(), response);
    }

    @PutMapping("/withdrawal")
    public ResponseDto<UserResponse> withdrawal(Authentication authentication) {
        userService.withdrawal(authentication.getName());
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }
}