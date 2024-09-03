package pagether.domain.council.presentation;

import pagether.domain.council.application.CouncilService;
import pagether.domain.council.dto.res.CouncilResponse;
import pagether.domain.council.dto.res.CouncilsResponse;
import pagether.domain.council.dto.res.SearchResponse;
import pagether.domain.council_item.presentation.constant.ResponseMessage;
import pagether.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static pagether.domain.council.presentation.constant.ResponseMessage.SUCCESS_READ;
import static pagether.domain.council.presentation.constant.ResponseMessage.SUCCESS_UPDATE;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/council") // councils
@RequiredArgsConstructor
public class CouncilApiController {

    private final CouncilService councilService;

    @GetMapping("/{id}")
    public ResponseDto<CouncilResponse> get(@PathVariable Integer id) {
        CouncilResponse response = councilService.get(id);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/all") //
    public ResponseDto<List<CouncilsResponse>> getAll(@RequestParam(name = "campus", required = false) String campus) {
        if (campus == null) {
            List<CouncilsResponse> responses = councilService.getAll();
            return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
        }
        List<CouncilsResponse> responses = councilService.getAllByCampus(campus);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @PutMapping("/{id}")
    public ResponseDto update(@PathVariable Integer id) {
        councilService.countHeart(id);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @GetMapping("/search/{keyword}")
    public ResponseDto<List<SearchResponse>> getByKeyword(@PathVariable String keyword) {
        List<SearchResponse> responses = councilService.getByKeyword(keyword);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), responses);
    }

}
