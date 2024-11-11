package gachonherald.domain.article.presentation;

import gachonherald.domain.article.application.ArticleService;
import gachonherald.domain.article.dto.req.AddArticleRequest;
import gachonherald.domain.article.dto.req.UpdateArticleRequest;
import gachonherald.domain.article.dto.res.ArticleResponse;
import gachonherald.domain.article.dto.res.ArticlesResponse;
import gachonherald.domain.article.dto.res.HomeArticlesResponse;
import gachonherald.domain.article.presentation.constant.ResponseMessage;
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
import org.hibernate.sql.Update;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static gachonherald.domain.oauth.presentation.constant.ResponseMessage.SUCCESS_REGISTER;
import static gachonherald.domain.user.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping
    public ResponseDto<ArticleResponse> save(@RequestBody AddArticleRequest request, Authentication authentication) {
        ArticleResponse response = articleService.save(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @PatchMapping
    public ResponseDto<ArticleResponse> update(@RequestBody UpdateArticleRequest request) {
        ArticleResponse response = articleService.update(request);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_UPDATE.getMessage(), response);
    }

    @GetMapping("/{id}")
    public ResponseDto<ArticleResponse> getFromReader(@PathVariable Long id) {
        ArticleResponse response = articleService.getFromReader(id);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/list/section/{sectionId}")
    public ResponseDto<ArticlesResponse> getArticlesBySection(@PathVariable Long sectionId, @RequestParam int pageNumber) {
        ArticlesResponse response = articleService.getArticlesBySection(sectionId, pageNumber);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/list/reporter/{reporterId}")
    public ResponseDto<ArticlesResponse> getArticlesByReporter(@PathVariable Long reporterId, @RequestParam int pageNumber) {
        ArticlesResponse response = articleService.getArticlesByReporter(reporterId, pageNumber);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }


    @GetMapping("/reporter/{id}")
    public ResponseDto<ArticleResponse> getFromReporter(@PathVariable Long id, Authentication authentication) {
        ArticleResponse response = articleService.getFromReporter(id, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/reporter/list")
    public ResponseDto<ArticlesResponse> getUnpublishedArticles(Authentication authentication) {
        ArticlesResponse response = articleService.getUnpublishedArticles(authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/home")
    public ResponseDto<HomeArticlesResponse> getHomeArticles() {
        HomeArticlesResponse response = articleService.getHomeArticles();
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }
}