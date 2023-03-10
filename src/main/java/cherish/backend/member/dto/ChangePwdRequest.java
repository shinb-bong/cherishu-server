package cherish.backend.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePwdRequest {
    private String pwd;
    private String email;
}
