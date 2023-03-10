package cherish.backend.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MemberEmailResponse {
    private String email;
    private Boolean isMember;
}
