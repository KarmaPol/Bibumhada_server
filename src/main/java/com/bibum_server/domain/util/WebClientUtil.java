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
import java.util.Collections;
import java.util.List;

@Component
public class WebClientUtil {
    public static final int MAX_RESTAURANT_NUM = 15;
    private String kakaoApiKey;
    private String naverClientId;
    private String naverClientSecret;

    @Value("${KAKAO.REST_API_KEY}")
    public void setKakaoApiKey(String kakaoApiKey) {
        this.kakaoApiKey = kakaoApiKey;
    }

    @Value("${NAVER.CLIENT_ID}")
    public void setNaverClientId(String naverClientId) {
        this.naverClientId = naverClientId;
    }

    @Value("${NAVER.CLIENT_SECRET}")
    public void setNaverClientSecret(String naverClientSecret) {
        this.naverClientSecret = naverClientSecret;
    }

    public List<KakaoApiRes.RestaurantResponse> getRestaurant(LocationReq locationReq) {

        var webClient = WebClient.builder()
                .baseUrl("https://dapi.kakao.com")
                .defaultHeader("Authorization", "KakaoAK " + kakaoApiKey)
                .build();

        Mono<KakaoApiRes> kakaoApiResMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/v2/local/search/keyword.json")
                        .queryParam("query", "음식점")
                        .queryParam("category_group_code", "FD6")
                        .queryParam("x", locationReq.getLongitude())
                        .queryParam("y", locationReq.getLatitude())
                        .queryParam("radius", 500)
                        .queryParam("size", MAX_RESTAURANT_NUM)
                        .build())
                .retrieve()
                .bodyToMono(KakaoApiRes.class);

        KakaoApiRes kakaoApiRes = kakaoApiResMono.block();
        List<KakaoApiRes.RestaurantResponse> restaurants = kakaoApiRes.getDocuments();

        Collections.shuffle(restaurants);

        return restaurants;
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
                        .queryParam("query", restaurantName + " 음식점")
                        .build())
                .retrieve().bodyToMono(NaverApiResponse.class);

        NaverApiResponse response = responseMono.block();
        NaverApiItemRes firstItem = response.getItems().get(0);  // 첫 번째 아이템 가져오기.
        return firstItem;
    }
}
