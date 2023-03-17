package cherish.backend.auth.security;

import cherish.backend.member.model.Member;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

/**
 * 현재 사용자정보를 더 알 수있게
 * Member 자체를 넣은 유틸 클래스
 */
public class SecurityUser extends User {
    private final Member member;

    public SecurityUser(Member member) {
        super(member.getEmail(), member.getPassword(),
                AuthorityUtils.createAuthorityList(member.getRole().toString()));
        this.member = member;
    }

    public Member getMember() {
        return member;
    }
}
