package pagether.domain.post.dto.res;

import pagether.domain.post.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PostResponse {

    private Integer postId;
    private String title;
    private String location;
    private String locationDetail;
    private Long rentalFee;
    private String security;
    private LocalDateTime createdAt;
    private LocalDate needAt;
    private LocalDate returnAt;
    private String content;
    private Boolean isClose;
    private String nickname;
    private String userId;
    private String userImgPath;
    private String postImgPath;
    private boolean isCertification;
    private boolean isLenderWriteReview;
    private String lenderId;
    private Integer chatCount;

    @Builder
    public PostResponse(Post post, Integer chatCount) {
        postId = post.getPostId();
        title = post.getTitle();
        location = post.getLocation();
        locationDetail = post.getLocationDetail();
        rentalFee = post.getRentalFee();
        security = post.getSecurity();
        createdAt = post.getCreatedAt();
        needAt = post.getNeedAt();
        returnAt = post.getReturnAt();
        content = post.getContent();
        isClose = post.getIsClose();
        isLenderWriteReview = post.getIsLenderWriteReview();
        lenderId = post.getLenderId();
        nickname = post.getUser().getNickName();
        userId = post.getUser().getId();
        userImgPath = post.getUser().getImgPath();
        postImgPath = post.getImgPath();
        isCertification = post.getUser().isCertification();
        this.chatCount = chatCount;
    }

    @Builder
    public PostResponse(Post post) {
        postId = post.getPostId();
        title = post.getTitle();
        location = post.getLocation();
        locationDetail = post.getLocationDetail();
        rentalFee = post.getRentalFee();
        security = post.getSecurity();
        createdAt = post.getCreatedAt();
        needAt = post.getNeedAt();
        returnAt = post.getReturnAt();
        content = post.getContent();
        isClose = post.getIsClose();
        isLenderWriteReview = post.getIsLenderWriteReview();
        lenderId = post.getLenderId();
        nickname = post.getUser().getNickName();
        userId = post.getUser().getId();
        userImgPath = post.getUser().getImgPath();
        postImgPath = post.getImgPath();
        isCertification = post.getUser().isCertification();
    }
}
