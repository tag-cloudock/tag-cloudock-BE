package pagether.domain.announcement.presentation;

import pagether.domain.announcement.application.AnnoService;
import pagether.domain.announcement.dto.req.AddAnnoRequest;
import pagether.domain.announcement.dto.res.AnnoResponse;
import pagether.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pagether.domain.announcement.presentation.constant.ResponseMessage;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/anno")
@RequiredArgsConstructor
public class AnnoApiController {

    private final AnnoService annoService;

    @PostMapping
    public ResponseDto<AnnoResponse> save(@RequestBody AddAnnoRequest request) {
        AnnoResponse response = annoService.save(request);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @GetMapping("/{id}")
    public ResponseDto<AnnoResponse> get(@PathVariable Integer id) {
        AnnoResponse response = annoService.get(id);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/all")
    public ResponseDto<List<AnnoResponse>> getAll() {
        List<AnnoResponse> responses = annoService.getAll();
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), responses);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Integer id) {
        annoService.delete(id);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}
