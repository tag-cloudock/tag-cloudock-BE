package pagether.domain.ocr.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pagether.domain.ocr.application.OCRService;
import pagether.domain.ocr.dto.res.OCRResponse;
import pagether.domain.ocr.presentation.constant.ResponseMessage;
import pagether.global.config.dto.ResponseDto;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/ocr")
@RequiredArgsConstructor
public class OCRController {
    private final OCRService OCRService;

    @GetMapping
    public ResponseDto<OCRResponse> extractText(@RequestPart(required = false) MultipartFile pic) {
        OCRResponse response = OCRService.extractTextFromImage(pic);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_EXTRACT.getMessage(), response);
    }
}
