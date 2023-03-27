package cherish.backend.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ChangePwdRequest {
    private final String pwd;
    private final String email;
}
