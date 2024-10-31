package gachonherald.domain.article.application;

import gachonherald.domain.image.application.ImageService;
import gachonherald.domain.oauth.application.OAuthService;
import gachonherald.domain.oauth.domain.KakaoUserInfo;
import gachonherald.domain.oauth.dto.req.KakaoSignUpRequest;
import gachonherald.domain.user.domain.Role;
import gachonherald.domain.user.domain.User;
import gachonherald.domain.user.dto.req.ChangePasswordRequest;
import gachonherald.domain.user.dto.req.EmailSignInRequest;
import gachonherald.domain.user.dto.req.EmailSignUpRequest;
import gachonherald.domain.user.dto.req.UpdateUserRequest;
import gachonherald.domain.user.dto.res.UserInfoResponse;
import gachonherald.domain.user.dto.res.UserResponse;
import gachonherald.domain.user.exception.DuplicateUserIdException;
import gachonherald.domain.user.exception.IncorrectPasswordException;
import gachonherald.domain.user.exception.UserNotFountException;
import gachonherald.domain.user.repository.UserRepository;
import gachonherald.global.config.exception.UnauthorizedAccessException;
import gachonherald.global.config.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class ArticleService {

}