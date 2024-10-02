package pagether.domain.note.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pagether.domain.news.presentation.constant.ResponseMessage;
import pagether.domain.note.application.NoteService;
import pagether.domain.note.dto.CommentDTO;
import pagether.domain.note.dto.NoteDTO;
import pagether.domain.note.dto.req.AddNoteRequest;
import pagether.domain.note.dto.req.UpdateNoteRequest;
import pagether.domain.note.dto.res.NoteContentResponse;
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
    public ResponseDto<NoteResponse> save(@RequestPart(required = false) MultipartFile pic,@RequestPart AddNoteRequest request, Authentication authentication) {
        NoteResponse response = noteService.save(request, authentication.getName(), pic);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @GetMapping("/{id}")
    public ResponseDto<NoteContentResponse> get(@PathVariable Long id, Authentication authentication) {
        NoteContentResponse response = noteService.get(id, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/{discussionId}")
    public ResponseDto<List<CommentDTO>> getComment(@PathVariable Long discussionId, Authentication authentication) {
        List<CommentDTO> responses = noteService.getComment(discussionId, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), responses);
    }

    @PutMapping("/{id}")
    public ResponseDto update(@RequestBody UpdateNoteRequest request, @PathVariable Long id, Authentication authentication) {
        noteService.update(request, id, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_UPDATE.getMessage());
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

    @GetMapping("/hearted")
    public ResponseDto<List<NoteDTO>> getHeartedNotes(@RequestParam String userId) {
        List<NoteDTO> response = noteService.getHeartedNotes(userId);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Long id, Authentication authentication) {
        noteService.delete(id, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}