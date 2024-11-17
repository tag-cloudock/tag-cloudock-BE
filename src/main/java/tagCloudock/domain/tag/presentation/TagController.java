package tagCloudock.domain.tag.presentation;

import tagCloudock.domain.tag.application.TagService;
import tagCloudock.domain.tag.dto.res.TagsResponse;
import tagCloudock.domain.tag.presentation.constant.ResponseMessage;
import tagCloudock.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/{stockId}")
    public ResponseDto<TagsResponse> getFromReader(@PathVariable int stockId) {
        TagsResponse response = tagService.getTags(stockId);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }
}