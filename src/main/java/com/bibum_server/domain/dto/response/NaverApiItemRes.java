package com.bibum_server.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor  // Lombok을 이용한 기본 생성자 자동 추가
public class NaverApiItemRes {
    String title;
    String link;

    public NaverApiItemRes(String title, String link) {
        this.title = title;
        this.link = link;
    }
}




