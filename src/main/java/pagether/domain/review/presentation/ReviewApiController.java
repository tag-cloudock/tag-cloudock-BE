package pagether.domain.review.presentation;

import pagether.domain.review.application.ReviewService;
import pagether.domain.review.dto.req.AddReviewRequest;
import pagether.domain.review.dto.res.ReviewResponse;
import pagether.domain.review.dto.res.ReviewsResponse;
import pagether.global.config.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static pagether.domain.review.presentation.constant.ResponseMessage.SUCCESS_CREATE;
import static pagether.domain.review.presentation.constant.ResponseMessage.SUCCESS_READ;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewApiController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseDto<ReviewResponse> save(@RequestBody AddReviewRequest request) throws Exception {
        ReviewResponse response = reviewService.save(request);
        return ResponseDto.of(OK.value(), SUCCESS_CREATE.getMessage(), response);
    }

    @GetMapping("/{userId}")
    public ResponseDto<ReviewsResponse> get(@PathVariable String userId) throws Exception {
        ReviewsResponse response = reviewService.get(userId);
        return ResponseDto.of(OK.value(), SUCCESS_READ.getMessage(), response);
    }
}
