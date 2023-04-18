package cherish.backend.curation.controller;

import cherish.backend.curation.service.CurationService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(PublicCurationController.class)
class PublicCurationControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CurationService curationService;

    @WithMockUser
    @DisplayName("parameter validation 잘 되는지")
    @Test
    void valid() throws Exception {
            mvc.perform(
                get("/public/curation")
                    .queryParam("gender", "남자")
                )
                .andExpect(status().isBadRequest());
    }
}