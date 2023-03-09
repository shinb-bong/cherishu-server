package cherish.backend.member;

import cherish.backend.member.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String name; // 이름
    private String nickName; // 닉네임
    private String email; // 이메일
    private String password; // 패스워드
    private boolean info_check; // 광고성 동의
    private String created_date; // 생성시간
    private String modified_date; // 수정 시간
    // 추가 정보
    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별
    private LocalDateTime brith; // 생일
    private String job; // 직업
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String name, String nickName, String email, String password, boolean info_check, String created_date, String modified_date, Gender gender, LocalDateTime brith, String job, Role role) {
        this.name = name;
        this.nickName = nickName;
        this.email = email;
        this.password = password;
        this.info_check = info_check;
        this.created_date = created_date;
        this.modified_date = modified_date;
        this.gender = gender;
        this.brith = brith;
        this.job = job;
        this.role = role;
    }

    // 생성 메소드
    public static Member createMember(MemberFormDto formDto, PasswordEncoder passwordEncoder){
        return Member.builder()
                .name(formDto.getName())
                .nickName(formDto.getNickName())
                .email(formDto.getEmail())
                .password(passwordEncoder.encode(formDto.getPassword()))
                .info_check(formDto.isInfo_check())
                .created_date(String.valueOf(LocalDateTime.now()))
                .modified_date(String.valueOf(LocalDateTime.now()))
                .gender(formDto.getGender())
                .brith(formDto.getBrith())
                .job(formDto.getJob())
                .build();
    }
}
