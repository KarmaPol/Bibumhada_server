package com.bibum_server.domain.dto.response;

import com.bibum_server.domain.restaurant.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MostPopularRestaurantRes {
    Long restaurantId;
    String restaurantTitle;
    String category;
    Long distance;
    String url;
    Long count;
    String rank;

    List<RestaurantRes> voteResultRes;

    @Builder
    public MostPopularRestaurantRes(Long restaurantId,String restaurantTitle, String category, Long distance, String url, Long count, String rank, List<RestaurantRes> voteResult) {
        this.restaurantId = restaurantId;
        this.restaurantTitle = restaurantTitle;
        this.category = category;
        this.distance = distance;
        this.url = url;
        this.count = count;
        this.rank = rank;
        this.voteResultRes= voteResult;
    }
}
