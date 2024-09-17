package pagether.domain.report.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pagether.domain.report.presentation.constant.ResponseMessage;
import pagether.domain.report.application.ReportService;
import pagether.domain.report.dto.req.AddReportRequest;
import pagether.domain.report.dto.res.ReportResponse;
import pagether.global.config.dto.ResponseDto;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportApiController {
    private final ReportService reportService;

    @PostMapping
    public ResponseDto<ReportResponse> save(@RequestBody AddReportRequest request, Authentication authentication) {
        ReportResponse response = reportService.save(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

//    @GetMapping()
//    public ResponseDto<SeparatedNewsResponse> getAll(Authentication authentication) {
//        SeparatedNewsResponse response = noteService.getAll(authentication.getName());
//        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
//    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Long id) {
        reportService.delete(id);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}