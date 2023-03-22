package cherish.backend.member.dto;

import lombok.Data;

@Data
public class EmailCodeRequest {
    private String email;
    private String code;
}
