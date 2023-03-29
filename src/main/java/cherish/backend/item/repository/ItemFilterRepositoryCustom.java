package cherish.backend.item.repository;

import cherish.backend.item.dto.AgeFilterCondition;
import cherish.backend.item.dto.AgeFilterQueryDto;
import cherish.backend.item.dto.ItemFilterCondition;
import cherish.backend.item.dto.ItemFilterQueryDto;

import java.util.List;

public interface ItemFilterRepositoryCustom {

    List<ItemFilterQueryDto> findItemFilterByNameAndId(ItemFilterCondition filterCondition);

    List<AgeFilterQueryDto> findItemFilterByAge(AgeFilterCondition ageCondition);

}
