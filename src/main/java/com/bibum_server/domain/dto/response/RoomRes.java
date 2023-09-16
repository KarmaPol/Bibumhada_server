package com.bibum_server.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter

public class RoomRes {
    Long id;
    String x;
    String y;
    List<RestaurantRes> restaurantResList;
    @Builder
    public RoomRes(Long id, String x, String y, List<RestaurantRes> restaurantResList) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.restaurantResList = restaurantResList;
    }
}
