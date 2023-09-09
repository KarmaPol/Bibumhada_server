package com.bibum_server;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.restdocs.RestDocumentationExtension;

public class JunitExampleTests {
    @RegisterExtension
    final RestDocumentationExtension restDocumentation = new RestDocumentationExtension ("custom");
}
