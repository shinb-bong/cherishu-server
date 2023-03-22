package cherish.backend.item.repository;

import cherish.backend.item.model.ItemFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemFilterRepository extends JpaRepository<ItemFilter, Long> {

    @Query("select i from ItemFilter i join i.filter f where i.name = :name and f.id = :filterId")
    List<ItemFilter> findItemFilterByNameAndFilterId(@Param("name") String name, @Param("filterId") Long filterId);

}
