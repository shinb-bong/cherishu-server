package cherish.backend.category.model;

import cherish.backend.category.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class CategoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    void findCategoryByParent() {

            // Given
            Category parentCategory = new Category("Parent Category");
            Category childCategory1 = new Category("Child Category 1", parentCategory);
            Category childCategory2 = new Category("Child Category 2", parentCategory);
            Category otherCategory = new Category("Other Category");
            categoryRepository.saveAll(List.of(parentCategory, childCategory1, childCategory2, otherCategory));

            em.clear();

            Category persistedParentCategory = categoryRepository.findById(parentCategory.getId()).orElseThrow();

            // When
            List<Category> result = categoryRepository.findCategoryByParent(persistedParentCategory);

            // Then
            assertThat(result).hasSize(2);
            assertThat(result.stream().map(Category::getName))
                    .containsExactlyInAnyOrder(childCategory1.getName(), childCategory2.getName());

    }
}
