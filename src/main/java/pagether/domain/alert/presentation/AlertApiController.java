package pagether.domain.alert.presentation;

import org.springframework.security.core.Authentication;
import pagether.domain.alert.application.AlertService;
import pagether.domain.alert.dto.res.SeparatedAlertResponse;
import pagether.domain.news.application.NewsService;
import pagether.domain.news.dto.req.AddNewsRequest;
import pagether.domain.news.dto.res.NewsResponse;
import pagether.domain.news.dto.res.SeparatedNewsResponse;
import pagether.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pagether.domain.news.presentation.constant.ResponseMessage;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertApiController {

    private final AlertService alertService;

    @GetMapping
    public ResponseDto<SeparatedAlertResponse> getAllByUser(Authentication authentication) {
        SeparatedAlertResponse response = alertService.getAllByUser(authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Long id) {
        alertService.delete(id);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}
