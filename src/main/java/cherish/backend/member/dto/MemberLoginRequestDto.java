package cherish.backend.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MemberLoginRequestDto {
    @NotEmpty
    @Email
    private final String email;
    @NotEmpty
    private final String password;
    @NotNull
    private final boolean isPersist;
}
