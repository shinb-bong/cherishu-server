package cherish.backend.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ChangePwdRequest {
    @Email
    private final String email;

    @NotEmpty
    private final String password;
}
