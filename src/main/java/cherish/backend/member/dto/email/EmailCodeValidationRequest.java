package cherish.backend.member.dto.email;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailCodeValidationRequest {
    @Email
    private String email;

    @Length(min = 6, max = 6)
    private String code;
}
