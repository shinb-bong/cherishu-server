package cherish.backend.item.repository;

import cherish.backend.item.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ItemRepository extends JpaRepository<Item,Long>, ItemRepositoryCustom, QuerydslPredicateExecutor<Item> {

}
