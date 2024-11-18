package tagCloudock.domain.user.application;

import tagCloudock.domain.user.dto.res.*;
import tagCloudock.domain.image.application.ImageService;
import tagCloudock.domain.oauth.application.OAuthService;
import tagCloudock.domain.oauth.domain.KakaoUserInfo;
import tagCloudock.domain.oauth.dto.req.KakaoSignUpRequest;
import tagCloudock.domain.user.domain.Role;
import tagCloudock.domain.user.domain.User;
import tagCloudock.domain.user.exception.DuplicateUserIdException;
import tagCloudock.domain.user.exception.UserNotFountException;
import tagCloudock.domain.user.repository.UserRepository;
import tagCloudock.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public static final long EXPIRATION_TIME = 60 * 60 * 1000L;
    private static final String EMAIL_KEYWORD = "E";


    public UserResponse kakaoLogin(String code) throws Exception {
        String token = oAuthService.getKakaoAccessToken(code);
        KakaoUserInfo kakaoUserInfo = oAuthService.getKakaoUserInfo(token);
        String email = kakaoUserInfo.getEmail();
        System.out.println(email);
        if (!userRepository.existsUserByUserId(email)) {
            redisTemplate.opsForValue().set(
                    EMAIL_KEYWORD + code,
                    email,
                    EXPIRATION_TIME,
                    TimeUnit.MILLISECONDS
            );
            throw new UserNotFountException();
        }
        User user = userRepository.findByUserId(email).get();
        UserResponse signResponse = UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .accessToken(jwtProvider.createAccessToken(user.getUserId(), user.getRole()))
                .refreshToken(jwtProvider.createRefreshToken(user.getUserId()))
                .build();
        return signResponse;
    }

    public UserResponse kakaoRegister(KakaoSignUpRequest request) {
        String email = redisTemplate.opsForValue().get(EMAIL_KEYWORD + request.getCode());
        if (userRepository.existsUserByUserId(email)) {
            throw new DuplicateUserIdException();
        }

        User user = User.builder()
                .userId(email)
                .role(Role.USER)
                .build();
        userRepository.save(user);
        UserResponse signResponse = UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .accessToken(jwtProvider.createAccessToken(user.getUserId(), user.getRole()))
                .refreshToken(jwtProvider.createRefreshToken(user.getUserId()))
                .build();
        return signResponse;
    }

}