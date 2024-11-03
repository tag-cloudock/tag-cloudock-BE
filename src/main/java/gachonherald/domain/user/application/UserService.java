package gachonherald.domain.user.application;

import gachonherald.domain.article.domain.Article;
import gachonherald.domain.article.dto.ArticleDTO;
import gachonherald.domain.user.domain.Position;
import gachonherald.domain.user.dto.ReporterDTO;
import gachonherald.domain.user.dto.res.ReporterResponse;
import gachonherald.domain.user.dto.res.ReportersResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import gachonherald.domain.image.application.ImageService;
import gachonherald.domain.oauth.application.OAuthService;
import gachonherald.domain.oauth.domain.KakaoUserInfo;
import gachonherald.domain.oauth.dto.req.KakaoSignUpRequest;
import gachonherald.domain.user.domain.Role;
import gachonherald.domain.user.domain.User;
import gachonherald.domain.user.dto.req.*;
import gachonherald.domain.user.dto.res.UserInfoResponse;
import gachonherald.domain.user.dto.res.UserResponse;
import gachonherald.domain.user.exception.DuplicateUserIdException;
import gachonherald.domain.user.exception.IncorrectPasswordException;
import gachonherald.domain.user.exception.UserNotFountException;
import gachonherald.domain.user.repository.UserRepository;
import gachonherald.global.config.exception.UnauthorizedAccessException;
import gachonherald.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
                .nickname(user.getNickName())
                .roles(user.getRole())
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
                .name(request.getName())
                .address(request.getAddress())
                .detailAddress(request.getDetailAddress())
                .nickName(request.getNickname())
                .phone(request.getPhone())
                .intro(request.getIntro())
                .imgPath(DEFAULT_IMAGE)
                .role(Role.READER)

                .build();
        userRepository.save(user);
        UserResponse signResponse = UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .nickname(user.getNickName())
                .roles(user.getRole())
                .accessToken(jwtProvider.createAccessToken(user.getUserId(), user.getRole()))
                .refreshToken(jwtProvider.createRefreshToken(user.getUserId()))
                .build();
        return signResponse;
    }

    public void emailCertification(EmailSignUpRequest request) {
    }

    public UserResponse emailLogin(EmailSignInRequest request) {
        User user;
        if (request.getEmail() != null && userRepository.existsUserByUserId(request.getEmail())){
            user = userRepository.findByUserId(request.getEmail()).orElseThrow(UserNotFountException::new);
        }else{
            throw new UserNotFountException();
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassWord())) {
            throw new IncorrectPasswordException();
        }

        UserResponse signResponse = UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .nickname(user.getNickName())
                .roles(user.getRole())
                .accessToken(jwtProvider.createAccessToken(user.getUserId(), user.getRole()))
                .build();
        return signResponse;
    }

    public UserResponse emailRegister(EmailSignUpRequest request) {
        if (userRepository.existsUserByUserId(request.getEmail())) {
            throw new DuplicateUserIdException();
        }
        String id = UUID.randomUUID().toString();

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .userId(request.getEmail())
                .name(request.getName())
                .address(request.getAddress())
                .detailAddress(request.getDetailAddress())
                .nickName(request.getNickname())
                .phone(request.getPhone())
                .intro(request.getIntro())
                .imgPath(DEFAULT_IMAGE)
                .passWord(request.getPassword())
                .role(Role.READER)
                .build();
        userRepository.save(user);
        UserResponse signResponse = UserResponse.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .nickname(user.getNickName())
                .roles(user.getRole())
                .accessToken(jwtProvider.createAccessToken(user.getUserId(), user.getRole()))
                .build();
        return signResponse;
    }

    public void changePassword(ChangePasswordRequest request, String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(request.getPassword(), user.getPassWord())) {
            throw new IncorrectPasswordException();
        }
        String encodedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassWord(encodedPassword);
        userRepository.save(user);
    }


    public ReporterResponse getReporterInfo(Long reporterId) {
        User reporter = userRepository.findById(reporterId).orElseThrow(UserNotFountException::new);

        if (reporter.getRole().equals(Role.READER)){
            throw new UnauthorizedAccessException();
        }
        return new ReporterResponse(reporter);
    }

    public ReportersResponse getReporters() {
        List<ReporterDTO> response = new ArrayList<>();
        List<User> reporters = userRepository.findAllByIsCurrentMember(true);
        Map<Position, Integer> positionOrder = new HashMap<>();
        for (int i = 0; i < Position.values().length; i++) {
            positionOrder.put(Position.values()[i], i);
        }
        reporters = reporters.stream()
                .sorted(Comparator.comparingInt(user -> positionOrder.get(user.getPosition())))
                .collect(Collectors.toList());
        for(User reporter : reporters) response.add(new ReporterDTO(reporter));
        return new ReportersResponse(response);
    }

    public List<UserInfoResponse> search(String keyword) {
        List<UserInfoResponse> responses = new ArrayList<>();
        Pageable pageable = PageRequest.of(0, 5);
        List<User> users = userRepository.findAllByNickNameContaining(keyword, pageable);
        for(User user : users){
            UserInfoResponse response = new UserInfoResponse(user);
            responses.add(response);
        }
        return responses;
    }

    public UserResponse updateProfileImg(String userId, MultipartFile pic) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        if (pic != null) {
            String imageFileName = imageService.save(pic, false);
            user.setImgPath(imageFileName);
        }
        User updatedUser = userRepository.save(user);

        return new UserResponse(updatedUser);
    }

    public UserResponse deleteProfileImg(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        user.setImgPath(imageService.DEFAULT_IMAGE);
        User updatedUser = userRepository.save(user);
        return new UserResponse(updatedUser);
    }

    public UserResponse updateNickName(String userId, UpdateUserRequest request) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        user.setNickName(request.getNickname());
        User updatedUser = userRepository.save(user);
        return new UserResponse(updatedUser);
    }

    public UserResponse updateIntro(String userId, UpdateUserRequest request) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        user.setIntro(request.getIntro());
        User updatedUser = userRepository.save(user);
        return new UserResponse(updatedUser);
    }

    public void validateOwnership(String ownerId, String requesterId) {
        if (!ownerId.equals(requesterId))
            throw new UnauthorizedAccessException();
    }

    public Boolean isOwner(String ownerId, String requesterId) {
        if (ownerId.equals(requesterId))
            return true;
        return false;
    }

    public Boolean isOwner(User owner, User requester) {
        if (owner.getUserId().equals(requester.getUserId()))
            return true;
        return false;
    }


    public UserResponse withdrawal(String userId) {
        User user = userRepository.findByUserId(userId).orElseThrow(IllegalArgumentException::new);
        user.setUserId("");
        User updatedUser = userRepository.save(user);

        return new UserResponse(updatedUser);
    }


}