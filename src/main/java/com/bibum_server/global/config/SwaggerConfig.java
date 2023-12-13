package com.bibum_server.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "오메 백엔드 API 명세서",
                description = "비범하다 팀의 오메 백엔드 API 명세서 입니다",
                version = "0.1"))
@Configuration
public class SwaggerConfig {
}
