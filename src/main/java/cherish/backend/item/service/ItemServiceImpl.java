package cherish.backend.item.service;

import cherish.backend.category.repository.CategoryRepository;
import cherish.backend.category.repository.FilterRepository;
import cherish.backend.common.dto.ModelMapperUtils;
import cherish.backend.item.dto.ItemSearchCondition;
import cherish.backend.item.dto.ItemSearchDto;
import cherish.backend.item.dto.ItemSearchQueryDto;
import cherish.backend.item.dto.ItemSearchQueryDtoMapper;
import cherish.backend.item.repository.ItemFilterRepository;
import cherish.backend.item.repository.ItemRepository;
import cherish.backend.item.repository.ItemUrlRepository;
import cherish.backend.member.repository.JobRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Profile("put-data")
public class ItemServiceImpl implements ItemService {

    private ItemFilterRepository itemFilterRepository;
    private JobRepository jobRepository;
    private CategoryRepository categoryRepository;
    private ItemRepository itemRepository;
    private FilterRepository filterRepository;
    private ItemUrlRepository itemUrlRepository;

    private ItemSearchQueryDtoMapper itemMidMapper;

    @Override
    public Page<ItemSearchDto.ResponseSearchItem> searchItem(ItemSearchCondition searchCondition, Pageable pageable) {
        Page<ItemSearchQueryDto> content = itemFilterRepository.searchItem(searchCondition, pageable);
        List<ItemSearchDto.ResponseSearchItem> response = content.stream()
                .map(itemSearchQueryDto -> itemMidMapper.map(itemSearchQueryDto))
                .collect(Collectors.toList());

        // ResponseSearchItem에 매핑할 데이터 뽑아오기
        for (ItemSearchDto.ResponseSearchItem responseDto : response) {
            ItemSearchDto.JobDto jobDto = jobRepository.findById(responseDto.getJob().getId())
                    .map(job -> ModelMapperUtils.map(job, ItemSearchDto.JobDto.class))
                    .orElse(null);
            responseDto.setJob(jobDto);

            ItemSearchDto.CategoryDto categoryDto = categoryRepository.findById(responseDto.getCategory().getId())
                    .map(category -> ModelMapperUtils.map(category, ItemSearchDto.CategoryDto.class))
                    .orElse(null);
            responseDto.setCategory(categoryDto);

            ItemSearchDto.ItemDto itemDto = itemRepository.findById(responseDto.getItem().getId())
                    .map(itemEntity -> ModelMapperUtils.map(itemEntity, ItemSearchDto.ItemDto.class))
                    .orElse(null);
            responseDto.setItem(itemDto);

            ItemSearchDto.FilterDto filterDto = filterRepository.findById(responseDto.getFilter().getId())
                    .map(filter -> ModelMapperUtils.map(filter, ItemSearchDto.FilterDto.class))
                    .orElse(null);
            responseDto.setFilter(filterDto);

            if (responseDto.getItemFilter() != null) {
                ItemSearchDto.FilterDto itemFilterDto = filterRepository.findById(responseDto.getItemFilter().getId())
                        .map(filter -> ModelMapperUtils.map(filter, ItemSearchDto.FilterDto.class))
                        .orElse(null);
                responseDto.setItemFilter(itemFilterDto);
            }

            ItemSearchDto.ItemUrlDto itemUrlDto = itemUrlRepository.findByItemId(responseDto.getItem().getId())
                    .map(itemUrl -> ModelMapperUtils.map(itemUrl, ItemSearchDto.ItemUrlDto.class))
                    .orElse(null);
            responseDto.setItemUrl(itemUrlDto);
        }

        return new PageImpl<>(response, pageable, content.getTotalElements());
    }
}