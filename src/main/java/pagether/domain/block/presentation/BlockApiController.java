package pagether.domain.block.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pagether.domain.block.application.BlockService;
import pagether.domain.block.dto.req.AddBlockRequest;
import pagether.domain.block.dto.res.BlockCountResponse;
import pagether.domain.block.dto.res.BlockListResponse;
import pagether.domain.block.dto.res.BlockResponse;
import pagether.domain.block.presentation.constant.ResponseMessage;
import pagether.domain.follow.dto.res.FollowListResponse;
import pagether.global.config.dto.ResponseDto;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/block")
@RequiredArgsConstructor
public class BlockApiController {
    private final BlockService blockService;
    @PostMapping
    public ResponseDto<BlockResponse> save(@RequestBody AddBlockRequest request, Authentication authentication) {
        BlockResponse response = blockService.save(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }
    @GetMapping("/all")
    public ResponseDto<BlockListResponse> getBlockList(Authentication authentication, Long cursor) {
        BlockListResponse response = blockService.getBlockedList(authentication.getName(), cursor);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/count")
    public ResponseDto<BlockCountResponse> getCount(Authentication authentication) {
        BlockCountResponse response = blockService.getCount(authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @DeleteMapping
    public ResponseDto delete(@PathVariable String userId, Authentication authentication) {
        blockService.delete(userId, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}