package cherish.backend.curation.dto;

import jakarta.persistence.Tuple;

public record CurationResponseDto(
   long id,
   String name,
   String brand,
   String description,
   String imgUrl,
   int price,
   String categoryName,
   String tagName,
   boolean like,
   int score
) {
    public static CurationResponseDto ofTuple(Tuple tuple) {
        return new CurationResponseDto(
            tuple.get(0, Long.class),
            tuple.get(1, String.class),
            tuple.get(2, String.class),
            tuple.get(3, String.class),
            tuple.get(4, String.class),
            tuple.get(5, Integer.class),
            tuple.get(6, String.class),
            tuple.get(7, String.class),
            tuple.get(8, Long.class) != null,
            tuple.get(9, Long.class).intValue()
        );
    }
}
