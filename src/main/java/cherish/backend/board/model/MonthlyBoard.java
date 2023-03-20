package cherish.backend.board.model;

import cherish.backend.common.model.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
@Table(name = "monthly_board")
public class MonthlyBoard extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    private int year; // 공지 연도
    private int month; // 공지 달

    private String title; // 제목
    private String subTitle; //부 제목
    private String imgUrl; // 배너 사진

}
