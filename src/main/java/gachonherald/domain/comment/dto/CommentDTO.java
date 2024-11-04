package gachonherald.domain.comment.dto;

import gachonherald.domain.article.domain.Article;
import gachonherald.domain.article.domain.ArticleStatus;
import gachonherald.domain.comment.domain.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class CommentDTO {
    private Long commentId;
    private Long articleId;
    private Long commenterId;
    private String commenterNickname;
    private String content;
    private LocalDateTime createdAt;


    public CommentDTO(Comment comment) {
        commentId = comment.getCommentId();
        articleId = comment.getArticle().getArticleId();
        content = comment.getContent();
        commenterId = comment.getCommenter().getId();
        commenterNickname = comment.getCommenter().getNickName();
        createdAt = comment.getCreatedAt();
    }
}
