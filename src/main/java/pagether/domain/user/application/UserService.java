package pagether.domain.user.application;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pagether.domain.block.application.BlockService;
import pagether.domain.follow.application.FollowService;
import pagether.domain.image.application.ImageService;
import pagether.domain.oauth.application.OAuthService;
import pagether.domain.oauth.domain.KakaoUserInfo;
import pagether.domain.oauth.dto.req.KakaoSignUpRequest;
import pagether.domain.user.domain.Role;
import pagether.domain.user.domain.User;
import pagether.domain.user.dto.req.*;
import pagether.domain.user.dto.res.UserInfoResponse;
import pagether.domain.user.dto.res.UserResponse;
import pagether.domain.user.exception.DuplicateUserIdException;
import pagether.domain.user.exception.IncorrectPasswordException;
import pagether.domain.user.exception.UserNotFountException;
import pagether.domain.user.repository.UserRepository;
import pagether.global.config.exception.UnauthorizedAccessException;
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

    private final FollowService followService;
    private final BlockService blockService;

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

        String id = UUID.randomUUID().toString();
        User user = User.builder()
                .id(id)
                .userId(email)
                .accountName(request.getAccountName())
                .nickName(request.getNickname())
                .imgPath(DEFAULT_IMAGE)
                .role(Role.USER)
                .bio("")
                .isHeartAlertEnabled(false)
                .isFollowAlertEnabled(false)
                .isCommentAlertEnabled(false)
                .isAccountPrivate(false)
                .lastSeenNewsId(0L)
                .lastSeenAlertId(0L)
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
        }
        else if (request.getAccountName() != null && userRepository.existsByAccountName(request.getAccountName())){
            user = userRepository.findByAccountName(request.getAccountName()).orElseThrow(UserNotFountException::new);
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
                .id(id)
                .userId(request.getEmail())
                .accountName(request.getAccountName())
                .nickName(request.getNickname())
                .passWord(encodedPassword)
                .imgPath(DEFAULT_IMAGE)
                .role(Role.USER)
                .bio("")
                .isHeartAlertEnabled(false)
                .isFollowAlertEnabled(false)
                .isCommentAlertEnabled(false)
                .isAccountPrivate(false)
                .lastSeenNewsId(0L)
                .lastSeenAlertId(0L)
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

    public UserResponse guestRegister(GuestSignUpRequest request) {
        String userId = UUID.randomUUID().toString();
        String id = UUID.randomUUID().toString();
        User user = User.builder()
                .id(id)
                .userId(userId)
                .accountName(request.getAccountName())
                .nickName(request.getNickname())
                .imgPath(DEFAULT_IMAGE)
                .role(Role.USER)
                .bio("")
                .isHeartAlertEnabled(false)
                .isFollowAlertEnabled(false)
                .isCommentAlertEnabled(false)
                .isAccountPrivate(false)
                .lastSeenNewsId(0L)
                .lastSeenAlertId(0L)
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

    public UserInfoResponse get(String ownerId, String userId) {
        if (!userRepository.existsUserByUserId(ownerId)) {
            throw new UserNotFountException();
        }
        User owner = userRepository.findByUserId(ownerId).get();
        User requester = userRepository.findByUserId(userId).get();

        Boolean isFollowed = followService.isFollowed(owner, requester);
        Boolean isFollowing = followService.isFollowed(requester, owner);
        Boolean isBlocked = blockService.isBlocked(requester, owner);
        Boolean isBlocking = blockService.isBlocked(owner, requester);
        return new UserInfoResponse(owner, isFollowed, isFollowing, isBlocked, isBlocking);
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

    public UserResponse updateAccountName(String userId, UpdateUserRequest request) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        if (userRepository.existsByAccountName(request.getAccountName())){
            throw new IllegalArgumentException();
        }
        user.setAccountName(request.getAccountName());
        User updatedUser = userRepository.save(user);
        return new UserResponse(updatedUser);
    }

    public UserResponse updateNickName(String userId, UpdateUserRequest request) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        user.setNickName(request.getNickname());
        User updatedUser = userRepository.save(user);
        return new UserResponse(updatedUser);
    }

    public UserResponse updateBio(String userId, UpdateUserRequest request) {
        User user = userRepository.findByUserId(userId).orElseThrow(UserNotFountException::new);
        user.setBio(request.getBio());
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