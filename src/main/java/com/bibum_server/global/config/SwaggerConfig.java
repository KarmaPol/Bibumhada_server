package com.bibum_server.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "오메 백엔드 API 명세서",
                description = "비범하다 팀의 오메 백엔드 API 명세서 입니다",
                version = "0.1"),
        servers = {
        @Server(url = "/", description = "오메 서버 url")}
)
@Configuration
public class SwaggerConfig {
}
