package cherish.backend.item.repository;

import cherish.backend.category.model.Category;
import cherish.backend.item.model.Item;
import cherish.backend.item.model.ItemCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCategoryRepository extends JpaRepository<ItemCategory, Long> {
    boolean existsByItemAndCategory(Item item, Category category);
}
