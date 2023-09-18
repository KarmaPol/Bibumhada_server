package com.bibum_server.domain.util;

import com.bibum_server.domain.dto.request.LocationReq;
import com.bibum_server.domain.dto.response.KakaoApiRes;
import com.bibum_server.domain.dto.response.NaverApiItemRes;
import com.bibum_server.domain.dto.response.NaverApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
public class WebClientUtil {
    private String kakaoApiKey;
    private String naverClientId;
    private String naverClientSecret;

    @Value("${KAKAO.REST_API_KEY}")
    public void setKakaoApiKey(String kakaoApiKey){
        this.kakaoApiKey = kakaoApiKey;
    }

    @Value("${NAVER.CLIENT_ID}")
    public void setNaverClientId(String naverClientId){
        this.naverClientId = naverClientId;
    }

    @Value("${NAVER.CLIENT_SECRET}")
    public void setNaverClientSecret(String naverClientSecret){
        this.naverClientSecret = naverClientSecret;
    }


    private final WebClient webClient;
    public WebClientUtil(@Value("${KAKAO.REST_API_KEY}") String kakaoApiKey,
                         @Value("${NAVER.CLIENT_ID}") String naverClientId,
                         @Value("${NAVER.CLIENT_SECRET}") String naverClientSecret) {
        this.webClient = WebClient.builder()
                .defaultHeader("Authorization", "KakaoAK " + kakaoApiKey)
                .defaultHeader("X-Naver-Client-Id", naverClientId)
                .defaultHeader("X-Naver-Client-Secret", naverClientSecret)
                .build();
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
                .header("Authorization","KakaoAK "+ kakaoApiKey)
                .retrieve()
                .bodyToMono(KakaoApiRes.class)
                .block()
                .getDocuments();
    }
    public NaverApiItemRes convertRestaurantUrl(String title) throws UnsupportedEncodingException {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://openapi.naver.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("X-Naver-Client-Id", naverClientId)
                .defaultHeader("X-Naver-Client-Secret", naverClientSecret)
                .build();

        String restaurantName = title;

        Mono<NaverApiResponse> responseMono = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/v1/search/local.json")
                        .queryParam("query", restaurantName+" 음식점")
                        .build())
        .retrieve().bodyToMono(NaverApiResponse.class);

        NaverApiResponse response = responseMono.block();
        NaverApiItemRes firstItem = response.getItems().get(0);  // 첫 번째 아이템 가져오기.
        return firstItem;
    }

}
