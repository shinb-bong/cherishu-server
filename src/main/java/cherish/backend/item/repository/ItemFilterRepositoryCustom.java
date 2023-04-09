package cherish.backend.item.repository;

import cherish.backend.item.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ItemFilterRepositoryCustom {

    List<ItemFilterQueryDto> findItemFilterByNameAndId(ItemFilterCondition filterCondition);

    List<AgeFilterQueryDto> findItemFilterByAge(AgeFilterCondition ageCondition);

    Page<ItemSearchResponseDto> searchItem(ItemSearchCondition searchCondition, Pageable pageable);

}
