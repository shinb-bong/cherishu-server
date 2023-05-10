package cherish.backend.board.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class BoardResponseDto {
    private String title;
    private String content;
    private LocalDate createdDate;
}
