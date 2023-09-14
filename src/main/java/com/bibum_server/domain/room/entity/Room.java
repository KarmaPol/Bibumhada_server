package com.bibum_server.domain.room.entity;

import com.bibum_server.domain.restaurant.entity.Restaurant;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private String title;

    private String x;

    private String y;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private List<Restaurant> restaurantList = new ArrayList<>();

    public void addRestaurant(List<Restaurant> restaurants) {
        restaurants.stream().map(restaurant -> restaurantList.add(restaurant)).collect(Collectors.toList());
    }

    @Builder
    public Room(String title, String x, String y) {
        this.title = title;
        this.x = x;
        this.y = y;
    }
}


