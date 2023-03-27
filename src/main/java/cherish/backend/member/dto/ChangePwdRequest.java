package cherish.backend.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ChangePwdRequest {
    private final String pwd;
    private final String email;
    private final boolean check; // 이메일 인증코드 체크된 사람
}
