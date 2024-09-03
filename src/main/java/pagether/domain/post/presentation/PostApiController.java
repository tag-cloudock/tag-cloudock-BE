package pagether.domain.post.presentation;

import pagether.domain.post.application.PostService;
import pagether.domain.post.dto.req.AddPostRequest;
import pagether.domain.post.dto.req.UpdatePostRequest;
import pagether.domain.post.dto.res.PageResponse;
import pagether.domain.post.dto.res.PostResponse;
import pagether.domain.post.dto.res.PostsResponse;
import pagether.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static pagether.domain.post.presentation.constant.ResponseMessage.*;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @PostMapping
    public ResponseDto<PostResponse> save(@RequestPart AddPostRequest request, @RequestPart(required = false) MultipartFile pic, Authentication authentication) {
        PostResponse response = postService.save(request, authentication.getName(), pic);
        return ResponseDto.of(OK.value(), SUCCESS_CREATE.getMessage(), response);
    }

    @GetMapping("/{id}")
    public ResponseDto<PostResponse> get(@PathVariable Integer id) {
        PostResponse response = postService.get(id);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }

    @GetMapping("/user/{userId}")
    public ResponseDto<List<PostsResponse>> get3ByUser(@PathVariable String userId) {
        List<PostsResponse> responses = postService.get3ByUser(userId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @GetMapping("/all/location/{location}/{page}")
    public ResponseDto<PageResponse> getAllByLocation(@PathVariable String location, @PathVariable int page) {
        PageResponse responses = postService.getAllByLocation(location, page);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @GetMapping("/all/campus/{campus}/{page}")
    public ResponseDto<PageResponse> getAllPostByCampus(@PathVariable String campus, @PathVariable int page) {
        PageResponse responses = postService.getAllPostByCampus(campus, page);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @GetMapping("/news")
    public ResponseDto<List<PostsResponse>> get8(@RequestParam(name = "campus", required = false) String campus) {
        List<PostsResponse> responses = postService.get8(campus);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), responses);
    }

    @DeleteMapping("/{id}")
    public ResponseDto delete(@PathVariable Integer id) {
        postService.delete(id);
        return ResponseDto.of(OK.value(), SUCCESS_DELETE.getMessage());
    }

    @PutMapping("/{id}")
    public ResponseDto update(@PathVariable Integer id, @RequestBody UpdatePostRequest request) {
        postService.update(id, request);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @PutMapping("/done/{id}/{lenderId}/{borrowerId}")
    public ResponseDto<PostResponse> doneAndCount(@PathVariable Integer id, @PathVariable String lenderId, @PathVariable String borrowerId) {
        postService.doneAndCount(id, lenderId, borrowerId);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }

    @PutMapping("/done/{id}")
    public ResponseDto<PostResponse> done(@PathVariable Integer id) {
        postService.done(id);
        return ResponseDto.of(OK.value(), SUCCESS_UPDATE.getMessage());
    }
}
