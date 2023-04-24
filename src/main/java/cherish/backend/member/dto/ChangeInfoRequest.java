package cherish.backend.member.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ChangeInfoRequest {
    @Size(min = 3, max = 10)
    @NotEmpty
    private String nickname;

    @NotEmpty
    private String jobName;

    private String oldPassword;

    private String newPassword;
}
