package pagether.domain.readInfo.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pagether.domain.readInfo.dto.req.UpdatePageRequest;
import pagether.domain.readInfo.presentation.constant.ResponseMessage;
import pagether.domain.readInfo.application.ReadInfoService;
import pagether.domain.readInfo.dto.req.AddReadInfoRequest;
import pagether.domain.readInfo.dto.res.ReadInfoResponse;
import pagether.global.config.dto.ResponseDto;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/read")
@RequiredArgsConstructor
public class ReadInfoApiController {

    private final ReadInfoService readInfoService;

    @PostMapping
    public ResponseDto<ReadInfoResponse> startRead(@RequestBody AddReadInfoRequest request, Authentication authentication) {
        ReadInfoResponse response = readInfoService.startRead(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @PostMapping("/pin")
    public ResponseDto<ReadInfoResponse> pin(@RequestBody AddReadInfoRequest request, Authentication authentication) {
        ReadInfoResponse response = readInfoService.pin(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @PatchMapping("/page")
    public ResponseDto<ReadInfoResponse> pageUpdate(@RequestBody UpdatePageRequest request, Authentication authentication) {
        ReadInfoResponse response = readInfoService.pageUpdate(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_UPDATE.getMessage(), response);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Long id) {
        readInfoService.delete(id);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}
