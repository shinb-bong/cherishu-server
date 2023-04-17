package cherish.backend.item.repository;

import cherish.backend.category.model.Category;
import cherish.backend.item.model.Item;
import cherish.backend.item.model.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
    boolean existsByItemAndCategory(Item item, Category category);

    @Query("SELECT ic.id FROM ItemCategory iC JOIN iC.category ic JOIN iC.item x WHERE x.id = :itemId")
    List<Long> findCategoryIdByItem(@Param("itemId") Long itemId);
}
