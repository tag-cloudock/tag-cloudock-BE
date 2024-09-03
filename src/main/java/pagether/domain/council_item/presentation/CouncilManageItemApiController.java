package pagether.domain.council_item.presentation;

import pagether.domain.council_item.application.CouncilItemService;
import pagether.domain.council_item.dto.req.AddCouncilItemRequest;
import pagether.domain.council_item.dto.req.UpdateCouncilItemRequest;
import pagether.domain.council_item.dto.res.CouncilItemResponse;
import pagether.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pagether.domain.council_item.presentation.constant.ResponseMessage;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/manage/council-item")
@RequiredArgsConstructor
public class CouncilManageItemApiController {

    private final CouncilItemService councilItemService;

    @PostMapping
    public ResponseDto<CouncilItemResponse> save(@RequestBody AddCouncilItemRequest request, Authentication authentication) {
        CouncilItemResponse response = councilItemService.saveByCouncil(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @GetMapping("/all")
    public ResponseDto<List<CouncilItemResponse>> getAll() {
        List<CouncilItemResponse> responses = councilItemService.getAll();
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage(), responses);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Integer id) {
        councilItemService.delete(id);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }

    @PutMapping("/{id}")
    public ResponseDto update(@PathVariable Integer id, @RequestBody UpdateCouncilItemRequest request) {
        councilItemService.update(id, request);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_UPDATE.getMessage());
    }
}
