package pagether.domain.certification.application;

import pagether.domain.certification.domain.Certification;
import pagether.domain.certification.dto.req.CertifiRequest;
import pagether.domain.certification.dto.res.CertifiResponse;
import pagether.domain.certification.exception.CertifiNotFoundException;
import pagether.domain.certification.repository.CertificationRepository;
import pagether.domain.image.application.ImageService;
import pagether.domain.user.domain.User;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CertificationService {

    private final UserRepository userRepository;
    private final CertificationRepository certificationRepository;
    private final ImageService imageService;
    private final RedisTemplate<String, String> redisTemplate;
    public static final long EXPIRATION_TIME = 60 * 60 * 1000L;


    public CertifiResponse save(CertifiRequest request, MultipartFile pic, String userId) throws Exception {
        if (!userRepository.existsUserByUserId(userId)) {
            throw new UserNotFountException();
        }

        String imageFileName = imageService.save(pic, true);
        User user = userRepository.findByUserId(userId).get();
        Certification certification = Certification.builder()
                .user(user)
                .name(request.getName())
                .studentIdNumber(request.getStudentIdNumber())
                .requestAt(LocalDateTime.now())
                .imgPath(imageService.IMPORTANT_KEYWORD + imageFileName)
                .build();

        certificationRepository.save(certification);
        return new CertifiResponse(certification);
    }

    public CertifiResponse get(Long id) {
        if (!certificationRepository.existsById(id)) {
            throw new CertifiNotFoundException();
        }
        Certification certification = certificationRepository.findById(id).get();

        return new CertifiResponse(certification);
    }

    public List<CertifiResponse> getAll() {

        List<CertifiResponse> certifisDTO = new ArrayList<>();
        List<Certification> certifications = certificationRepository.findAllByOrderByRequestAtDesc();

        for (Certification certification : certifications) {
            CertifiResponse dto = new CertifiResponse();
            dto.setCertiId(certification.getCertiId());
            dto.setName(certification.getName());
            dto.setStudentIdNumber(certification.getStudentIdNumber());
            dto.setUser(certification.getUser());
            dto.setRequestAt(certification.getRequestAt());
            dto.setImgPath(certification.getImgPath());
            certifisDTO.add(dto);
        }
        return certifisDTO;
    }
}