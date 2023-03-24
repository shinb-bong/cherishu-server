package cherish.backend.member.dto.email;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmailCodeValidationRequest {
    @Email
    @NotEmpty
    private String email;

    @Length(min = 6, max = 6)
    private String code;

}
