package com.bibum_server.domain.presentation;

import com.bibum_server.domain.AbstractRestDocsTests;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;
import java.util.stream.LongStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(TodayMenuController.class)
class TodayMenuControllerTest extends AbstractRestDocsTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;

    ObjectMapper mapper = new ObjectMapper();

    @DisplayName("방을 생성한다.")
    @Test
    void createRoom() throws Exception {
        LocationReq location = new LocationReq();
        location.setLatitude("37.230840");
        location.setLongitude("127.190607");
        String locationRequest = mapper.writeValueAsString(location);

        Room room = Room.builder()
                .id(1L)
                .x("1")
                .y("2")
                .build();

        List<Restaurant> restaurantList = LongStream.range(0L, 3L)
                .mapToObj((i) -> Restaurant.builder()
                        .room(room)
                        .id(i)
                        .distance(1L)
                        .count(0L)
                        .link("www.test.com")
                        .category("testCat")
                        .title("test")
                        .build()
                ).toList();
        room.addRestaurant(restaurantList);

        List<RestaurantRes> restaurantResList = restaurantList.stream().map(RestaurantRes::fromEntity).toList();
        RoomRes mockResponse = RoomRes.builder()
                .id(room.getId())
                .x(room.getX())
                .y(room.getY())
                .restaurantResList(restaurantResList)
                .build();
        given(roomService.createRoom(any(LocationReq.class))).willReturn(mockResponse);


        this.mockMvc.perform(post("/create")  // 요청 URL 설정
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(locationRequest))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }


    }
