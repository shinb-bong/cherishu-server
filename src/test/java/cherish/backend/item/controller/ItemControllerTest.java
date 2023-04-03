package cherish.backend.item.controller;

import cherish.backend.item.dto.ItemSearchCondition;
import cherish.backend.item.dto.ItemSearchDto;
import cherish.backend.item.service.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("put-data")
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    @DisplayName("아이템 브랜드로 검색했을때 검색에 성공할때")
    void searchTest() throws Exception {
        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setTarget("itemName");
        condition.setKeyword("어딕트 립 글로우");
        Pageable pageable = PageRequest.of(0, 10, Sort.by("itemName").ascending());
        Page<ItemSearchDto.ResponseSearchItem> result = itemService.searchItem(condition, pageable);

        mockMvc.perform(get("/public/item/search")
                        .param("itemName", "어딕트 립 글로우")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content[0].item.id").value(01001L))
                .andExpect(jsonPath("$.content[0].item.name").value("어딕트 립 글로우"))
                .andExpect(jsonPath("$.content[0].item.brand").value("디올"))
                .andExpect(jsonPath("$.content[0].item.price").value(48000))
                .andExpect(jsonPath("$.content[0].item.description").value("청량한 달콤함을 시작으로, 화이트 튤립의 푸른 잎사귀를 떠오르는 탬버린즈 퍼퓸 핸드크림이에요. 작은 크기에 들고 다니기 좋아요."))
//                .andExpect(jsonPath("$.content[0].item.imgUrl").value("https://image.com/dior/01001.png"))
//                .andExpect(jsonPath("$.content[0].item.itemFilter.name").value(itemDto.getItemFilter().getName()))
//                .andExpect(jsonPath("$.content[0].item.itemCategory.id").value(itemDto.getItemCategory().getId()))
//                .andExpect(jsonPath("$.content[0].item.itemCategory.name").value(itemDto.getItemCategory().getName()))
//                .andExpect(jsonPath("$.content[0].item.job.id").value(itemDto.getJob().getId()))
//                .andExpect(jsonPath("$.content[0].item.job.name").value(itemDto.getJob().getName()))
//                .andExpect(jsonPath("$.content[0].filter.name").value("선물용"))
                .andExpect(jsonPath("$.content[0].category.parent.name").value("뷰티"))
                .andExpect(jsonPath("$.content[0].category.children[*].name", containsInAnyOrder("립", "틴트", "립글로즈", "립스틱", "립밤", "화장품", "색조")))
                .andExpect(jsonPath("$.content[0].item.itemUrl[?(@.platform == 'KAKAO')].url").value("https://gift.kakao.com/product/2259144"));
    }
}