package gachonherald.domain.ocr.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import gachonherald.domain.ocr.application.OCRService;
import gachonherald.domain.ocr.dto.res.OCRResponse;
import gachonherald.domain.ocr.presentation.constant.ResponseMessage;
import gachonherald.global.config.dto.ResponseDto;

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
