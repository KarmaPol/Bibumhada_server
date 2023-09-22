package com.bibum_server.domain.util;

import com.bibum_server.domain.dto.request.LocationReq;
import com.bibum_server.domain.dto.request.ReSuggestReq;
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
    private final WebClient webClient;
    private String kakaoApiKey;
    private String naverClientId;
    private String naverClientSecret;

    public WebClientUtil(@Value("${KAKAO.REST_API_KEY}") String kakaoApiKey,
                         @Value("${NAVER.CLIENT_ID}") String naverClientId,
                         @Value("${NAVER.CLIENT_SECRET}") String naverClientSecret) {
        this.webClient = WebClient.builder()
                .defaultHeader("Authorization", "KakaoAK " + kakaoApiKey)
                .defaultHeader("X-Naver-Client-Id", naverClientId)
                .defaultHeader("X-Naver-Client-Secret", naverClientSecret)
                .build();
    }

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
        List<KakaoApiRes.RestaurantResponse> restaurants = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("dapi.kakao.com")
                        .path("/v2/local/search/keyword.json")
                        .queryParam("x", locationReq.getLongitude())
                        .queryParam("y", locationReq.getLatitude())
                        .queryParam("query", "음식점")
                        .queryParam("radius", 500)
                        .queryParam("page", 1)
                        .queryParam("size", MAX_RESTAURANT_NUM)
                        .build())
                .header("Authorization", "KakaoAK " + kakaoApiKey)
                .retrieve()
                .bodyToMono(KakaoApiRes.class)
                .block()
                .getDocuments();
        Collections.shuffle(restaurants);

        return restaurants.subList(0, 5);
    }

    public List<KakaoApiRes.RestaurantResponse> reSuggestRestaurant(ReSuggestReq reSuggestReq) {
        if(reSuggestReq.getPage().equals(3L)){
            reSuggestReq.setPage(1L);
        }

        List<KakaoApiRes.RestaurantResponse> restaurants = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("dapi.kakao.com")
                        .path("/v2/local/search/keyword.json")
                        .queryParam("x", reSuggestReq.getLongitude())
                        .queryParam("y", reSuggestReq.getLatitude())
                        .queryParam("query", "음식점")
                        .queryParam("radius", 500)
                        .queryParam("page", reSuggestReq.getPage())
                        .queryParam("size", MAX_RESTAURANT_NUM)
                        .build())
                .header("Authorization", "KakaoAK " + kakaoApiKey)
                .retrieve()
                .bodyToMono(KakaoApiRes.class)
                .block()
                .getDocuments();
        Collections.shuffle(restaurants);

        return restaurants.subList(0, 1);
    }

    public List<KakaoApiRes.RestaurantResponse> reSuggestOneRestaurant(ReSuggestReq reSuggestReq){
        List<KakaoApiRes.RestaurantResponse> restaurants = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .host("dapi.kakao.com")
                        .path("/v2/local/search/keyword.json")
                        .queryParam("x", reSuggestReq.getLongitude())
                        .queryParam("y", reSuggestReq.getLatitude())
                        .queryParam("query", "음식점")
                        .queryParam("radius", 500)
                        .queryParam("page", reSuggestReq.getPage())
                        .queryParam("size", MAX_RESTAURANT_NUM)
                        .build())
                .header("Authorization", "KakaoAK " + kakaoApiKey)
                .retrieve()
                .bodyToMono(KakaoApiRes.class)
                .block()
                .getDocuments();
        Collections.shuffle(restaurants);

        return restaurants.subList(0, 5);
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
