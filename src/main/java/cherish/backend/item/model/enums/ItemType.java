package cherish.backend.item.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemType {
    FOOD("식품"), BEAUTY("뷰티"), LIVING("리빙/주방"), DIGITAL("디지털/가전"), CLOTHES("의류/잡화"), ETC("기타");

    private final String description;
}
