package pagether.domain.ocr.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pagether.domain.news.dto.res.NewsResponse;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Builder
public class OCRResponse {
    private String text;

    public OCRResponse(String text) {
        this.text = text;
    }
}
