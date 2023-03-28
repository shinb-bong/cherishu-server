package cherish.backend.test.data;

import cherish.backend.category.model.Category;
import cherish.backend.category.repository.CategoryRepository;
import cherish.backend.category.repository.FilterRepository;
import cherish.backend.item.model.Item;
import cherish.backend.item.model.ItemCategory;
import cherish.backend.item.repository.ItemCategoryRepository;
import cherish.backend.item.repository.ItemRepository;
import cherish.backend.member.repository.JobRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Profile("put-data")
@Slf4j
@RequiredArgsConstructor
@Component
public class DataParser {

    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;
    private final FilterRepository filterRepository;
    private final JobRepository jobRepository;
    private final ItemCategoryRepository itemCategoryRepository;

    @Transactional(rollbackOn = RuntimeException.class)
    public void read() {
        try (CSVReader csvReader = new CSVReader(new FileReader("data.csv"))) {
            List<DataRow> dataList = csvReader.readAll().stream()
                .map(DataRow::of)
                .toList();
            var categoryList = categoryRepository.findAll();
            var filterList = filterRepository.findAll();
            var jobList = jobRepository.findAll();
            var itemList = itemRepository.findAll();
            for (DataRow row : dataList) {
                // item table 저장된것 조회
                int price;
                int minAge;
                int maxAge;
                try {
                    price = Integer.parseInt(row.getPrice());
                } catch (NumberFormatException e) {
                    price = 0;
                }
                try {
                    minAge = Integer.parseInt(row.getMinAge());
                } catch (NumberFormatException e) {
                    minAge = 0;
                }
                try {
                    maxAge = Integer.parseInt(row.getMaxAge());
                } catch (NumberFormatException e) {
                    maxAge = 100;
                }
                Item item = itemRepository.save(
                    Item.builder()
                        .id(row.getId())
                        .name(row.getItem())
                        .brand(row.getBrand())
                        .description(row.getDescription())
                        .price(price)
                        .minAge(minAge)
                        .maxAge(maxAge)
                        .build()
                );
                // category 있는지 조회 후 없으면 저장
                Category category = categoryList.stream()
                    .filter(ct -> ct.getName().equalsIgnoreCase(row.getCategory()))
                    .findFirst()
                    .orElseGet(() -> categoryRepository.save(
                        Category.builder()
                            .name(row.getCategory())
                            .build()
                    ));
                // 이미 매핑된 item_category의 경우 제외하고 저장
                if (!itemCategoryRepository.existsByItemAndCategory(item, category)) {
                    itemCategoryRepository.save(
                        ItemCategory.builder()
                            .item(item)
                            .category(category)
                            .build()
                    );
                }
                row.getCategoryChild().forEach(c -> {

                });
            }
        } catch (IOException | CsvException ex) {
            throw new RuntimeException(ex);
        }
    }

    @PostConstruct
    public void test() {
        read();
    }
}
