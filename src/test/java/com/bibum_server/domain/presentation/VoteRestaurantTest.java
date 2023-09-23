package com.bibum_server.domain.presentation;

import com.bibum_server.domain.AbstractRestDocsTests;
import com.bibum_server.domain.TestUtil;
import com.bibum_server.domain.application.RoomService;
import com.bibum_server.domain.dto.request.VoteReq;
import com.bibum_server.domain.dto.response.MostPopularRestaurantRes;
import com.bibum_server.domain.dto.response.RestaurantRes;
import com.bibum_server.domain.dto.response.VoteRes;
import com.bibum_server.domain.restaurant.entity.Restaurant;
import com.bibum_server.domain.restaurant.repository.RestaurantRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@WebMvcTest(TodayMenuController.class)
public class VoteRestaurantTest extends AbstractRestDocsTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomService roomService;
    @MockBean
    private RestaurantRepository restaurantRepository;

    ObjectMapper mapper = new ObjectMapper();


    @DisplayName("Vote Restaurant")
    @Test
    void voteRestaurant() throws Exception{
        //given
        long roomId = 1L;
        List<Long> restaurantId = new ArrayList<>();
        restaurantId.add(1L);
        restaurantId.add(2L);
        VoteReq voteReq = VoteReq.builder()
                .restaurantIdList(restaurantId)
                .build();
        String VoteRequest = mapper.writeValueAsString(voteReq);

        List<VoteRes.RestaurantVote> voteResList = LongStream.range(1L,3L).mapToObj(i->{
            return VoteRes.RestaurantVote.builder()
                    .roomId(i)
                    .id(i)
                    .rank(0L)
                    .address("testAddress")
                    .distance(999L)
                    .link("www.test.com")
                    .count(1L)
                    .category("testCategory")
                    .title("testRestaurant")
                    .build();
        }).toList();
        VoteRes voteRes = VoteRes.builder().restaurantVotes(voteResList).build();

        given(roomService.voteRestaurant(any(),any())).willReturn(voteRes);


        this.mockMvc.perform(RestDocumentationRequestBuilders.post("/{roomId}/vote",
                roomId).contentType(MediaType.APPLICATION_JSON)
                        .content(VoteRequest))
                .andExpect(status().isOk())
                .andDo(restDocs.document());


    }
    @DisplayName("Vote Result")
    @Test
    void checkBestRestaurant() throws Exception {
        //given
        long roomId = 1L;
        Room room = TestUtil.CreateTestRoom();
        List<Restaurant> restaurantList = TestUtil.CreateTestRestaurantList(room);
        MostPopularRestaurantRes mostPopularRestaurantRes = TestUtil.CreateTestBestRestaurantList(room,restaurantList);
        given(restaurantRepository.findAllByRoomId(roomId)).willReturn(restaurantList);
        given(roomService.checkBestRestaurant(roomId)).willReturn(mostPopularRestaurantRes);
        //then

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/{roomId}/result",roomId))
                .andExpect(status().isOk())
                .andDo(restDocs.document());
    }
}
