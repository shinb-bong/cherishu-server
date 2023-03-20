package cherish.backend.member.model;

import cherish.backend.common.model.BaseTimeEntity;
import cherish.backend.member.dto.MemberFormDto;
import cherish.backend.member.model.enums.Gender;
import cherish.backend.member.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;
    private String name; // 이름
    private String nickName; // 닉네임
    private String email; // 이메일
    private String password; // 패스워드
    private boolean informationCheck; // 광고성 동의
    // 추가 정보
    @Enumerated(EnumType.STRING)
    private Gender gender; // 성별
    private LocalDate brith; // 생일

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id")
    private Job job;
    // 생성 메소드
    @Enumerated(EnumType.STRING)
    private Role roles;

    public static Member createMember(MemberFormDto formDto, PasswordEncoder passwordEncoder){
        return Member.builder()
                .name(formDto.getName())
                .nickName(formDto.getNickName())
                .email(formDto.getEmail())
                .password(passwordEncoder.encode(formDto.getPassword()))
                .informationCheck(formDto.isInfoCheck())
                .gender(formDto.getGender())
                .brith(formDto.getBrith())
                .roles(Role.USER)
                .build();
    }

    // 유틸 메소드
    // 비밀번호 변경
    public void changePwd(String pwd, PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(pwd);
    }
}
