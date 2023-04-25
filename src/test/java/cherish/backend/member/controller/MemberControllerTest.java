package cherish.backend.member.controller;

import cherish.backend.auth.jwt.TokenInfo;
import cherish.backend.auth.jwt.config.JwtConfig;
import cherish.backend.member.dto.ChangeInfoRequest;
import cherish.backend.member.dto.MemberLoginRequestDto;
import cherish.backend.member.dto.email.EmailCodeValidationRequest;
import cherish.backend.member.dto.email.EmailRequest;
import cherish.backend.member.model.Member;
import cherish.backend.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest({MemberController.class, PublicMemberController.class})
class MemberControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private JwtConfig jwtConfig;

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
    public void emailValidTest() throws Exception {
        //given
        String email = "cherishu.fullstack@gmail.com";
        String content = objectMapper.writeValueAsString(new EmailRequest(email));
        MvcResult mvcResult = mvc.perform(post("/member/code-send").content(content)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON).with(csrf())).andReturn();
        //when
        String code = mvcResult.getResponse().getContentAsString();
        content = objectMapper.writeValueAsString(new EmailCodeValidationRequest(email, code));

        //then
        mvc.perform(post("/member/code-valid").content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).with(csrf()))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void 로그인_테스트() throws Exception {
        //given
        Member member = Member.builder().id(1L).name("test").email("test@naver.com").password("12345").build();
        given(memberService.login(member.getEmail(), member.getPassword())).willReturn(
            TokenInfo.builder()
                .grantType("Bearer").refreshToken("abc").accessToken("cde").build()
        );
        //when
        //then
        String content = objectMapper.writeValueAsString(new MemberLoginRequestDto("test@naver.com", "12345"));
        mvc.perform(post("/public/member/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .with(csrf()))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.grantType").value("Bearer"))
            .andExpect(jsonPath("$.refreshToken").value("abc"))
            .andExpect(jsonPath("$.accessToken").value("cde"));
    }

    @WithMockUser
    @DisplayName("old 패스워드만 입력시 400")
    @Test
    void validateNoNewPassword() throws Exception {
        ChangeInfoRequest request = new ChangeInfoRequest();
        request.setNickname("nick");
        request.setJobName("job");
        request.setOldPassword("oldpassword");
        String content = objectMapper.writeValueAsString(request);
        mvc.perform(patch("/member/info")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .with(csrf())
            )
            .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @DisplayName("old 패스워드와 new 패스워드 같을시 400")
    @Test
    void validateOldPwEqualsNewPw() throws Exception {
        ChangeInfoRequest request = new ChangeInfoRequest();
        request.setNickname("nick");
        request.setJobName("job");
        request.setOldPassword("oldpassword");
        request.setNewPassword("oldpassword");
        String content = objectMapper.writeValueAsString(request);
        mvc.perform(patch("/member/info")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .with(csrf())
            )
            .andExpect(status().isBadRequest());
    }

    @WithMockUser
    @DisplayName("old 패스워드와 new 패스워드 다를시 200")
    @Test
    void validateChangeInfo() throws Exception {
        ChangeInfoRequest request = new ChangeInfoRequest();
        request.setNickname("nick");
        request.setJobName("job");
        request.setOldPassword("oldpassword");
        request.setNewPassword("newpassword");
        String content = objectMapper.writeValueAsString(request);
        mvc.perform(patch("/member/info")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8)
                .with(csrf())
            )
            .andExpect(status().isOk());
    }

}