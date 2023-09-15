/*
package com.bibum_server.domain.util;

import com.bibum_server.domain.dto.request.LocationReq;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;


import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WebClientUtilTest {

    @BeforeEach
            void beforeEach(){
        System.setProperty("apiKey","3edf38677cf65d3371a14c2494883595");
    }
    WebClientUtilTest(WebClient.Builder builder) {
        webClient = builder.build();
    }

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private final WebClient webClient;

    @InjectMocks
    WebClientUtil webClientUtil;

    @Test
    public void getBaseUrl() throws IOException {
        //given
        given(webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("dapi.kakao.com")
                        .path("/v2/local/search/keyword.json")
                        .queryParam("x", "127.190607")
                        .queryParam("y", "37.230840")
                        .queryParam("query", "음식점")
                        .queryParam("radius", 500)
                        .queryParam("page", 1)
                        .queryParam("size", 10)
                        .build()))
                .header("Authorization","KakaoAK "+System.getProperty("apiKey"))
                .retrieve()
                .bodyToMono(String.class);
    }
}*/
