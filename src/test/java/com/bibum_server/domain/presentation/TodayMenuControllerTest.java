package com.bibum_server.domain.presentation;

import com.bibum_server.domain.application.RoomService;
import com.bibum_server.domain.util.WebClientUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@WebMvcTest(TodayMenuController.class)
class TodayMenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;
    @MockBean
    private WebClientUtil webClientUtil;

    /*@DisplayName("방을 생성한다.")
    @Test
    void createRoom() throws Exception {
        Map<String,String> requestMap = new HashMap<>();
        requestMap.put("longitude","127.190607");
        requestMap.put("latitude","37.230840");
        String content = new ObjectMapper().writeValueAsString(requestMap);

        MvcResult mvcResult = mockMvc.perform(
                        post("/create") //url
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andDo(print())
                .andReturn();
        Map<String,String> responseMap = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(),Map.class);
        assertThat(responseMap.get("method")).isEqualTo("POST");
        assertThat(responseMap.get("x")).isEqualTo("127.190607");
        assertThat(responseMap.get("y")).isEqualTo("37.230840");

    }*/
}