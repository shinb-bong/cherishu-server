package cherish.backend.item.service;

import cherish.backend.category.repository.CategoryRepository;
import cherish.backend.category.repository.FilterRepository;
import cherish.backend.common.util.ModelMapperUtils;
import cherish.backend.item.dto.ItemSearchCondition;
import cherish.backend.item.dto.ItemSearchDto;
import cherish.backend.item.dto.ItemSearchQueryDto;
import cherish.backend.item.dto.ItemSearchQueryDtoMapper;
import cherish.backend.item.repository.ItemFilterRepository;
import cherish.backend.item.repository.ItemRepository;
import cherish.backend.item.repository.ItemUrlRepository;
import cherish.backend.member.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemFilterRepository itemFilterRepository;
    private final JobRepository jobRepository;
    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final FilterRepository filterRepository;
    private final ItemUrlRepository itemUrlRepository;

    private final ItemSearchQueryDtoMapper itemMidMapper;

    @Autowired
    public ItemServiceImpl(ItemFilterRepository itemFilterRepository, JobRepository jobRepository,
                           CategoryRepository categoryRepository, ItemRepository itemRepository,
                           FilterRepository filterRepository, ItemUrlRepository itemUrlRepository,
                           ItemSearchQueryDtoMapper itemMidMapper) {
        this.itemFilterRepository = itemFilterRepository;
        this.jobRepository = jobRepository;
        this.categoryRepository = categoryRepository;
        this.itemRepository = itemRepository;
        this.filterRepository = filterRepository;
        this.itemUrlRepository = itemUrlRepository;
        this.itemMidMapper = itemMidMapper;
    }

    @Override
    public Page<ItemSearchDto.ResponseSearchItem> searchItem(ItemSearchCondition searchCondition, Pageable pageable) {
        Page<ItemSearchQueryDto> content = itemFilterRepository.searchItem(searchCondition, pageable);
        List<ItemSearchDto.ResponseSearchItem> response = content.stream()
                .map(itemSearchQueryDto -> itemMidMapper.map(itemSearchQueryDto))
                .collect(Collectors.toList());

        // ResponseSearchItem 에 매핑할 데이터 뽑아오기
        for (ItemSearchDto.ResponseSearchItem responseDto : response) {
            Optional<ItemSearchDto.JobDto> jobDto = jobRepository.findById(responseDto.getJob().getId())
                    .map(job -> ModelMapperUtils.map(job, ItemSearchDto.JobDto.class));
            responseDto.setJob(jobDto.isPresent() ? jobDto.get() : null);

            Optional<ItemSearchDto.CategoryDto> categoryDto = categoryRepository.findById(responseDto.getCategory().getId())
                    .map(category -> ModelMapperUtils.map(category, ItemSearchDto.CategoryDto.class));
            responseDto.setCategory(categoryDto.isPresent() ? categoryDto.get() : null);

            Optional<ItemSearchDto.ItemDto> itemDto = itemRepository.findById(responseDto.getItem().getId())
                    .map(itemEntity -> ModelMapperUtils.map(itemEntity, ItemSearchDto.ItemDto.class));
            responseDto.setItem(itemDto.isPresent() ? itemDto.get() : null);

            Optional<ItemSearchDto.FilterDto> filterDto = filterRepository.findById(responseDto.getFilter().getId())
                    .map(filter -> ModelMapperUtils.map(filter, ItemSearchDto.FilterDto.class));
            responseDto.setFilter(filterDto.isPresent() ? filterDto.get() : null);

            if (responseDto.getItemFilter() != null) {
                Optional<ItemSearchDto.FilterDto> itemFilterDto = filterRepository.findById(responseDto.getItemFilter().getId())
                        .map(filter -> ModelMapperUtils.map(filter, ItemSearchDto.FilterDto.class));
                responseDto.setItemFilter(itemFilterDto.isPresent() ? itemFilterDto.get() : null);
            }

            Optional<ItemSearchDto.ItemUrlDto> itemUrlDto = itemUrlRepository.findByItemId(responseDto.getItem().getId())
                    .map(itemUrl -> ModelMapperUtils.map(itemUrl, ItemSearchDto.ItemUrlDto.class));
            responseDto.setItemUrl(itemUrlDto.isPresent() ? itemUrlDto.get() : null);
        }

        return new PageImpl<>(response, pageable, content.getTotalElements());
    }
}