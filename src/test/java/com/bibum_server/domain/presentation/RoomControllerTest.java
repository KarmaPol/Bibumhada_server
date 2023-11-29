package com.bibum_server.domain.presentation;

import com.bibum_server.domain.AbstractRestDocsTests;
import com.bibum_server.domain.TestUtil;
import com.bibum_server.domain.application.RoomService;
import com.bibum_server.domain.dto.request.LocationReq;
import com.bibum_server.domain.dto.response.RestaurantRes;
import com.bibum_server.domain.dto.response.RoomRes;
import com.bibum_server.domain.restaurant.entity.Restaurant;
import com.bibum_server.domain.room.entity.Room;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(TodayMenuController.class)
class RoomControllerTest extends AbstractRestDocsTests {

    ObjectMapper mapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private RoomService roomService;

    @DisplayName("Create Room.")
    @Test
    void createRoom() throws Exception {
        LocationReq location = LocationReq.builder()
                .latitude("37.230840")
                .longitude("127.190607")
                .build();

        String locationRequest = mapper.writeValueAsString(location);

        Room room = TestUtil.CreateTestRoom();

        List<Restaurant> restaurantList = TestUtil.CreateTestRestaurantList(room);
        room.addRestaurants(restaurantList);

        List<RestaurantRes> restaurantResList = restaurantList.stream().map(RestaurantRes::fromEntity).toList();
        RoomRes mockResponse = RoomRes.builder()
                .id(room.getId())
                .x(room.getX())
                .y(room.getY())
                .total(room.getTotal())
                .restaurantResList(restaurantResList)
                .build();
        given(roomService.createRoom(any(LocationReq.class))).willReturn(mockResponse);


        this.mockMvc.perform(post("/api/v1/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(locationRequest))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    void ReSuggest() throws Exception {
        Long roomId = 1L;
        Room room = TestUtil.CreateTestRoom();
        List<Restaurant> restaurantList = TestUtil.CreatereSuggestedRestaurantList(room);
        List<RestaurantRes> restaurantResList = restaurantList.stream().map(RestaurantRes::fromEntity).toList();
        given(roomService.ReSuggestRestaurants(roomId)).willReturn(RoomRes.builder().id(room.getId())
                .total(room.getTotal())
                .x(room.getX())
                .y(room.getY())
                .restaurantResList(restaurantResList)
                .build());

        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/resuggest/{roomId}", roomId))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    void retry() throws Exception {
        long roomId = 1L;
        Room room = TestUtil.CreateTestRoom();

        List<Restaurant> restaurantList = TestUtil.CreateTestRestaurantList(room);
        room.addRestaurants(restaurantList);

        List<RestaurantRes> restaurantResList = restaurantList.stream().map(RestaurantRes::fromEntity).toList();
        RoomRes mockResponse = RoomRes.builder()
                .id(room.getId())
                .x(room.getX())
                .y(room.getY())
                .total(room.getTotal())
                .restaurantResList(restaurantResList)
                .build();
        given(roomService.retry(any(Long.class))).willReturn(mockResponse);


        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/retry/{roomId}", roomId))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }

    @Test
    void getRoomInfo() throws Exception {
        long roomId = 1L;
        Room room = TestUtil.CreateTestRoom();
        List<Restaurant> restaurantList = TestUtil.CreateTestRestaurantList(room);
        List<RestaurantRes> restaurantResList = restaurantList.stream().map(RestaurantRes::fromEntity).toList();
        RoomRes mockResponse = RoomRes.builder()
                .id(room.getId())
                .x(room.getX())
                .y(room.getY())
                .total(room.getTotal())
                .restaurantResList(restaurantResList)
                .build();
        given(roomService.getRoomInfo(any(Long.class))).willReturn(mockResponse);


        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/api/v1/{roomId}", roomId))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }
    @Test
    void ReSuggestOneRestaurant() throws Exception{
        long roomId = 1L;
        long restaurantId = 1L;
        Room room = TestUtil.CreateTestRoom();
        Restaurant restaurant = Restaurant.builder()
                .room(room)
                .id(1L)
                .title("ReSuggestedRestaurant")
                .link("www.ResuggestURL.com")
                .distance(123L)
                .count(0L)
                .category("TestCategory")
                .address("testAddress")
                .build();
        RestaurantRes response = RestaurantRes.fromEntity(restaurant);
        given(roomService.reSuggestOneRestaurant(any(Long.class),any(Long.class))).willReturn(response);
        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/api/v1/{roomId}/resuggest/{restaurantId}",roomId,restaurantId))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }
}
