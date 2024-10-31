package gachonherald.domain.ocr.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
