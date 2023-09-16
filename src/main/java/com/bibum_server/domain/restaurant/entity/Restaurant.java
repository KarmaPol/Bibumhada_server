package com.bibum_server.domain.restaurant.entity;

import com.bibum_server.domain.room.entity.Room;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id")
    private Room room;


    @Builder
    public Restaurant(Long id,String title, String category, Long count, String link, Long distance,Room room) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.count = count;
        this.link = link;
        this.distance = distance;
        this.room = room;
    }

    public void incrementCount(){
        this.count+=1;
    }

}
