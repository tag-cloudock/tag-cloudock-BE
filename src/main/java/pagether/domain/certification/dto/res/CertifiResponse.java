package pagether.domain.certification.dto.res;

import pagether.domain.certification.domain.Certification;
import pagether.domain.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CertifiResponse {

    private Long certiId;
    private User user;
    private String name;
    private String studentIdNumber;
    private LocalDateTime requestAt; // requestedAt
    private String imgPath = "default.png";

    @Builder
    public CertifiResponse(Certification certification) {
        certiId = certification.getCertiId();
        user = certification.getUser();
        name = certification.getName();
        studentIdNumber = certification.getStudentIdNumber();
        requestAt = certification.getRequestAt();
        imgPath = certification.getImgPath();
    }
}