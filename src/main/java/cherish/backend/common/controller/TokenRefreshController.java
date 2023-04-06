package cherish.backend.common.controller;

import cherish.backend.auth.jwt.TokenInfo;
import cherish.backend.common.dto.RefreshTokenRequestDto;
import cherish.backend.common.service.TokenRefreshService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/public/token/refresh")
@RestController
public class TokenRefreshController {

    private final TokenRefreshService tokenRefreshService;

    @PostMapping
    public TokenInfo refreshToken(@Valid @RequestBody RefreshTokenRequestDto dto) {
        return tokenRefreshService.refreshToken(dto.getRefreshToken());
    }
}
