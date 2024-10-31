package gachonherald.domain.comment.presentation;

import gachonherald.domain.article.application.ArticleService;
import gachonherald.domain.comment.application.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;


}