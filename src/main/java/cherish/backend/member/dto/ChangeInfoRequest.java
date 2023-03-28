package cherish.backend.member.dto;

import lombok.Data;

@Data
public class ChangeInfoRequest {
    private String memberEmail;
    private String nickName; // 닉네임
    // 추가 정보
    private String jobName;
}
