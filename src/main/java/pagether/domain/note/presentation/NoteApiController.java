package pagether.domain.note.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pagether.domain.news.presentation.constant.ResponseMessage;
import pagether.domain.note.application.NoteService;
import pagether.domain.note.dto.req.AddNoteRequest;
import pagether.domain.note.dto.res.NoteResponse;
import pagether.global.config.dto.ResponseDto;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteApiController {
    private final NoteService noteService;

    @PostMapping
    public ResponseDto<NoteResponse> save(@RequestBody AddNoteRequest request) {
        NoteResponse response = noteService.save(request);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

//    @GetMapping()
//    public ResponseDto<SeparatedNewsResponse> getAll(Authentication authentication) {
//        SeparatedNewsResponse response = noteService.getAll(authentication.getName());
//        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
//    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}