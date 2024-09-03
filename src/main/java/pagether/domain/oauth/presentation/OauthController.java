package pagether.domain.oauth.presentation;

import pagether.domain.oauth.dto.req.KakaoSignUpRequest;
import pagether.domain.user.application.UserService;
import pagether.domain.user.dto.res.UserResponse;
import pagether.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static pagether.domain.oauth.presentation.constant.ResponseMessage.SUCCESS_LOGIN;
import static pagether.domain.oauth.presentation.constant.ResponseMessage.SUCCESS_REGISTER;
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
        UserResponse response = userService.kakaoRegister(request.getNickname(), request.getCode());
        return ResponseDto.of(OK.value(), SUCCESS_REGISTER.getMessage(), response);
    }
}
