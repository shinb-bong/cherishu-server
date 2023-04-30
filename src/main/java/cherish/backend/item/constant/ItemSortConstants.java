package cherish.backend.item.constant;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class ItemSortConstants {

    public static final String MOST_RECOMMENDED = "추천";
    public static final String MOST_POPULAR = "인기";
    public static final String LATEST = "최신";
    public static final String MOST_EXPENSIVE = "고가";
    public static final String LEAST_EXPENSIVE = "저가";

    public static final List<String> SORT_OPTIONS = List.of(MOST_RECOMMENDED, MOST_POPULAR, LATEST, MOST_EXPENSIVE, LEAST_EXPENSIVE);
}
