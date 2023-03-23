package cherish.backend.item.repository;

import cherish.backend.item.model.ItemFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ItemFilterRepository extends JpaRepository<ItemFilter, Long>, ItemFilterRepositoryCustom, QuerydslPredicateExecutor<ItemFilter> {


}
