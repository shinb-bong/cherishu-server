package cherish.backend.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@AllArgsConstructor
@Data
public class MemberFormDto {
    private String name; // 이름
    private String nickName; // 닉네임
    private String email; // 이메일
    private String password; // 패스워드
    private boolean infoCheck; // 광고성 동의
    // 추가 정보
    private String gender; // 성별
    private LocalDate birth; // 생일
    private String job;

}
