package pagether.domain.post.dto.res;

import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostsResponse {
    private Integer postId;
    private String title;
    private String location;
    private String locationDetail;
    private Long rentalFee;
    private String security;
    private LocalDateTime createdAt;
    private Integer chatCount;
    private boolean isClose;
    private String userId;
    private String postImgPath;
}
