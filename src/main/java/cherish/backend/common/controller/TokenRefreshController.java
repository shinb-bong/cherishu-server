package cherish.backend.common.controller;

import cherish.backend.auth.jwt.JwtProperties;
import cherish.backend.auth.jwt.dto.TokenResponseDto;
import cherish.backend.common.service.TokenRefreshService;
import cherish.backend.common.util.DurationShortFormUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static cherish.backend.common.constant.CommonConstants.REFRESH_TOKEN_COOKIE_NAME;

@RequiredArgsConstructor
@RequestMapping("/public/token/refresh")
@RestController
public class TokenRefreshController {

    private final TokenRefreshService tokenRefreshService;
    private final JwtProperties jwtProperties;

    @PostMapping
    public TokenResponseDto refreshToken(
        @CookieValue(value = REFRESH_TOKEN_COOKIE_NAME) Cookie refreshTokenCookie,
        HttpServletResponse response) {
        var token = tokenRefreshService.refreshToken(refreshTokenCookie.getValue());
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN_COOKIE_NAME, token.getRefreshToken())
            .maxAge(DurationShortFormUtils.convertShortFormToMillis(jwtProperties.getRefreshTokenExpireTime()))
            .path("/")
            .secure(true)
            .httpOnly(true)
            .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return token.toResponseDto();
    }
}
