package com.bibum_server.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class MostPopularRestaurantRes {
    Long total;
    List<RestaurantRes> win;

    List<RestaurantRes> voteResultRes;

    @Builder
    public MostPopularRestaurantRes(Long total, List<RestaurantRes>win, List<RestaurantRes> voteResult) {
        this.total = total;
        this.win = win;
        this.voteResultRes= voteResult;
    }
}
