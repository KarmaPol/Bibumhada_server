package com.bibum_server.domain.dto.response;

import com.bibum_server.domain.room.entity.Room;
import lombok.Builder;

public class VoteRes {
    Long id;
    Room room;
    Long count;

    @Builder
    public VoteRes(Long id, Room room, Long count) {
        this.id = id;
        this.room = room;
        this.count = count;
    }
}
