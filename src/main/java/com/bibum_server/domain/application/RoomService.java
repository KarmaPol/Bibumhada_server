package com.bibum_server.domain.application;

import com.bibum_server.domain.dto.request.ReSuggestReq;
import com.bibum_server.domain.dto.response.*;
import com.bibum_server.domain.dto.request.LocationReq;
import com.bibum_server.domain.restaurant.entity.Restaurant;
import com.bibum_server.domain.restaurant.repository.RestaurantRepository;
import com.bibum_server.domain.room.entity.Room;
import com.bibum_server.domain.room.repository.RoomRepository;
import com.bibum_server.domain.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RestaurantRepository restaurantRepository;
    private final WebClientUtil webClientUtil;

    @Transactional
    public RoomRes createRoom(LocationReq locationReq) {
        Room room = Room.builder()
                .x(locationReq.getLongitude())
                .y(locationReq.getLatitude())
                .total(0L)
                .page(1L)
                .build();

        roomRepository.save(room);

        List<KakaoApiRes.RestaurantResponse> restaurantResponses = webClientUtil.getRestaurant(locationReq);
        List<Restaurant> restaurants = restaurantResponses.stream().map(restaurantResponse -> Restaurant.builder()
                .title(restaurantResponse.getPlace_name())
                .category(restaurantResponse.getCategory_name().substring(6).trim())
                .link(restaurantResponse.getPlace_url())
                .count(0L)
                .address(restaurantResponse.getAddress_name())
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
                .total(room.getTotal())
                .restaurantResList(restaurantRes)
                .build();
    }

    @Transactional
    public RestaurantRes voteRestaurant(Long roomId, Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findByRoomIdAndId(roomId, restaurantId);
        Optional<Room> room = roomRepository.findById(roomId);
        room.ifPresent(Room::incrementTotal);
        restaurant.incrementCount();
        return RestaurantRes.fromEntity(restaurant);
    }

    public MostPopularRestaurantRes checkBestRestaurant(Long roomId) {
        long total = roomRepository.findById(roomId).get().getTotal();

        List<RestaurantRes> resultList = restaurantRepository.findAllByRoomId(roomId)
                .stream().map(RestaurantRes::fromEntity).sorted(Comparator.comparing(RestaurantRes::getCount).reversed()).toList();

        long rank = 1L;
        Long prevCount = null;
        for (RestaurantRes currentRestaurant : resultList) {
            if (prevCount != null && !currentRestaurant.getCount().equals(prevCount)) {
                rank++;
            }
            currentRestaurant.setRank(rank);
            prevCount = currentRestaurant.getCount();
        }

        Map<Boolean, List<RestaurantRes>> partitionedResult = resultList.stream()
                .collect(Collectors.partitioningBy(r -> 1L == r.getRank()));
        List<RestaurantRes> rankOneRestaurants = partitionedResult.get(true);
        List<RestaurantRes> otherRestaurants = partitionedResult.get(false);


        return MostPopularRestaurantRes.builder()
                .total(total)
                .win(rankOneRestaurants)
                .voteResult(otherRestaurants)
                .build();
    }

    @Transactional
    public RoomRes retry(Long roomId) {
        restaurantRepository.deleteAllByRoomId(roomId);
        Room room = roomRepository.findById(roomId).orElse(null);

        room.updateTotal(0L);
        roomRepository.save(room);
        LocationReq locationReq = LocationReq.builder()
                .longitude(room.getX())
                .latitude(room.getY())
                .build();


        List<KakaoApiRes.RestaurantResponse> restaurantResponses = webClientUtil.getRestaurant(locationReq);
        List<Restaurant> restaurants = restaurantResponses.stream().map(restaurantResponse -> Restaurant.builder()
                .title(restaurantResponse.getPlace_name())
                .category(restaurantResponse.getCategory_name().substring(6).trim())
                .link(restaurantResponse.getPlace_url())
                .count(0L)
                .address(restaurantResponse.getAddress_name())
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
                .total(0L)
                .restaurantResList(restaurantRes)
                .build();
    }

    @Transactional
    public RoomRes ReSuggestRestaurant(Long roomId){
        restaurantRepository.deleteAllByRoomId(roomId);
        Room room = roomRepository.findById(roomId).orElseThrow(NoSuchElementException::new);
        ReSuggestReq reSuggestReq = ReSuggestReq.builder()
                .latitude(room.getY())
                .longitude(room.getX())
                .page(room.getPage() + 1L)
                .build();
        room.getNextPage();
        roomRepository.save(room);
        List<KakaoApiRes.RestaurantResponse> restaurantResponses = webClientUtil.reSuggestRestaurant(reSuggestReq);
        List<Restaurant> restaurants = restaurantResponses.stream().map(restaurantResponse -> Restaurant.builder()
                .title(restaurantResponse.getPlace_name())
                .category(restaurantResponse.getCategory_name().substring(6).trim())
                .link(restaurantResponse.getPlace_url())
                .count(0L)
                .address(restaurantResponse.getAddress_name())
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
                .total(room.getTotal())
                .restaurantResList(restaurantRes)
                .build();
    }

    public NaverApiItemRes convertUrl(Long restaurantId) throws UnsupportedEncodingException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(RuntimeException::new);
        return webClientUtil.convertRestaurantUrl(restaurant.getTitle());

    }
}
