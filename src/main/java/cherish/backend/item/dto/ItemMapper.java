package cherish.backend.item.dto;

import cherish.backend.item.model.Item;
import cherish.backend.item.model.ItemUrl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper {

    @Mapping(target = "id", source = "item.id")
    @Mapping(target = "brand", source = "item.brand")
    @Mapping(target = "name", source = "item.name")
    @Mapping(target = "description", source = "item.description")
    @Mapping(target = "price", source = "item.price")
    @Mapping(target = "views", source = "item.views")
    @Mapping(target = "imgUrl", source = "item.imgUrl")
    @Mapping(target = "url", source = "itemUrl.url")
    @Mapping(target = "platform", source = "itemUrl.platform")
    ItemDto.ResponseSearchItem responseToSearchDto(Item item, ItemUrl itemUrl);

}