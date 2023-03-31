package cherish.backend.item.dto;

import cherish.backend.item.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ItemMapper {

    @Mapping(target = "id", source = "item.id")
    @Mapping(target = "brand", source = "item.brand")
    @Mapping(target = "name", source = "item.name")
    @Mapping(target = "description", source = "item.description")
    @Mapping(target = "price", source = "item.price")
    @Mapping(target = "views", source = "item.views")
    @Mapping(target = "imgUrl", source = "item.imgUrl")
    ItemDto.RequestSearchItem responseToSearchDto(Item item);

    @Mapping(target = "id", source = "searchQuery.itemId")
    @Mapping(target = "brand", source = "searchQuery.itemBrand")
    @Mapping(target = "name", source = "searchQuery.itemName")
    @Mapping(target = "description", source = "searchQuery.itemDescription")
    @Mapping(target = "price", source = "searchQuery.itemPrice")
    @Mapping(target = "views", source = "searchQuery.itemViews")
    @Mapping(target = "imgUrl", source = "searchQuery.itemImgUrl")
    ItemDto.RequestSearchItem searchQueryToRequestSearchDto(ItemSearchQueryDto searchQuery);
}