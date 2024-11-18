package tagCloudock.domain.tag.presentation;

import tagCloudock.domain.tag.application.TagService;
import tagCloudock.domain.tag.dto.res.TagsResponse;
import tagCloudock.domain.tag.presentation.constant.ResponseMessage;
import tagCloudock.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/{stockName}")
    public ResponseDto<TagsResponse> getFromReader(@PathVariable String stockName) {
        TagsResponse response = tagService.getTags(stockName);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }
}