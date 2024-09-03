package pagether.global.config.security;

import pagether.domain.user.domain.Role;
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

    private final long exp = 48000L * 60 * 60;

    private static final String ROLE_KEY = "role";

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public String createToken(String userid, Role roles) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + exp);
        return Jwts.builder()
                .setSubject(userid)
                .claim(ROLE_KEY, roles.getRole())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
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
        List<GrantedAuthority> authoritie = new ArrayList<>();
        authoritie.add(new SimpleGrantedAuthority(role));
        return authoritie;
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

    public Claims parseClaims(String accessToken) {
        try {
            JwtParser parser = Jwts.parser().setSigningKey(secretKey);
            return parser.parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}