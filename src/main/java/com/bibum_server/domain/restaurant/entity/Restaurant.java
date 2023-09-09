package com.bibum_server.domain.restaurant.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String title;

    String category;

    Long count;

    String link;

    Long distance;


    @Builder
    public Restaurant(String title, String category, Long count, String link, Long distance) {
        this.title = title;
        this.category = category;
        this.count = count;
        this.link = link;
        this.distance = distance;
    }
}
