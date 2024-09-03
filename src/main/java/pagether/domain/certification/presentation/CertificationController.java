package pagether.domain.certification.presentation;

import pagether.domain.certification.application.CertificationService;
import pagether.domain.certification.dto.req.CertifiRequest;
import pagether.domain.certification.dto.req.UpdateUserCertifiRequest;
import pagether.domain.certification.dto.res.CertifiResponse;
import pagether.domain.user.application.UserService;
import pagether.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pagether.domain.announcement.presentation.constant.ResponseMessage;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/certifi") // certifications
@RequiredArgsConstructor
public class CertificationController {

    private final CertificationService certificationService;
    private final UserService userService;

    @PostMapping("/request") //
    public ResponseDto<CertifiResponse> save(@RequestPart CertifiRequest request, @RequestPart(required = false) MultipartFile pic, Authentication authentication) throws Exception {
        CertifiResponse response = certificationService.save(request, pic, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @GetMapping("/requests/{id}") // {id}
    public ResponseDto<CertifiResponse> get(@PathVariable Long id) throws Exception {
        CertifiResponse response = certificationService.get(id);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/requests") //
    public ResponseDto<List<CertifiResponse>> getAll() {
        List<CertifiResponse> responses = certificationService.getAll();
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), responses);
    }

    @PutMapping("/approval/{id}") // {id}
    public ResponseDto update(@PathVariable String id, @RequestBody UpdateUserCertifiRequest request) {
        userService.updateCertification(id, request);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_UPDATE.getMessage());
    }
}