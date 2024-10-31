package gachonherald.domain.article.presentation;

import gachonherald.domain.article.application.ArticleService;
import gachonherald.domain.user.application.UserService;
import gachonherald.domain.user.dto.req.ChangePasswordRequest;
import gachonherald.domain.user.dto.req.EmailSignInRequest;
import gachonherald.domain.user.dto.req.EmailSignUpRequest;
import gachonherald.domain.user.dto.req.UpdateUserRequest;
import gachonherald.domain.user.dto.res.TokensResponse;
import gachonherald.domain.user.dto.res.UserInfoResponse;
import gachonherald.domain.user.dto.res.UserResponse;
import gachonherald.global.config.dto.ResponseDto;
import gachonherald.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static gachonherald.domain.oauth.presentation.constant.ResponseMessage.SUCCESS_REGISTER;
import static gachonherald.domain.user.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;


}