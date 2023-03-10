package cherish.backend.member.dto;

import lombok.Data;

@Data
public class MemberLoginRequestDto {
    private String email;
    private String password;
}
