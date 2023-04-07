package cherish.backend.item.controller;

import cherish.backend.item.dto.ItemSearchCondition;
import cherish.backend.item.dto.ItemSearchResponseDto;
import cherish.backend.item.service.ItemService;
import cherish.backend.test.data.DataParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("put-data")
@ComponentScan(basePackages = "cherish.backend.test.data")
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ItemControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ItemService itemService;

    @Autowired
    private DataParser dataParser;

    @Test
    @DisplayName("아이템 브랜드로 검색에 성공")
    void searchTest() throws Exception {
        dataParser.read();

        ItemSearchCondition condition = new ItemSearchCondition();
        condition.setKeyword("어딕트 립 글로우");
        Pageable pageable = PageRequest.of(0, 10, Sort.by("itemName").ascending());
        Page<ItemSearchResponseDto.ResponseSearchItem> result = itemService.searchItem(condition, pageable);

        mockMvc.perform(get("/public/item/search")
                        .param("keyword", condition.getKeyword())
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].ItemSearchResponseDto.ItemDto.item.id").value("01001"))
                .andExpect(jsonPath("$.content[0].ItemSearchResponseDto.ItemDto.item.name").value("어딕트 립 글로우"))
                .andExpect(jsonPath("$.content[0].ItemSearchResponseDto.ItemDto.item.brand").value("디올"))
                .andExpect(jsonPath("$.content[0].ItemSearchResponseDto.ItemDto.item.price").value(48000))
                .andExpect(jsonPath("$.content[0].ItemSearchResponseDto.ItemDto.item.description").value("청량한 달콤함을 시작으로, 화이트 튤립의 푸른 잎사귀를 떠오르는 탬버린즈 퍼퓸 핸드크림이에요. 작은 크기에 들고 다니기 좋아요."))
                .andExpect(jsonPath("$.content[0].ItemSearchResponseDto.ItemDto.item.imgUrl").value("https://image.com/dior/01001.png"));
    }

}