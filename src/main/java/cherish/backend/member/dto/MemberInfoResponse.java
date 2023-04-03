package cherish.backend.member.dto;

import cherish.backend.member.model.Job;
import cherish.backend.member.model.Member;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Optional;

@Data
@Builder
public class MemberInfoResponse {
    private String name; // 이름
    private String nickName; // 닉네임
    private String email; // 이메일
    private String gender; // 성별
    private LocalDate birth; // 생일
    private String job;

    public static MemberInfoResponse of(Member member, Optional<Job> job){
        return MemberInfoResponse.builder().name(member.getName())
                .nickName(member.getNickName())
                .email(member.getEmail())
                .gender(member.getGender().toString())
                .birth(member.getBirth())
                .job((member.getJob() == null) ? "입력 사항 없음" : member.getJob().getName())
                .build();
    }
}