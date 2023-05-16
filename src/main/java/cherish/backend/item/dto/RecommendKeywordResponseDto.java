package cherish.backend.item.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecommendKeywordResponseDto {
    private final List<String> keywords;
}
