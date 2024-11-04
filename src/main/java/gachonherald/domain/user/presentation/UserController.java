package gachonherald.domain.user.presentation;

import gachonherald.domain.user.application.UserService;
import gachonherald.domain.user.dto.req.*;
import gachonherald.domain.user.dto.res.*;
import gachonherald.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import gachonherald.global.config.security.JwtProvider;

import java.util.List;

import static gachonherald.domain.oauth.presentation.constant.ResponseMessage.SUCCESS_REGISTER;
import static gachonherald.domain.user.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/refresh")
    public ResponseDto<TokensResponse> refreshAccessToken(@RequestBody String refreshToken) {
        TokensResponse response = jwtProvider.refreshTokens(refreshToken);
        return ResponseDto.of(OK.value(), SUCCESS_REFRESH.getMessage(), response);
    }

    @GetMapping(value = "/email-check/{email}")
    public ResponseDto<EmailCheckResponse> emailCheck(@PathVariable String email) {
        EmailCheckResponse response = userService.emailCheck(email);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @PostMapping(value = "/email-verify/{email}")
    public ResponseDto sendVerificationEmail(@PathVariable String email) {
        userService.sendVerificationEmail(email);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage());
    }


    @PostMapping(value = "/email/signin")
    public ResponseDto<UserResponse> emailSignIn(@RequestBody EmailSignInRequest request) {
        UserResponse response = userService.emailLogin(request);
        return ResponseDto.of(OK.value(), SUCCESS_LOGIN.getMessage(), response);
    }

    @PostMapping(value = "/email/signup")
    public ResponseDto<UserResponse> emailSignUp(@RequestBody EmailSignUpRequest request) {
        UserResponse response = userService.emailRegister(request);
        return ResponseDto.of(OK.value(), SUCCESS_REGISTER.getMessage(), response);
    }

    @PatchMapping(value = "/password")
    public ResponseDto changePassword(@RequestBody ChangePasswordRequest request, Authentication authentication) {
        userService.changePassword(request, authentication.getName());
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @GetMapping(value = "/{reporterId}")
    public ResponseDto<ReporterResponse> getReporterInfo(@PathVariable Long reporterId) {
        ReporterResponse response = userService.getReporterInfo(reporterId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping(value = "/reporters")
    public ResponseDto<ReportersResponse> getReporters() {
        ReportersResponse response = userService.getReporters();
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

//    @GetMapping("/search")
//    public ResponseDto<List<UserInfoResponse>> search(@RequestParam String keyword) {
//        List<UserInfoResponse> responses = userService.search(keyword);
//        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
//    }

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

    @PatchMapping("/reporter/profile")
    public ResponseDto<UserResponse> updateProfile(@RequestBody UpdateUserRequest request, Authentication authentication) {
        UserResponse response = userService.updateProfile(authentication.getName(), request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage(), response);
    }

    @PutMapping("/withdrawal")
    public ResponseDto<UserResponse> withdrawal(Authentication authentication) {
        userService.withdrawal(authentication.getName());
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }
}