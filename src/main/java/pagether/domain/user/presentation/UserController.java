package pagether.domain.user.presentation;

import pagether.domain.user.application.UserService;
import pagether.domain.user.dto.req.UpdateUserRequest;
import pagether.domain.user.dto.req.UserRequest;
import pagether.domain.user.dto.res.UserResponse;
import pagether.domain.user.dto.res.UserSearchResponse;
import pagether.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static pagether.domain.user.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

//    @PostMapping(value = "/login")
//    public ResponseDto<UserResponse> signin(@RequestBody UserRequest request) {
//        UserResponse response = userService.login(request);
//        return ResponseDto.of(OK.value(), SUCCESS_LOGIN.getMessage(), response);
//    }

    @GetMapping("/account")
    public ResponseDto<UserResponse> get(@RequestParam String id) {
        UserResponse response = userService.get(id);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/search")
    public ResponseDto<List<UserSearchResponse>> search(@RequestParam String keyword) {
        List<UserSearchResponse> responses = userService.search(keyword);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

//    @GetMapping("/admin")
//    public ResponseDto<UserResponse> getUserForAdmin(@RequestParam String id) {
//        return userService.getUser(id);
//    }

    @PutMapping("/account/update")
    public ResponseDto<UserResponse> updateNicknameAndPhoto(@RequestPart UpdateUserRequest request, @RequestPart(required = false) MultipartFile pic, Authentication authentication) {
        UserResponse response = userService.updateNicknameAndPhoto(authentication.getName(), pic, request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage(), response);
    }

    @PutMapping("/account/withdrawal")
    public ResponseDto<UserResponse> withdrawal(Authentication authentication) {
        userService.withdrawal(authentication.getName());
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }
}