package pagether.global.config.security;

import pagether.domain.user.domain.Role;
import pagether.domain.user.dto.res.TokensResponse;
import pagether.global.config.security.exception.ExpiredTokenException;
import pagether.global.config.security.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String salt;

    private Key secretKey;

    private static final long ACCESS_TOKEN_EXPIRATION = 60 * 60 * 1000;
    private static final long REFRESH_TOKEN_EXPIRATION = 30 * 24 * 60 * 60 * 1000;

    private static final String ROLE_KEY = "role";

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(String userid, Role roles) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRATION);
        return Jwts.builder()
                .setSubject(userid)
                .claim(ROLE_KEY, roles.getRole())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(String userid) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + REFRESH_TOKEN_EXPIRATION);
        return Jwts.builder()
                .setSubject(userid)
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public TokensResponse refreshTokens(String refreshToken) {
        validateToken(refreshToken);
        String userId = getEmail(refreshToken);
        String newAccessToken = createAccessToken(userId, Role.USER);
        String newRefreshToken = createRefreshToken(userId);
        return new TokensResponse(newAccessToken, newRefreshToken);
    }

    public Authentication getAuthentication(String token) {
        return new PreAuthenticatedAuthenticationToken(this.getEmail(token), null, this.getRole(token));
    }

    public String getEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Collection<? extends GrantedAuthority> getRole(String token) {
        return convertToGrantedAuthorities(Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody().get(ROLE_KEY).toString());
    }

    private Collection<? extends GrantedAuthority> convertToGrantedAuthorities(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));
        return authorities;
    }

    public void validateToken(String token) {
        try {
            parseClaims(token);
        } catch (SignatureException | UnsupportedJwtException | IllegalArgumentException | MalformedJwtException e) {
            throw new InvalidTokenException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        }
    }


    public Claims parseClaims(String token) {
        try {
            JwtParser parser = Jwts.parser().setSigningKey(secretKey);
            return parser.parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
