package cherish.backend.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ChangeInfoRequest {
    @NotEmpty
    private String nickName; // 닉네임
    // 추가 정보
    private String jobName;
}
