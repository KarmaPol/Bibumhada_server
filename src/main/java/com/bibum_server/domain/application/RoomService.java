package com.bibum_server.domain.application;

import com.bibum_server.domain.dto.response.RestaurantRes;
import com.bibum_server.domain.dto.response.RoomRes;
import com.bibum_server.domain.dto.request.LocationReq;
import com.bibum_server.domain.dto.response.KakaoApiRes;
import com.bibum_server.domain.dto.response.MostPopularRestaurantRes;
import com.bibum_server.domain.restaurant.entity.Restaurant;
import com.bibum_server.domain.restaurant.repository.RestaurantRepository;
import com.bibum_server.domain.room.entity.Room;
import com.bibum_server.domain.room.repository.RoomRepository;
import com.bibum_server.domain.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;


@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RestaurantRepository restaurantRepository;
    private final WebClientUtil webClientUtil;

    @Transactional
    public RoomRes createRoom(LocationReq locationReq){
        Room room = Room.builder()
                .x(locationReq.getLongitude())
                .y(locationReq.getLatitude())
                .build();

        roomRepository.save(room);

        List<KakaoApiRes.RestaurantResponse> restaurantResponses = webClientUtil.getRestaurant(locationReq);
        List<Restaurant> restaurants = restaurantResponses.stream().map(restaurantResponse -> Restaurant.builder()
                .title(restaurantResponse.getPlace_name())
                .category(restaurantResponse.getCategory_name().substring(6).trim())
                .link(restaurantResponse.getPlace_url())
                .count(0L)
                .distance(Long.valueOf(restaurantResponse.getDistance()))
                .room(room)
                .build()).collect(Collectors.toList());

        room.addRestaurant(restaurants);

        restaurantRepository.saveAll(restaurants);
        List<RestaurantRes> restaurantRes = restaurants.stream().map(RestaurantRes::fromEntity).toList();

        return RoomRes.builder()
                .id(room.getId())
                .x(room.getX())
                .y(room.getY())
                .restaurantResList(restaurantRes)
                .build();


    }

    @Transactional
    public RestaurantRes voteRestaurant(Long roomId, Long restaurantId){
        Restaurant restaurant = restaurantRepository.findByRoomIdAndId(roomId,restaurantId);
        restaurant.incrementCount();
        return RestaurantRes.fromEntity(restaurant);

    }
    public MostPopularRestaurantRes checkBestRestaurant(Long roomId){
        List<RestaurantRes> resultList = restaurantRepository.findAllByRoomId(roomId)
                .stream().map(RestaurantRes::fromEntity).sorted(Comparator.comparing(RestaurantRes::getCount).reversed()).toList();

        IntStream.range(0, resultList.size())
                .forEach(i -> resultList.get(i).setRank((i + 1)+"등"));

        RestaurantRes bestRestaurant = resultList.get(0);

        return MostPopularRestaurantRes.builder()
                .restaurantId(bestRestaurant.getId())
                .restaurantTitle(bestRestaurant.getTitle())
                .category(bestRestaurant.getCategory())
                .count(bestRestaurant.getCount())
                .url(bestRestaurant.getLink())
                .distance(bestRestaurant.getDistance())
                .rank(bestRestaurant.getRank())
                .voteResult(resultList.stream()
                        .sorted(Comparator.comparing(RestaurantRes::getCount).reversed())
                        .limit(5).collect(Collectors.toList()))
                .build();

    }

}
