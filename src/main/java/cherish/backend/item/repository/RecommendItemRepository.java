package cherish.backend.item.repository;

import cherish.backend.board.model.QMonthlyBoard;
import cherish.backend.item.dto.*;
import cherish.backend.member.model.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.IntStream;

import static cherish.backend.board.model.QMonthlyBoard.*;
import static cherish.backend.board.model.QMonthlyBoardItem.*;
import static cherish.backend.board.model.QRecommend.*;
import static cherish.backend.item.model.QItem.item;
import static cherish.backend.item.model.QItemLike.*;
import static cherish.backend.item.model.QRecommendItem.recommendItem;

@RequiredArgsConstructor
@Repository
public class RecommendItemRepository {
    private final JPAQueryFactory queryFactory;

    public List<RecommendItemQueryDto> getRecommendItemList(Member member) {
        BooleanExpression like = member != null ? new CaseBuilder().when(itemLike.member.eq(member)).then(true).otherwise(false) : Expressions.asBoolean(false);
        List<Integer> months = IntStream.rangeClosed(1, 12).boxed().toList();

        return queryFactory
                .select(new QRecommendItemQueryDto(
                        recommend.id, monthlyBoard.imgUrl, item.id, item.name, item.brand, item.price, monthlyBoard.id, item.imgUrl, like, recommend.title, recommend.subTitle)
                )
                .from(recommendItem)
                .leftJoin(recommendItem.item, item)
                .leftJoin(recommendItem.recommend, recommend)
                .leftJoin(item.monthlyBoardItems, monthlyBoardItem)
                .leftJoin(monthlyBoardItem.monthlyBoard, monthlyBoard)
                .where(
                        member != null ? itemLike.member.id.eq(member.getId()) : null,
                        monthlyBoard.id.in(
                                JPAExpressions
                                        .select(QMonthlyBoard.monthlyBoard.id.min())
                                        .from(QMonthlyBoard.monthlyBoard)
                                        .where(QMonthlyBoard.monthlyBoard.month.in(months))
                                        .groupBy(QMonthlyBoard.monthlyBoard.month)
                                )
                )
                .fetch();
    }
}
