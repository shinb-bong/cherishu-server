package cherish.backend.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberFormDto {
    @Size(min = 2, max = 20)
    @NotEmpty
    private String name; // 이름

    @Size(min = 3, max = 10)
    @NotEmpty
    private String nickname; // 닉네임

    @Email
    @NotEmpty
    private String email; // 이메일

    @Size(min = 8, max = 20)
    @NotEmpty
    private String password; // 패스워드

    @NotNull
    private boolean infoCheck; // 광고성 동의

    // 추가 정보
    private String gender; // 성별

    private LocalDate birth; // 생일

    private String job;
}
