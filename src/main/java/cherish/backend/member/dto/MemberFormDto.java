package cherish.backend.member.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class MemberFormDto {
    @NotEmpty
    private final String name; // 이름
    @NotEmpty
    private final String nickName; // 닉네임
    @NotEmpty
    private final String email; // 이메일
    @NotEmpty
    private final String password; // 패스워드
    @NotNull
    private final boolean infoCheck; // 광고성 동의
    // 추가 정보
    private final String gender; // 성별
    private final LocalDate birth; // 생일
    private final String job;

}
