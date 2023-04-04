package cherish.backend.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginRequestDto {
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String password;
    @NotNull
    private boolean persist;
}
