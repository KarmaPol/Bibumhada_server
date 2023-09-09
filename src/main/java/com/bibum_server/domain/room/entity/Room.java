package com.bibum_server.domain.room.entity;

import com.bibum_server.domain.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String url;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private List<Restaurant> restaurantList;

    @Builder
    public Room(String title, String url) {
        this.title = title;
        this.url = url;
    }
}

