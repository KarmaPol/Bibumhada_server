package com.bibum_server.domain.dto.request;

import lombok.Setter;

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
}
