package cherish.backend.item.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class ItemSearchCondition {
    private String keyword;
    private Set<String> categoryName; // 1개 이상 가능
    private String jobName;
    private String situationName;
    private String gender;
}
