package com.bibum_server.domain.dto.response;

import com.bibum_server.domain.room.entity.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VoteRes {

    private List<RestaurantVote> restaurantVotes;

    @Builder
    public VoteRes(List<RestaurantVote> restaurantVotes) {
        this.restaurantVotes = restaurantVotes;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class RestaurantVote {
        private Long id;
        private String title;
        private String category;
        private Long count;
        private String link;
        private Long distance;
        private String address;
        private Long roomId;
        private Long rank;

        @Builder
        public RestaurantVote(Long id, String title, String category, Long count, String link, Long distance, String address, Long roomId, Long rank) {
            this.id = id;
            this.title = title;
            this.category = category;
            this.count = count;
            this.link = link;
            this.distance = distance;
            this.address = address;
            this.roomId = roomId;
            this.rank = rank;
        }
    }
}
