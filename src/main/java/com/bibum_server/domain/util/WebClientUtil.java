package com.bibum_server.domain.util;

import com.bibum_server.domain.dto.request.LocationReq;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientUtil {

    @Value("${KAKAO.REST_API_KEY}")
    public static String apiKey;
    private static final WebClient webClient = WebClient.create();

    public static String getBaseUrl(LocationReq locationReq){
        Mono<String> responseMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("dapi.kakao.com")
                        .path("/v2/local/geo/coord2adddress.json")
                        .queryParam("query",locationReq.getLongitude(),locationReq.getLatitude())
                        .build())
                .header("Authorization","KakaoAK "+apiKey)
                .retrieve()
                .bodyToMono(String.class);
        return responseMono.block();
    }

    //TODO TEST 코드 작성 후 SERVICE에 붙일 것
}
