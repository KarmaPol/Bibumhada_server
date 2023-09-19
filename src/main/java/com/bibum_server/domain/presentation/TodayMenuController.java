package com.bibum_server.domain.presentation;

import com.bibum_server.domain.application.RoomService;
import com.bibum_server.domain.dto.response.NaverApiItemRes;
import com.bibum_server.domain.dto.response.RestaurantRes;
import com.bibum_server.domain.dto.response.RoomRes;
import com.bibum_server.domain.dto.request.LocationReq;
import com.bibum_server.domain.dto.response.MostPopularRestaurantRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequiredArgsConstructor
public class TodayMenuController {

    private final RoomService roomService;

    @PostMapping("/create")
    public RoomRes CreateRoom(@RequestBody LocationReq locationReq){
        return roomService.createRoom(locationReq);
    }
    @PostMapping("/{roomId}/vote/{restaurantId}")
    public RestaurantRes voteRestaurant(@PathVariable("roomId") Long roomId, @PathVariable("restaurantId") Long restaurantId){
       return roomService.voteRestaurant(roomId,restaurantId);
    }
    @GetMapping("/{roomId}/result")
    public MostPopularRestaurantRes checkBestRestaurant(@PathVariable("roomId") Long roomId){
        return roomService.checkBestRestaurant(roomId);
    }
    @PostMapping("/retry/{roomId}")
    public RoomRes ReCreateRoom(@PathVariable("roomId") Long roomId){
        return roomService.retry(roomId);
    }

    @PostMapping("/resuggest/{roomId}")
    public RoomRes ReSuggestRestaurant(@PathVariable("roomId") Long roomId){
        return roomService.ReSuggestRestaurant(roomId);
    }
    @GetMapping("/info/{restaurantId}")
    public NaverApiItemRes convertKakaoUrl(@PathVariable("restaurantId") long restaurantId) throws UnsupportedEncodingException {
        return roomService.convertUrl(restaurantId);
    }

}
