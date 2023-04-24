package cherish.backend.item.controller;

import cherish.backend.item.service.ItemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PublicItemController.class)
public class ItemInfoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ItemService itemService;

    @Test
    @WithMockUser
    void itemInfoResponseApi() throws Exception {
        Long itemId = 3002L;

        mvc.perform(get("/public/item/{itemId}", itemId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").value(3002))
                .andExpect(jsonPath("$.name").value("유아 젖병세정제&주방세제 무향"))
                .andExpect(jsonPath("$.brand").value("베베숲"))
                .andExpect(jsonPath("$.price").value(13900));

    }
}