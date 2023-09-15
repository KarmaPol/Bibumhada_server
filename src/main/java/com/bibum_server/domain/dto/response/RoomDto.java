package com.bibum_server.domain.dto.response;

import com.bibum_server.domain.dto.response.RestaurantDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter

public class RoomDto {
    Long id;
    String x;
    String y;
    List<RestaurantDto> restaurantDtoList;
    @Builder
    public RoomDto(Long id, String x, String y, List<RestaurantDto> restaurantDtoList) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.restaurantDtoList = restaurantDtoList;
    }
}
