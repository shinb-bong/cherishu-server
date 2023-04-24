package cherish.backend.item.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemSearchCondition {
    private String keyword;
    private List<String> categoryName; // 1개 이상 가능
    private String jobName;
    private String situationName;
    private String gender;
}
