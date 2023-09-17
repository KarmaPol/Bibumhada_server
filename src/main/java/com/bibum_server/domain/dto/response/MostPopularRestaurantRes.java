package com.bibum_server.domain.dto.response;

import com.bibum_server.domain.restaurant.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MostPopularRestaurantRes {
    List<RestaurantRes> win;

    List<RestaurantRes> voteResultRes;

    @Builder
    public MostPopularRestaurantRes(List<RestaurantRes>win, List<RestaurantRes> voteResult) {
        this.win = win;
        this.voteResultRes= voteResult;
    }
}
