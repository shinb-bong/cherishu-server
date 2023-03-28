package cherish.backend.member.controller;

import cherish.backend.member.dto.email.EmailCodeValidationRequest;
import cherish.backend.member.dto.email.EmailRequest;
import cherish.backend.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberService memberService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @WithMockUser
    public void emailSendApiTest() throws Exception {
        String email = "cherishu.fullstack@gmail.com";
        String content = objectMapper.writeValueAsString(new EmailRequest(email));

        mvc.perform(post("/member/code-send").content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void emailValidTest() throws Exception{
        //given
        String email = "cherishu.fullstack@gmail.com";
        String content = objectMapper.writeValueAsString(new EmailRequest(email));
        MvcResult mvcResult = mvc.perform(post("/member/code-send").content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).with(csrf())).andReturn();
        //when
        String code = mvcResult.getResponse().getContentAsString();
        content = objectMapper.writeValueAsString(new EmailCodeValidationRequest(email,code));

        //then
        mvc.perform(post("/member/code-valid").content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).with(csrf()))
                .andExpect(status().isOk());


    }

}