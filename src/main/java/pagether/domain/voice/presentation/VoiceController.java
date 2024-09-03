package pagether.domain.voice.presentation;

import pagether.domain.voice.application.VoiceService;
import pagether.domain.voice.dto.req.VoiceRequest;
import pagether.domain.voice.dto.res.VoicesResponse;
import pagether.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pagether.domain.announcement.presentation.constant.ResponseMessage;

import java.util.List;

import static pagether.domain.council.presentation.constant.ResponseMessage.SUCCESS_READ;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/voice")
@RequiredArgsConstructor
public class VoiceController {
    private final VoiceService voiceService;

    @PostMapping
    public ResponseDto save(@RequestBody VoiceRequest request) throws Exception {
        voiceService.save(request);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage());
    }

    @GetMapping()
    public ResponseDto<List<VoicesResponse>> get() {
        List<VoicesResponse> response = voiceService.get();
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }
}