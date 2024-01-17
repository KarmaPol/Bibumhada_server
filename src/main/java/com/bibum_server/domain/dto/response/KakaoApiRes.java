package com.bibum_server.domain.dto.response;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class KakaoApiRes {
    public List<RestaurantResponse> getDocuments() {
        return documents;
    }

    private MetaResponse meta;
    private List<RestaurantResponse> documents = new ArrayList<>();
    @Getter
    public static class RestaurantResponse {
        String address_name;
        String category_group_code;
        String category_group_name;
        String category_name;
        String distance;
        String id;
        String phone;
        String place_name;
        String place_url;
        String road_address_name;
        String x;
        String y;
    }

    @Getter
    public static class MetaResponse {
        Boolean is_end;
    }
}
