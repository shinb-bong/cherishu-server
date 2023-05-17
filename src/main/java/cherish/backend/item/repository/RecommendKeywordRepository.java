package cherish.backend.item.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static cherish.backend.item.model.QRecommendKeyword.recommendKeyword;

@RequiredArgsConstructor
@Repository
public class RecommendKeywordRepository {
    private final JPAQueryFactory queryFactory;

    public List<String> getKeywords() {
        return queryFactory
            .select(recommendKeyword.value)
            .from(recommendKeyword)
            .fetch();
    }
}
