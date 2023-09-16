package com.bibum_server;

import com.bibum_server.domain.AbstractRestDocsTests;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ExtendWith(RestDocumentationExtension.class)
class BibumServerApplicationTests {
	@Test
	void contextLoads() {
	}
}
