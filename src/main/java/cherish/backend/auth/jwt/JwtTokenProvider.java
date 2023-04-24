package cherish.backend.auth.jwt;

import cherish.backend.auth.jwt.config.JwtConfig;
import cherish.backend.auth.jwt.config.JwtProperties;
import cherish.backend.common.service.RedisService;
import cherish.backend.member.service.CustomUserDetailService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@EnableConfigurationProperties(JwtProperties.class)
@Component
public class JwtTokenProvider {

    private final Key key;
    private final CustomUserDetailService service;
    private final RedisService redisService;
    private final JwtConfig jwtConfig;

    // 유저 정보를 가지고 AccessToken, RefreshToken 을 생성하는 메서드
    public TokenInfo generateToken(Authentication authentication) {
        // 권한 가져오기
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        // Access Token 생성
        String accessToken = generateAccessToken(authentication, authorities, now);
        // Refresh Token 생성
        String refreshToken = generateRefreshToken(authentication, now);

        redisService.setRefreshToken(authentication.getName(), refreshToken, jwtConfig.getRefreshTokenExpireMillis() / 1000);

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private String generateAccessToken(Authentication authentication, String authorities, long now) {
        return Jwts.builder()
            .setSubject(authentication.getName())
            .claim("auth", authorities)
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + jwtConfig.getAccessTokenExpireMillis()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    private String generateRefreshToken(Authentication authentication, long now) {
        return Jwts.builder()
            .setSubject(authentication.getName())
            .setIssuedAt(new Date(now))
            .setExpiration(new Date(now + jwtConfig.getRefreshTokenExpireMillis()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact();
    }

    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 메서드
    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        if (claims.get("auth") == null) {
            throw new JwtException("권한 정보가 없는 토큰입니다.");
        }

        // 클레임에서 권한 정보 가져오기
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get("auth").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();

        // UserDetails 객체를 만들어서 Authentication 리턴
        // 여기서 우리는 유틸리티 객체를 쓸 것이기 때문에
        // User를 상속받은 SecurityUser가 들어가게된다.
        UserDetails principal = service.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 토큰 정보를 검증하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            if (redisService.isLoggedOut(token)) {
                throw new JwtException("Invalid token");
            }
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new JwtException("Invalid token");
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token has been expired");
        } catch (UnsupportedJwtException e) {
            throw new JwtException("Unsupported jwt token");
        } catch (IllegalArgumentException e) {
            throw new JwtException("JWT claims string is empty.");
        }
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /**
     * 남은 유효기간 ms return
     * @param accessToken
     * @return
     */
    public long getExpiration(String accessToken) {
        Date expiration = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(accessToken)
            .getBody().getExpiration();
        // 만료기간 date에서 현재 date 뺀만큼 ms
        return expiration.getTime() - new Date().getTime();
    }
}
