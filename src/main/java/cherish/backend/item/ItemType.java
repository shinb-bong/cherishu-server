package cherish.backend.item;

import jakarta.persistence.Embeddable;

@Embeddable
public enum ItemType {
    Food("식품"), Beauty("뷰티"), Living("리빙/주방"), Digital("디지털/가전"), Clothes("의류/잡화"), ETC("기타");
    private final String itemType;
    ItemType(String itemType) {
        this.itemType = itemType;
    }

    public String itemType() {
        return itemType;
    }
}
