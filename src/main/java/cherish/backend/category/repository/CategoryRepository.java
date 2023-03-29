package cherish.backend.category.repository;

import cherish.backend.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c FROM Category c WHERE c.parent = :parent")
    List<Category> findCategoryByParent(@Param("parent") Category parent);

    Optional<Category> findByName(String name);

}
