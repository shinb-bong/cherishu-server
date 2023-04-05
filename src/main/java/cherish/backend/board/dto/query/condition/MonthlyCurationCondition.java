package cherish.backend.board.dto.query.condition;

import cherish.backend.member.model.Member;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MonthlyCurationCondition {
    private int year;
    private int month;
    private Member member;
}
