package cherish.backend.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class AgeFilterQueryDto {
    private Long itemId;
    private Long filterId;
    private Long itemFilterId;
    private String itemName;
    private String filterName;
    private String itemFilterName;
    private int minAge;
    private int maxAge;

    @QueryProjection
    public AgeFilterQueryDto(Long itemId, Long filterId, Long itemFilterId, String itemName, String filterName, String itemFilterName, int minAge, int maxAge) {
        this.itemId = itemId;
        this.filterId = filterId;
        this.itemFilterId = itemFilterId;
        this.itemName = itemName;
        this.filterName = filterName;
        this.itemFilterName = itemFilterName;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

}
