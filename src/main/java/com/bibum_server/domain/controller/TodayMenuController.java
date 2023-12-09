package com.bibum_server.domain.controller;

import com.bibum_server.domain.service.RoomService;
import com.bibum_server.domain.dto.request.VoteReq;
import com.bibum_server.domain.dto.response.NaverApiItemRes;
import com.bibum_server.domain.dto.response.RoomRes;
import com.bibum_server.domain.dto.request.LocationReq;
import com.bibum_server.domain.dto.response.MostPopularRestaurantRes;
import com.bibum_server.domain.dto.response.VoteRes;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@RestController
@RequiredArgsConstructor
public class TodayMenuController {

    private final RoomService roomService;

    @PostMapping("/api/v1/create")
    public RoomRes CreateRoom(@RequestBody LocationReq locationReq) {
        return roomService.createRoom(locationReq);
    }

    @GetMapping("/api/v1/{roomId}")
    public RoomRes getRoomInfo(@PathVariable Long roomId){
        return roomService.getRoomInfo(roomId);
    }

    @PostMapping(value = "/api/v1/{roomId}/vote",produces = "application/json")
    public VoteRes voteRestaurant(@PathVariable Long roomId, @RequestBody VoteReq voteReq) {
        return roomService.voteRestaurant(roomId, voteReq);
    }

    @GetMapping("/api/v1/{roomId}/result")
    public MostPopularRestaurantRes checkBestRestaurant(@PathVariable("roomId") Long roomId) {
        return roomService.checkBestRestaurant(roomId);
    }

    @PostMapping("/api/v1/retry/{roomId}")
    public RoomRes ReCreateRoom(@PathVariable("roomId") Long roomId) {
        return roomService.retry(roomId);
    }

    @PostMapping("/api/v1/resuggest/{roomId}")
    public RoomRes ReSuggestRestaurants(@PathVariable("roomId") Long roomId) {
        return roomService.ReSuggestRestaurants(roomId);
    }

    @PostMapping("/api/v1/{roomId}/resuggest/{restaurantId}")
    public RoomRes ReSuggestOneRestaurant(@PathVariable("roomId")Long roomId, @PathVariable("restaurantId") Long restaurantId){
        return roomService.reSuggestOneRestaurant(roomId, restaurantId);
    }

    @GetMapping("/api/v1/info/{restaurantId}")
    public NaverApiItemRes convertKakaoUrl(@PathVariable("restaurantId") long restaurantId) throws UnsupportedEncodingException {
        return roomService.convertUrl(restaurantId);
    }

    private Long decodeUrl(String roomId) {
        byte[] decodedBytes = Base64.getDecoder().decode(roomId);
        String id = new String(decodedBytes);
        return Long.parseLong(id);
    }
}
