package cherish.backend.member.dto;

import cherish.backend.member.sub.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class MemberFormDto {
    private String name; // 이름
    private String nickName; // 닉네임
    private String email; // 이메일
    private String password; // 패스워드
    private boolean info_check; // 광고성 동의
    // 추가 정보
    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별
    private LocalDateTime brith; // 생일
    private String job;

}
