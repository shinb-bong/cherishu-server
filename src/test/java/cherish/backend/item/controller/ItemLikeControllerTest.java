package cherish.backend.item.controller;

import cherish.backend.item.dto.ItemLikeRequest;
import cherish.backend.item.service.ItemLikeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ItemLikeController.class)
class ItemLikeControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ItemLikeService service;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "test@naver.com")
    public void 아이템_좋아요() throws Exception {
        //given
        given(service.likeItem(any(),any())).willReturn(3L);
        String content = objectMapper.writeValueAsString(new ItemLikeRequest(3L));
        //when
        mvc.perform(post("/item/like")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(content().string("3"));
    }


}