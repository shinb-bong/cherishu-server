package cherish.backend.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeInfoRequest {
    @Email
    private String email;
    @NotEmpty
    private String nickName; // 닉네임
    // 추가 정보
    private String jobName;
}
