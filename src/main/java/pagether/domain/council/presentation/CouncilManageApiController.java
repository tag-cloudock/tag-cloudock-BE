package pagether.domain.council.presentation;

import pagether.domain.council.application.CouncilService;
import pagether.domain.council.dto.req.AddCouncilRequest;
import pagether.domain.council.dto.req.UpdateCouncilRequest;
import pagether.domain.council.dto.res.CouncilResponse;
import pagether.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static pagether.domain.council.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/manage/council")
@RequiredArgsConstructor
public class CouncilManageApiController {

    private final CouncilService councilService;

    @PostMapping
    public ResponseDto<CouncilResponse> save(@RequestPart AddCouncilRequest request, @RequestPart(required = false) MultipartFile pic) throws Exception {
        CouncilResponse response = councilService.save(request, pic);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping
    public ResponseDto<CouncilResponse> getByManager(Authentication authentication) {
        CouncilResponse response = councilService.getCouncilByManager(authentication.getName());
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Integer id) {
        councilService.delete(id);
        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }

    @PutMapping("/{id}")
    public ResponseDto update(@PathVariable Integer id, @RequestBody UpdateCouncilRequest request) {
        councilService.update(id, request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @PutMapping("/{id}/{isCouncilSelfManage}")
    public ResponseDto changeManager(@PathVariable Integer id, @PathVariable Boolean isCouncilSelfManage) {
        councilService.changeManager(id, isCouncilSelfManage);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }
}
