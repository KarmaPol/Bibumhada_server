package com.bibum_server.domain.util;

import com.bibum_server.domain.dto.request.LocationReq;
import com.bibum_server.domain.dto.response.KakaoApiRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class WebClientUtil {
    private static String apiKey;
    @Value("${KAKAO.REST_API_KEY}")
    public void setApiKey(String apiKey){
        WebClientUtil.apiKey = apiKey;
    }
    private static final WebClient webClient = WebClient.create();

    public Mono<String> getBaseUrl(LocationReq locationReq){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("dapi.kakao.com")
                        .path("/v2/local/geo/coord2address.json")
                        .queryParam("x",locationReq.getLongitude())
                        .queryParam("y",locationReq.getLatitude())
                        .build())
                .header("Authorization","KakaoAK "+apiKey)
                .retrieve()
                .bodyToMono(String.class);
    }

    public List<KakaoApiRes.RestaurantResponse> getRestaurant(LocationReq locationReq){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("dapi.kakao.com")
                        .path("/v2/local/search/keyword.json")
                        .queryParam("x",locationReq.getLongitude())
                        .queryParam("y",locationReq.getLatitude())
                        .queryParam("query","음식점")
                        .queryParam("radius",500)
                        .queryParam("page",1)
                        .queryParam("size",10)
                        .build())
                .header("Authorization","KakaoAK "+apiKey)
                .retrieve()
                .bodyToMono(KakaoApiRes.class)
                .block()
                .getDocuments();
    }

}
