package cherish.backend.board.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class MonthlyCurationRequestDto {
    @Range(max = 9999)
    private int year;

    @Range(min = 1, max = 12)
    private int month;
}
