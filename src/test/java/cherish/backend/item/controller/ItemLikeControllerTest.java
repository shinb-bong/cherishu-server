package cherish.backend.item.controller;

import cherish.backend.item.service.ItemLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest(ItemLikeController.class)
class ItemLikeControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    ItemLikeService service;

}