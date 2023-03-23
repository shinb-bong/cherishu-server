package cherish.backend.item.repository;

import cherish.backend.item.dto.ItemFilterCondition;
import cherish.backend.item.dto.ItemFilterQueryDto;

import java.util.List;

public interface ItemFilterRepositoryCustom {

    List<ItemFilterQueryDto> findItemFilterByNameAndId(ItemFilterCondition filterCondition);

}
