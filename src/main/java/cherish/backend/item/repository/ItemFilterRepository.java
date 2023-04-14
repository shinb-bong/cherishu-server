package cherish.backend.item.repository;

import cherish.backend.item.model.ItemFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemFilterRepository extends JpaRepository<ItemFilter, Long>, ItemFilterRepositoryCustom, QuerydslPredicateExecutor<ItemFilter> {
    @Query("select i from ItemFilter i join i.filter f where i.name = :name and f.id = :filterId")
    List<ItemFilter> findItemFilterByNameAndFilterId(@Param("name") String name, @Param("filterId") Long filterId);

    @Query("SELECT ite.name FROM ItemFilter ite JOIN ite.filter fi JOIN ite.item a WHERE fi.id = :filterId AND a.id = :itemId")
    List<String> findItemFilterNameByFilterId(@Param("itemId") Long itemId, @Param("filterId") Long filterId);
}
