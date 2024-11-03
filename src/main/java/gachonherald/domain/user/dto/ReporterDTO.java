package gachonherald.domain.user.dto;

import gachonherald.domain.article.domain.Article;
import gachonherald.domain.article.domain.ArticleStatus;
import gachonherald.domain.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ReporterDTO {
    private Long reporterId;
    private String position;
    private String nickname;

    public ReporterDTO(User reporter) {
        reporterId = reporter.getId();
        position = reporter.getPosition().getDisplayName();
        nickname = reporter.getNickName();
    }
}
