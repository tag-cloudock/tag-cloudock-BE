package pagether.domain.readInfo.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pagether.domain.readInfo.dto.ReadFriendInfoDTO;
import pagether.domain.readInfo.dto.req.UpdatePageRequest;
import pagether.domain.readInfo.dto.req.UpdateStatusRequest;
import pagether.domain.readInfo.dto.res.ReadInfoByBookResponse;
import pagether.domain.readInfo.dto.res.ReadingAndPinedBooksResponse;
import pagether.domain.readInfo.dto.res.ReadingAndReadBooksResponse;
import pagether.domain.readInfo.presentation.constant.ResponseMessage;
import pagether.domain.readInfo.application.ReadInfoService;
import pagether.domain.readInfo.dto.req.AddReadInfoRequest;
import pagether.domain.readInfo.dto.res.ReadInfoResponse;
import pagether.global.config.dto.ResponseDto;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/read")
@RequiredArgsConstructor
public class ReadInfoApiController {

    private final ReadInfoService readInfoService;

    @PostMapping
    public ResponseDto<ReadInfoResponse> startRead(@RequestBody AddReadInfoRequest request, Authentication authentication) {
        ReadInfoResponse response = readInfoService.startRead(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @PostMapping("/pin")
    public ResponseDto<ReadInfoResponse> pin(@RequestBody AddReadInfoRequest request, Authentication authentication) {
        ReadInfoResponse response = readInfoService.pin(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @PatchMapping("/stop")
    public ResponseDto<ReadInfoResponse> stop(@RequestBody UpdateStatusRequest request, Authentication authentication) {
        ReadInfoResponse response = readInfoService.stop(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @PatchMapping("/done")
    public ResponseDto<ReadInfoResponse> done(@RequestBody UpdateStatusRequest request, Authentication authentication) {
        ReadInfoResponse response = readInfoService.done(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_CREATE.getMessage(), response);
    }

    @PatchMapping("/page")
    public ResponseDto<ReadInfoResponse> pageUpdate(@RequestBody UpdatePageRequest request, Authentication authentication) {
        ReadInfoResponse response = readInfoService.pageUpdate(request, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_UPDATE.getMessage(), response);
    }

    @GetMapping("/reading-and-pined")
    public ResponseDto<ReadingAndPinedBooksResponse> getReadingAndPinedBooks(Authentication authentication) {
        ReadingAndPinedBooksResponse response = readInfoService.getReadingAndPinedBooks(authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/reading-and-read")
    public ResponseDto<ReadingAndReadBooksResponse> getReadingAndReadBooks(@RequestParam String userId) {
        ReadingAndReadBooksResponse response = readInfoService.getReadingAndReadBooks(userId);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/friends")
    public ResponseDto<List<ReadFriendInfoDTO>> getFriends(@RequestParam String isbn, Authentication authentication) {
        List<ReadFriendInfoDTO> responses = readInfoService.getFriends(isbn, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), responses);
    }

    @GetMapping("/by-book")
    public ResponseDto<ReadInfoByBookResponse> getByBook(@RequestParam String isbn, @RequestParam String userId, Authentication authentication) {
        ReadInfoByBookResponse response = readInfoService.getByBook(isbn, userId, authentication.getName());
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_READ.getMessage(), response);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Long id) {
        readInfoService.delete(id);
        return ResponseDto.of(OK.value(), ResponseMessage.SUCCESS_DELETE.getMessage());
    }
}
