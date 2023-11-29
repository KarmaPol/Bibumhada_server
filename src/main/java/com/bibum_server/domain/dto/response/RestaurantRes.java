package com.bibum_server.domain.dto.response;

import com.bibum_server.domain.restaurant.entity.Restaurant;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestaurantRes {

    Long id;

    String title;

    String category;

    Long count;

    String link;

    Long distance;

    String address;

    Boolean isRetried;

    Long roomId;  // Room ID

    Long rank;

    @Builder
    public RestaurantRes(Long id, String title, String category, Long count,
                         String link, Long distance, String address, Boolean isRetried, Long roomId, Long rank) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.count = count;
        this.link = link;
        this.distance = distance;
        this.address = address;
        this.isRetried = isRetried;
        this.roomId = roomId;
        this.rank = rank;
    }

    public static RestaurantRes fromEntity(Restaurant restaurant) {
        return RestaurantRes.builder()
                .id(restaurant.getId())
                .category(restaurant.getCategory())
                .distance(restaurant.getDistance())
                .count(restaurant.getCount())
                .title(restaurant.getTitle())
                .link(restaurant.getLink())
                .address(restaurant.getAddress())
                .isRetried(restaurant.getIsRetried())
                .roomId(restaurant.getRoom().getId())
                .rank(0L)
                .build();

    }
}

