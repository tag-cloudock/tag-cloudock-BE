package pagether.domain.alert.presentation;

import org.springframework.security.core.Authentication;
import pagether.domain.alert.application.AlertService;
import pagether.domain.alert.domain.FetchAlarmType;
import pagether.domain.alert.dto.res.AlertResponses;
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
    public ResponseDto<AlertResponses> getAlertsByUser(@RequestParam FetchAlarmType type, @RequestParam Long cursor, Authentication authentication) {
        AlertResponses response = alertService.getAlertsByUser(type, authentication.getName(), cursor);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Long id) {
        alertService.delete(id);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}
