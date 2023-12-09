package com.bibum_server.domain;

import com.bibum_server.domain.dto.response.MostPopularRestaurantRes;
import com.bibum_server.domain.dto.response.RestaurantRes;
import com.bibum_server.domain.restaurant.entity.Restaurant;
import com.bibum_server.domain.room.entity.Room;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class TestUtil {

    public static final long LEAST_ID = 1L;
    public static final long MAX_ID = 6L;

    public static Room CreateTestRoom() {
        return Room.builder()
                .id(1L)
                .x("1")
                .y("2")
                .total(0L)
                .page(1L)
                .build();
    }
    public static List<Restaurant> CreateTestRestaurantList(Room room){
        return LongStream.range(1L, 6L)
                .mapToObj((i) -> Restaurant.builder()
                        .room(room)
                        .id(i)
                        .distance(1L)
                        .count(0L)
                        .link("www.test.com")
                        .category("testCat")
                        .address("testAddress")
                        .title("test")
                        .build()
                ).toList();
    }
    public static List<Restaurant> CreatereSuggestedRestaurantList(Room room){
        return LongStream.range(LEAST_ID, MAX_ID)
                .mapToObj((i) -> Restaurant.builder()
                        .room(room)
                        .id(i)
                        .distance(99L)
                        .count(0L)
                        .link("www.nextPageLink.com")
                        .category("nextPageCategory")
                        .address("nextPageAddress")
                        .title("NextPageTitle")
                        .build()
                ).toList();
    }
    public static MostPopularRestaurantRes CreateTestBestRestaurantList(Room room,List<Restaurant>restaurantList){
        List<RestaurantRes> restaurantResList = IntStream.range(0, restaurantList.size())
                .mapToObj(i -> {
                    Restaurant restaurant = TestUtil.CreateTestRestaurantList(room).get(i);
                    RestaurantRes res = RestaurantRes.fromEntity(restaurant);
                    res.setRank((long) i + 1);
                    return res;
                }).sorted(Comparator.comparing(RestaurantRes::getRank)).toList();

        Map<Boolean, List<RestaurantRes>> partitionedResult = restaurantResList.stream()
                .collect(Collectors.partitioningBy(r -> 1L==r.getRank()));
        List<RestaurantRes> rankOneRestaurants = partitionedResult.get(true);
        List<RestaurantRes> otherRestaurants = partitionedResult.get(false);

        return MostPopularRestaurantRes.builder()
                .total(8L)
                .win(rankOneRestaurants)
                .voteResult(otherRestaurants)
                .build();
    }
}
