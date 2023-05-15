package cherish.backend.board.repository;

import cherish.backend.board.dto.query.MonthlyCurationQueryDto;
import cherish.backend.board.dto.query.QMonthlyCurationQueryDto;
import cherish.backend.board.dto.query.condition.MonthlyCurationCondition;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cherish.backend.board.model.QMonthlyBoard.monthlyBoard;
import static cherish.backend.board.model.QMonthlyBoardItem.monthlyBoardItem;
import static cherish.backend.item.model.QItem.item;
import static cherish.backend.item.model.QItemLike.itemLike;

@Slf4j
@RequiredArgsConstructor
@Repository
public class MonthlyBoardRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Transactional(readOnly = true)
    public List<MonthlyCurationQueryDto> searchMonthlyCurationByYearAndMonth(MonthlyCurationCondition condition) {
        if (condition.getMember() == null) {
            return queryFactory
                .select(new QMonthlyCurationQueryDto(
                        monthlyBoard.id,
                        monthlyBoard.title,
                        monthlyBoard.subTitle,
                        monthlyBoard.imgUrl,
                        item.id,
                        item.name,
                        item.brand,
                        item.description,
                        item.price,
                        item.imgUrl,
                        Expressions.asBoolean(false)
                    )
                )
                .from(monthlyBoardItem)
                .leftJoin(monthlyBoardItem.item, item)
                .leftJoin(monthlyBoardItem.monthlyBoard, monthlyBoard)
                .where(
                    monthlyBoard.year.eq(condition.getYear()),
                    monthlyBoard.month.eq(condition.getMonth())
                )
                .fetch();
        }
        return queryFactory
            .select(new QMonthlyCurationQueryDto(
                    monthlyBoard.id,
                    monthlyBoard.title,
                    monthlyBoard.subTitle,
                    monthlyBoard.imgUrl,
                    item.id,
                    item.name,
                    item.brand,
                    item.description,
                    item.price,
                    item.imgUrl,
                    JPAExpressions
                        .select(itemLike.id)
                        .from(itemLike)
                        .where(
                            itemLike.member.eq(condition.getMember()),
                            itemLike.item.id.eq(item.id)
                        )
                        .exists()
                )
            )
            .from(monthlyBoardItem)
            .leftJoin(monthlyBoardItem.item, item)
            .leftJoin(monthlyBoardItem.monthlyBoard, monthlyBoard)
            .where(
                monthlyBoard.year.eq(condition.getYear()),
                monthlyBoard.month.eq(condition.getMonth())
            )
            .fetch();
    }
}
