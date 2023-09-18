package com.bibum_server.domain.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NaverApiResponse {
    // Lombok을 이용한 기본 생성자 자동 추가
    List<NaverApiItemRes> items;

    public NaverApiResponse(List<NaverApiItemRes> items) {
        this.items = items;
    }
}

