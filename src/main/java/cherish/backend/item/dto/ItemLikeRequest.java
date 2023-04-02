package cherish.backend.item.dto;

import lombok.Data;

@Data
public class ItemLikeRequest {
    private final String email;
    private final Long itemId;
}
