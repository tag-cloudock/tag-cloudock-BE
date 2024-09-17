package pagether.domain.heart.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pagether.domain.heart.application.HeartService;
import pagether.domain.heart.dto.req.AddHeartRequest;
import pagether.domain.heart.dto.res.HeartResponse;
import pagether.domain.heart.presentation.constant.ResponseMessage;
import pagether.global.config.dto.ResponseDto;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/hearts")
@RequiredArgsConstructor
public class HeartApiController {
    private final HeartService heartService;
    @PostMapping
    public ResponseDto<HeartResponse> save(@RequestBody AddHeartRequest request, Authentication authentication) {
        HeartResponse response = heartService.save(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @DeleteMapping("/{noteId}")
    public ResponseDto delete(@PathVariable Long noteId, Authentication authentication) {
        heartService.delete(noteId, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}