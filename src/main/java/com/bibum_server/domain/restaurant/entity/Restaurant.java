package com.bibum_server.domain.restaurant.entity;

import com.bibum_server.domain.room.entity.Room;
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

    String address;

    @Builder.Default
    Boolean isRetried = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id")
    private Room room;

    @Builder
    public Restaurant(Long id,String title, String category, Long count, String link, Long distance,String address,Room room) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.count = count;
        this.link = link;
        this.distance = distance;
        this.address = address;
        this.room = room;
    }

    public void incrementCount(){
        this.count+=1;
    }

}
