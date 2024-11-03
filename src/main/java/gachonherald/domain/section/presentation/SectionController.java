package gachonherald.domain.section.presentation;

import gachonherald.domain.article.dto.res.ArticleResponse;
import gachonherald.domain.article.presentation.constant.ResponseMessage;
import gachonherald.domain.section.application.SectionService;
import gachonherald.domain.section.dto.req.AddSectionRequest;
import gachonherald.domain.section.dto.res.SectionResponse;
import gachonherald.domain.section.dto.res.SectionsResponse;
import gachonherald.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/sections")
@RequiredArgsConstructor
public class SectionController {
    private final SectionService sectionService;

    @PostMapping
    public ResponseDto<ArticleResponse> save(@RequestPart AddSectionRequest request) {
        sectionService.save(request);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage());
    }

    @GetMapping("{sectionId}")
    public ResponseDto<SectionResponse> get(@PathVariable Long sectionId) {
        SectionResponse response = sectionService.get(sectionId);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/list")
    public ResponseDto<SectionsResponse> getList() {
        SectionsResponse response = sectionService.getList();
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }
}