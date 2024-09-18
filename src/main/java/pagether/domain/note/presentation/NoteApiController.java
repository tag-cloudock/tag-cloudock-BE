package pagether.domain.note.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pagether.domain.news.presentation.constant.ResponseMessage;
import pagether.domain.note.application.NoteService;
import pagether.domain.note.dto.NoteDTO;
import pagether.domain.note.dto.req.AddNoteRequest;
import pagether.domain.note.dto.res.NoteResponse;
import pagether.global.config.dto.ResponseDto;
import org.springframework.security.core.Authentication;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/notes")
@RequiredArgsConstructor
public class NoteApiController {
    private final NoteService noteService;

    @PostMapping
    public ResponseDto<NoteResponse> save(@RequestBody AddNoteRequest request, Authentication authentication) {
        NoteResponse response = noteService.save(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @GetMapping("/by-user")
    public ResponseDto<List<NoteDTO>> getNotesByUser(@RequestParam String type, @RequestParam String userId, Authentication authentication) {
        List<NoteDTO> response = noteService.getNotesByUser(type, userId, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/by-book")
    public ResponseDto<List<NoteDTO>> getNotesByBook(@RequestParam String type, @RequestParam String isbn, Authentication authentication) {
        List<NoteDTO> response = noteService.getNotesByBook(type, isbn, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Long id) {
        noteService.delete(id);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}