package cherish.backend.member.dto;

import lombok.Data;

@Data
public class ChangePwdRequest {
    private final String password;
    private final String email;
}
