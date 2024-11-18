package tagCloudock.domain.user.presentation;

import tagCloudock.domain.user.application.UserService;
import tagCloudock.domain.user.dto.req.*;
import tagCloudock.domain.user.dto.res.*;
import tagCloudock.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tagCloudock.global.config.security.JwtProvider;

import static tagCloudock.domain.oauth.presentation.constant.ResponseMessage.SUCCESS_REGISTER;
import static tagCloudock.domain.user.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class UserController {
    private final JwtProvider jwtProvider;

    @PostMapping("/refresh")
    public ResponseDto<TokensResponse> refreshAccessToken(@RequestBody String refreshToken) {
        TokensResponse response = jwtProvider.refreshTokens(refreshToken);
        return ResponseDto.of(OK.value(), SUCCESS_REFRESH.getMessage(), response);
    }
}