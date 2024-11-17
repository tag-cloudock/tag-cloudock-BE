package tagCloudock.domain.oauth.presentation;

import tagCloudock.domain.oauth.dto.req.KakaoSignUpRequest;
import tagCloudock.domain.user.application.UserService;
import tagCloudock.domain.user.dto.res.UserResponse;
import tagCloudock.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static tagCloudock.domain.oauth.presentation.constant.ResponseMessage.SUCCESS_LOGIN;
import static tagCloudock.domain.oauth.presentation.constant.ResponseMessage.SUCCESS_REGISTER;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OauthController {
    private final UserService userService;

    @GetMapping(value = "/kakao/{code}")
    public ResponseDto<UserResponse> signin(@PathVariable String code) throws Exception {
        UserResponse response = userService.kakaoLogin(code);
        return ResponseDto.of(OK.value(), SUCCESS_LOGIN.getMessage(), response);
    }

    @PostMapping(value = "/kakao")
    public ResponseDto<UserResponse> signUp(@RequestBody KakaoSignUpRequest request) {
        UserResponse response = userService.kakaoRegister(request);
        return ResponseDto.of(OK.value(), SUCCESS_REGISTER.getMessage(), response);
    }
}
