package cherish.backend.member.dto.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class EmailRequest {

    @Email
    @NotEmpty
    private String email;
}
