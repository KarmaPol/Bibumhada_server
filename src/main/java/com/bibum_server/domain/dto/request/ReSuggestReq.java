package com.bibum_server.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ReSuggestReq {

    String longitude;
    String latitude;
    Long page;

    @Builder
    public ReSuggestReq(String longitude, String latitude, long page) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.page = page;
    }
}
