package cherish.backend.item.service;

import cherish.backend.item.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemService {
    Page<ItemSearchDto.ResponseSearchItem> searchItem(ItemSearchCondition searchCondition, Pageable pageable);
}

