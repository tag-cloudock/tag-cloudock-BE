package pagether.domain.count.presentation;

import pagether.domain.count.application.CountService;
import pagether.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static pagether.domain.council.presentation.constant.ResponseMessage.SUCCESS_READ;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/count")
@RequiredArgsConstructor
public class CountApiController {

    private final CountService countService;

    @GetMapping("/{id}")
    public ResponseDto<Long> get(@PathVariable Integer id) {
        Long response = countService.get(id);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }


}
