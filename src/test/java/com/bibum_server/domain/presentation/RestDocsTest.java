package com.bibum_server.domain.presentation;

import com.bibum_server.domain.AbstractRestDocsTests;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodayMenuController.class)
class RestDocsTest extends AbstractRestDocsTests {

    @Test
    void restDocsAPI() throws Exception {
        mockMvc.perform(get("/restDocsTest")).andExpect(status().isOk());
    }

}