package cherish.backend.auth.jwt;

import cherish.backend.auth.jwt.dto.TokenResponseDto;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TokenInfo {

    private String grantType;
    private String accessToken;
    private String refreshToken;

    public TokenResponseDto toResponseDto() {
        return TokenResponseDto.builder()
            .accessToken(accessToken)
            .build();
    }
}
