package cherish.backend.member.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("유저"), ADMIN("관리자"), DEL("삭제");

    private final String roleName;
}