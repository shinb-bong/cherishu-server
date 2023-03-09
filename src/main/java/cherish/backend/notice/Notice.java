package cherish.backend.notice;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class Notice {
    @Id
    @GeneratedValue
    private Long id;
    private String title; // 제목
    private String content; // 내용
    private String created_date; // 작성일자
}
