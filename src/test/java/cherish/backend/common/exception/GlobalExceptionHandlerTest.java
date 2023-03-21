package cherish.backend.common.exception;

import cherish.backend.member.controller.MemberController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
class GlobalExceptionHandlerTest {
    @Autowired
    MemberController memberController;

    @Test
    void 예외문구_처리() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(memberController)
                .setControllerAdvice(GlobalExceptionHandler.class)
                .build();

        MvcResult result = mockMvc.perform(
                        post("/member/register") // 없는 요청
                                .content("{}")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

}