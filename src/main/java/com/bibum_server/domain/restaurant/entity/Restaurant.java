package com.bibum_server.domain.restaurant.entity;

import com.bibum_server.domain.room.entity.Room;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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
    Boolean isExposed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id")
    private Room room;

    public void incrementCount(){
        this.count+=1;
    }

}
