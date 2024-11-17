package tagCloudock.domain.news.presentation.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {
    SUCCESS_READ("조회에 성공했습니다."),
    NEWS_NOT_FOUND("존재하지 않는 항목입니다."),
    ;
    private String message;
}