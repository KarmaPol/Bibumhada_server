package com.bibum_server.domain.dto.request;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class VoteReq {
    List<Long> restaurantIdList;

    @Builder
    public VoteReq( final List<Long> restaurantIdList) {
        this.restaurantIdList = restaurantIdList;
    }
}
