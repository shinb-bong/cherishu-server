package cherish.backend.item.controller;

import cherish.backend.item.dto.ItemSearchCondition;
import cherish.backend.item.service.ItemService;
import cherish.backend.test.data.DataParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@Slf4j
@Profile("put-data")
@WithMockUser
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(PublicItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemService itemService;

    @Autowired
    private DataParser parser;

    @Test
    @DisplayName("아이템 브랜드로 검색했을때 검색에 성공할때")
    void searchTest() throws Exception {
        mvc.perform(get("public/item/search"))
                .andExpect()
    }
}
