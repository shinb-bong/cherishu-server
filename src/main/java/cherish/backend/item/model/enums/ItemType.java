package cherish.backend.item.model.enums;

public enum ItemType {
    Food("식품"), Beauty("뷰티"), Living("리빙/주방"), Digital("디지털/가전"), Clothes("의류/잡화"), ETC("기타");

    private final String name;
    ItemType(String name) {
        this.name = name;
    }
}
