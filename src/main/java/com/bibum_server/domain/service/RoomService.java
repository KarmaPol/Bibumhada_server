package com.bibum_server.domain.service;

import com.bibum_server.domain.dto.request.VoteReq;
import com.bibum_server.domain.dto.response.*;
import com.bibum_server.domain.dto.request.LocationReq;
import com.bibum_server.domain.exception.RestaurantNotFoundException;
import com.bibum_server.domain.exception.ResuggestUnavailableException;
import com.bibum_server.domain.exception.RoomNotFoundException;
import com.bibum_server.domain.restaurant.entity.Restaurant;
import com.bibum_server.domain.restaurant.repository.RestaurantCustomRepository;
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
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantCustomRepository restaurantCustomRepository;
    private final WebClientUtil webClientUtil;

    @Transactional(readOnly = true)
    public RoomRes getRoomInfo(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        List<RestaurantRes> restaurantList = restaurantCustomRepository.getRestaurantByRoomLimit5(room)
                .stream()
                .map(RestaurantRes::fromEntity)
                .toList();
        return RoomRes.builder()
                .id(room.getId())
                .x(room.getX())
                .y(room.getY())
                .total(room.getTotal())
                .restaurantResList(restaurantList)
                .build();
    }

    public RoomRes createRoom(LocationReq locationReq) {
        Room room = Room.builder()
                .x(locationReq.getLongitude())
                .y(locationReq.getLatitude())
                .total(0L)
                .page(1L)
                .build();

        Room savedRoom = roomRepository.save(room);

        addRoomRestaurant(room);

        return getRoomInfo(savedRoom.getId());
    }

    public VoteRes voteRestaurant(Long roomId,VoteReq voteReq) {
        List<Long> restaurantReqList = voteReq.getRestaurantIdList();
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        List<Restaurant> restaurantList = restaurantRepository.findByRoomIdAndIdIn(roomId, restaurantReqList);

        restaurantList.forEach(Restaurant::incrementCount);
        room.incrementTotal();

        List<VoteRes.RestaurantVote> votes =
                restaurantList.stream()
                        .map(r -> {
                            return VoteRes.RestaurantVote.builder()
                                    .id(r.getId())
                                    .title(r.getTitle())
                                    .category(r.getCategory())
                                    .count(r.getCount())
                                    .link(r.getLink())
                                    .distance(r.getDistance())
                                    .address(r.getAddress())
                                    .rank(0L)
                                    .roomId(room.getId())
                                    .build();
                        })
                        .toList();

        return new VoteRes(votes);
    }

    public MostPopularRestaurantRes checkBestRestaurant(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);
        long total = roomRepository.findById(roomId).get().getTotal();

        List<RestaurantRes> resultList = restaurantCustomRepository.getRestaurantByRoomLimit5(room)
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

    public RoomRes ReSuggestRestaurants(Long roomId) {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);

        if(room.isResuggestAllAvailable()){
            if(room.getIsEnd()) {
                throw new ResuggestUnavailableException();
            }
            addRoomRestaurant(room);
        }

        List<Restaurant> restaurantsByRoom = restaurantCustomRepository.getRestaurantByRoomLimit5(room);
        restaurantsByRoom.forEach(Restaurant::changeRoomIsExposedFalse);

        return getRoomInfo(room.getId());
    }

    public RoomRes reSuggestOneRestaurant(Long roomId, Long restaurantId) {
        Room room = roomRepository.findById(roomId).orElseThrow(RoomNotFoundException::new);

        if(room.isResuggestOneAvailable()){
            if(room.getIsEnd()) {
                throw new ResuggestUnavailableException();
            }
            addRoomRestaurant(room);
        }

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(RestaurantNotFoundException::new);
        restaurant.changeRoomIsExposedFalse();

        return getRoomInfo(room.getId());
    }

    public NaverApiItemRes convertUrl(Long restaurantId) throws UnsupportedEncodingException {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(RestaurantNotFoundException::new);
        return webClientUtil.convertRestaurantUrl(restaurant.getTitle());
    }

    private void addRoomRestaurant(Room room) {
        KakaoApiRes kakaoApiRes = webClientUtil.getRestaurant(new LocationReq(room.getX(), room.getY()), room.getPage());
        room.setNextPage();

        if(kakaoApiRes.getMeta().getIs_end()) {
            room.setIsEndTrue();
        }

        List<KakaoApiRes.RestaurantResponse> restaurantResponses = kakaoApiRes.getDocuments();
        List<Restaurant> restaurants = restaurantResponses.stream().map(restaurantResponse -> Restaurant.builder()
                .title(restaurantResponse.getPlace_name())
                .category(restaurantResponse.getCategory_name().substring(6).trim())
                .link(restaurantResponse.getPlace_url())
                .count(0L)
                .address(restaurantResponse.getAddress_name())
                .distance(Long.valueOf(restaurantResponse.getDistance().equals("") ? "0": restaurantResponse.getDistance()))
                .room(room)
                .build()).collect(Collectors.toList());

        room.addRestaurants(restaurants);

        restaurantRepository.saveAll(restaurants);
    }
}
