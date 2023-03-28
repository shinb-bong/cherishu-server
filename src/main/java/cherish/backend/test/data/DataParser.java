package cherish.backend.test.data;

import cherish.backend.category.model.Category;
import cherish.backend.category.repository.CategoryRepository;
import cherish.backend.category.repository.FilterRepository;
import cherish.backend.item.constant.ItemUrlPlatforms;
import cherish.backend.item.model.Item;
import cherish.backend.item.model.ItemCategory;
import cherish.backend.item.model.ItemJob;
import cherish.backend.item.model.ItemUrl;
import cherish.backend.item.repository.ItemCategoryRepository;
import cherish.backend.item.repository.ItemJobRepository;
import cherish.backend.item.repository.ItemRepository;
import cherish.backend.item.repository.ItemUrlRepository;
import cherish.backend.member.model.Job;
import cherish.backend.member.repository.JobRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
    private final ItemUrlRepository itemUrlRepository;
    private final ItemJobRepository itemJobRepository;

    @Transactional(rollbackOn = RuntimeException.class)
    public void read() {
        try (CSVReader csvReader = new CSVReader(new FileReader("data.csv"))) {
            List<DataRow> dataList = csvReader.readAll().stream()
                .map(DataRow::of)
                .toList();
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
                Category category = categoryRepository.findByName(row.getCategory())
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

                //2차 카테고리 작업
                for (String childCategoryName : row.getCategoryChild()) {
                    Category childCategory = categoryRepository.findByName(childCategoryName)
                        .orElseGet(() -> categoryRepository.save(
                            Category.builder()
                                .name(childCategoryName)
                                .build()
                        ));
                    if (!itemCategoryRepository.existsByItemAndCategory(item, childCategory)) {
                        itemCategoryRepository.save(
                            ItemCategory.builder()
                                .item(item)
                                .category(childCategory)
                                .build()
                        );
                    }
                }
                // 브랜드 url insert
                if (StringUtils.isNotBlank(row.getBrandUrl()) && !row.getBrandUrl().equals("-")) {
                    itemUrlRepository.findBrandUrlByItem(item)
                        .ifPresentOrElse(itemUrl -> itemUrlRepository.save(
                            ItemUrl.builder()
                                .id(itemUrl.getId())
                                .item(item)
                                .url(row.getBrandUrl())
                                .platform(ItemUrlPlatforms.BRAND)
                                .build()
                        ), () -> itemUrlRepository.save(
                            ItemUrl.builder()
                                .item(item)
                                .url(row.getBrandUrl())
                                .platform(ItemUrlPlatforms.BRAND)
                                .build()
                        ));
                }
                // kakao url
                if (StringUtils.isNotBlank(row.getKakaoUrl()) && !row.getKakaoUrl().equals("-")) {
                    itemUrlRepository.findKakaoUrlByItem(item)
                        .ifPresentOrElse(itemUrl -> itemUrlRepository.save(
                            ItemUrl.builder()
                                .id(itemUrl.getId())
                                .item(item)
                                .url(row.getKakaoUrl())
                                .platform(ItemUrlPlatforms.KAKAO)
                                .build()
                        ), () -> itemUrlRepository.save(
                            ItemUrl.builder()
                                .item(item)
                                .url(row.getKakaoUrl())
                                .platform(ItemUrlPlatforms.KAKAO)
                                .build()
                        ));
                }
                // coupang url
                if (StringUtils.isNotBlank(row.getCoupangUrl()) && !row.getCoupangUrl().equals("-")) {
                    itemUrlRepository.findCoupangUrlByItem(item)
                        .ifPresentOrElse(itemUrl -> itemUrlRepository.save(
                            ItemUrl.builder()
                                .id(itemUrl.getId())
                                .item(item)
                                .url(row.getCoupangUrl())
                                .platform(ItemUrlPlatforms.COUPANG)
                                .build()
                        ), () -> itemUrlRepository.save(
                            ItemUrl.builder()
                                .item(item)
                                .url(row.getCoupangUrl())
                                .platform(ItemUrlPlatforms.COUPANG)
                                .build()
                        ));
                }
                // naver url
                if (StringUtils.isNotBlank(row.getNaverUrl()) && !row.getNaverUrl().equals("-")) {
                    itemUrlRepository.findNaverUrlByItem(item)
                        .ifPresentOrElse(itemUrl -> itemUrlRepository.save(
                            ItemUrl.builder()
                                .id(itemUrl.getId())
                                .item(item)
                                .url(row.getNaverUrl())
                                .platform(ItemUrlPlatforms.NAVER)
                                .build()
                        ), () -> itemUrlRepository.save(
                            ItemUrl.builder()
                                .item(item)
                                .url(row.getNaverUrl())
                                .platform(ItemUrlPlatforms.NAVER)
                                .build()
                        ));
                }
                // 1차 직업군 insert
                for (String jobName : row.getJob()) {
                    Job job = jobRepository.findByName(jobName)
                        .orElseGet(() -> jobRepository.save(
                            Job.builder()
                                .name(jobName)
                                .build()
                        ));
                    if (!itemJobRepository.existsByItemAndJob(item, job)) {
                        itemJobRepository.save(
                            ItemJob.builder()
                                .item(item)
                                .job(job)
                                .build()
                        );
                    }
                }
                // 2차 직업군 insert
                for (String jobChildName : row.getJobChild()) {
                    Job childJob = jobRepository.findByName(jobChildName)
                        .orElseGet(() -> jobRepository.save(
                            Job.builder()
                                .name(jobChildName)
                                .build()
                        ));
                    if (!itemJobRepository.existsByItemAndJob(item, childJob)) {
                        itemJobRepository.save(
                            ItemJob.builder()
                                .item(item)
                                .job(childJob)
                                .build()
                        );
                    }
                }

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
