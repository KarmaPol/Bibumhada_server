package com.bibum_server.domain.controller;

import com.bibum_server.domain.service.RoomService;
import com.bibum_server.domain.dto.request.VoteReq;
import com.bibum_server.domain.dto.response.NaverApiItemRes;
import com.bibum_server.domain.dto.response.RoomRes;
import com.bibum_server.domain.dto.request.LocationReq;
import com.bibum_server.domain.dto.response.MostPopularRestaurantRes;
import com.bibum_server.domain.dto.response.VoteRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Tag(name = "방 API", description = "방 관련 API 명세입니다.")
@RestController
@RequiredArgsConstructor
public class TodayMenuController {

    private final RoomService roomService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

    @Operation(summary = "방 생성", description = """
             방을 생성합니다.
             데이터 예시)
             "longitude" : "127.06283102249932",
             "latitude" : "37.514322572335935"
             """)
    @PostMapping("/api/v1/create")
    public RoomRes CreateRoom(@RequestBody LocationReq locationReq) {
        return roomService.createRoom(locationReq);
    }

    @Operation(summary = "방 정보 확인", description = "특정 방 정보를 확인합니다.")
    @GetMapping("/api/v1/{roomId}")
    public RoomRes getRoomInfo(@PathVariable Long roomId){
        return roomService.getRoomInfo(roomId);
    }

    @Operation(summary = "메뉴 투표", description = "특정 방의 메뉴에 투표합니다.")
    @PostMapping(value = "/api/v1/{roomId}/vote",produces = "application/json")
    public VoteRes voteRestaurant(@PathVariable Long roomId, @RequestBody VoteReq voteReq) {
        return roomService.voteRestaurant(roomId, voteReq);
    }

    @Operation(summary = "메뉴 결과 확인", description = "특정 방의 메뉴 투표 결과를 확인합니다.")
    @GetMapping("/api/v1/{roomId}/result")
    public MostPopularRestaurantRes checkBestRestaurant(@PathVariable("roomId") Long roomId) {
        return roomService.checkBestRestaurant(roomId);
    }

    @Operation(summary = "메뉴 전체 재추천", description = "특정 방의 메뉴 전체(5개)를 재생성합니다.")
    @PostMapping("/api/v1/resuggest/{roomId}")
    public RoomRes ReSuggestRestaurants(@PathVariable("roomId") Long roomId) {
        return roomService.ReSuggestRestaurants(roomId);
    }

    @Operation(summary = "메뉴 단일 재추천", description = "특정 방의 메뉴 1개를 재생성합니다.")
    @PostMapping("/api/v1/{roomId}/resuggest/{restaurantId}")
    public RoomRes ReSuggestOneRestaurant(@PathVariable("roomId")Long roomId, @PathVariable("restaurantId") Long restaurantId){
        return roomService.reSuggestOneRestaurant(roomId, restaurantId);
    }

    @Operation(summary = "레스토랑 정보 조회", description = "특정 메뉴의 레스토랑 정보를 조회합니다.")
    @GetMapping("/api/v1/info/{restaurantId}")
    public NaverApiItemRes convertKakaoUrl(@PathVariable("restaurantId") long restaurantId) throws UnsupportedEncodingException {
        return roomService.convertUrl(restaurantId);
    }
}
