package pagether.domain.user.application;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import pagether.domain.image.application.ImageService;
import pagether.domain.oauth.application.OAuthService;
import pagether.domain.oauth.domain.KakaoUserInfo;
import pagether.domain.user.domain.Role;
import pagether.domain.user.domain.User;
import pagether.domain.user.dto.req.UpdateUserRequest;
import pagether.domain.user.dto.res.UserResponse;
import pagether.domain.user.dto.res.UserSearchResponse;
import pagether.domain.user.exception.DuplicateUserIdException;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;
import pagether.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final OAuthService oAuthService;
    private final ImageService imageService;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String DEFAULT_IMAGE = "default.png";
    public static final long EXPIRATION_TIME = 60 * 60 * 1000L;
    private static final String EMAIL_KEYWORD = "E";
    private static final String PHONE_NUMBER_KEYWORD = "P";


    public UserResponse kakaoLogin(String code) throws Exception {
        String token = oAuthService.getKakaoAccessToken(code);
        KakaoUserInfo kakaoUserInfo = oAuthService.getKakaoUserInfo(token);

        String email = kakaoUserInfo.getEmail();
        String phoneNumber = kakaoUserInfo.getPhoneNumber();

        if (!userRepository.existsUserByUserId(email)) {
            redisTemplate.opsForValue().set(
                    EMAIL_KEYWORD + code,
                    email,
                    EXPIRATION_TIME,
                    TimeUnit.MILLISECONDS
            );

            redisTemplate.opsForValue().set(
                    PHONE_NUMBER_KEYWORD + code,
                    phoneNumber,
                    EXPIRATION_TIME,
                    TimeUnit.MILLISECONDS
            );
            throw new UserNotFountException();
        }
        User user = userRepository.findByUserId(email).get();
        UserResponse signResponse = UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .nickname(user.getNickName())
                .roles(user.getRole())
                .token(jwtProvider.createToken(user.getUserId(), user.getRole()))
                .build();
        return signResponse;
    }

    public UserResponse kakaoRegister(String nickname, String code) {
        String email = redisTemplate.opsForValue().get(EMAIL_KEYWORD + code);
        String phoneNumber = redisTemplate.opsForValue().get(PHONE_NUMBER_KEYWORD + code);
        if (userRepository.existsUserByUserId(email)) {
            throw new DuplicateUserIdException();
        }

        String id = UUID.randomUUID().toString();
        User user = User.builder()
                .id(id)
                .userId(email)
                .phone(phoneNumber)
                .nickName(nickname)
                .imgPath(DEFAULT_IMAGE)
                .role(Role.USER)
                .lastSeenNewsId(0L)
                .lastSeenAlertId(0L)
                .build();
        userRepository.save(user);
        UserResponse signResponse = UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .nickname(user.getNickName())
                .roles(user.getRole())
                .token(jwtProvider.createToken(user.getUserId(), user.getRole()))
                .build();
        return signResponse;
    }

    public UserResponse get(String id) {
        if (!userRepository.existsUserById(id)) {
            throw new UserNotFountException();
        }
        User user = userRepository.findById(id).get();
        return new UserResponse(user);
    }

    public List<UserSearchResponse> search(String keyword) {
        List<UserSearchResponse> responses = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 5);
        List<User> users = userRepository.findAllByNickNameContaining(keyword, pageable);
        for(User user : users){
            UserSearchResponse response = new UserSearchResponse(user);
            responses.add(response);
        }
        return responses;
    }

    public UserResponse updateNicknameAndPhoto(String userId, MultipartFile pic, UpdateUserRequest request) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        if (pic != null) {
            String imageFileName = imageService.save(pic, false);
            user.setImgPath(imageFileName);
        }
        user.setNickName(request.getNickname());
        User updatedUser = userRepository.save(user);

        return new UserResponse(updatedUser);
    }

    public UserResponse withdrawal(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(IllegalArgumentException::new);
        user.setUserId("");
        user.setPhone("");
        User updatedUser = userRepository.save(user);

        return new UserResponse(updatedUser);
    }

}