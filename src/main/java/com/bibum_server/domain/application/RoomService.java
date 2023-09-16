package com.bibum_server.domain.application;

import com.bibum_server.domain.dto.response.RoomDto;
import com.bibum_server.domain.dto.request.LocationReq;
import com.bibum_server.domain.dto.response.KakaoApiResponse;
import com.bibum_server.domain.dto.response.RestaurantDto;
import com.bibum_server.domain.restaurant.entity.Restaurant;
import com.bibum_server.domain.restaurant.repository.RestaurantRepository;
import com.bibum_server.domain.room.entity.Room;
import com.bibum_server.domain.room.repository.RoomRepository;
import com.bibum_server.domain.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RestaurantRepository restaurantRepository;
    private final WebClientUtil webClientUtil;

    @Transactional
    public RoomDto createRoom(LocationReq locationReq){
        Room room = Room.builder()
                .x(locationReq.getLongitude())
                .y(locationReq.getLatitude())
                .build();

        roomRepository.save(room);

        List<KakaoApiResponse.RestaurantResponse> restaurantResponses = webClientUtil.getRestaurant(locationReq);
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
        List<RestaurantDto> RestaurantDtos = restaurants.stream().map(RestaurantDto::fromEntity).toList();

        return RoomDto.builder()
                .id(room.getId())
                .x(room.getX())
                .y(room.getY())
                .restaurantDtoList(RestaurantDtos)
                .build();


    }

    /*@Transactional
    public RoomDto voteRestaurant(Long restaurantId){
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(()-> new NoSuchElementException);
        restaurant.
    }*/
}
