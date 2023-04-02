package cherish.backend.item.controller;

import cherish.backend.item.dto.ItemSearchCondition;
import cherish.backend.item.service.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("put-data")
@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    @DisplayName("아이템 브랜드로 검색했을때 검색에 성공할때")
    void searchTest() throws Exception {
        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setItemName("어딕트 립 글로우");
        Pageable pageable = PageRequest.of(0, 10, Sort.by("itemName").ascending());
        itemService.searchItem(condition, pageable);
        mockMvc.perform(get("/public/item/search")
                        .param("itemName", "어딕트 립 글로우")
                        .param("page", "0")
                        .param("size", "10")
                        .param("itemName", "itemName,asc"))
                .andExpect(status().isOk());
    }

}