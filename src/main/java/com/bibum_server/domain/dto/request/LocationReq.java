package com.bibum_server.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationReq {
    private String longitude;
    private String latitude;


    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    @Builder
    public LocationReq(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }


}

