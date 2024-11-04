package gachonherald.domain.comment.presentation;

import gachonherald.domain.article.application.ArticleService;
import gachonherald.domain.article.dto.req.AddArticleRequest;
import gachonherald.domain.article.dto.res.ArticleResponse;
import gachonherald.domain.article.presentation.constant.ResponseMessage;
import gachonherald.domain.comment.application.CommentService;
import gachonherald.domain.comment.dto.req.AddCommentRequest;
import gachonherald.domain.comment.dto.res.CommentResponse;
import gachonherald.domain.comment.dto.res.CommentsResponse;
import gachonherald.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


    @PostMapping
    public ResponseDto<CommentResponse> save(@RequestBody AddCommentRequest request, Authentication authentication) {
        CommentResponse response = commentService.save(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @GetMapping("/{articleId}")
    public ResponseDto<CommentsResponse> getByArticle(@PathVariable Long articleId, @RequestParam int pageNumber) {
        CommentsResponse response = commentService.getByArticle(articleId, pageNumber);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping
    public ResponseDto<CommentsResponse> getAll(@RequestParam int pageNumber) {
        CommentsResponse response = commentService.getAll(pageNumber);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }
}