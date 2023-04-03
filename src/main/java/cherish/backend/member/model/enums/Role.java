package cherish.backend.member.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ROLE_USER("유저"), ROLE_ADMIN("관리자"), ROLE_DEL("삭제");

    private final String roleName;
}