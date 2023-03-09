package cherish.backend.mothlyBoard;

import cherish.backend.item.Item;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@ToString
public class MonthlyBoard {

    @Id @GeneratedValue
    private String id;
    private String monthlyDate; // 공지 날짜
    private String title; // 제목
    private String subtitle; //부 제목
//    @OneToMany(mappedBy = "month")
//    private List<Item> itemList = new ArrayList<>(); // 월별 추천 아이템 리스트
}
