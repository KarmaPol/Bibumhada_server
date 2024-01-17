package com.bibum_server.domain.room.entity;

import com.bibum_server.domain.exception.ResuggestUnavailableException;
import com.bibum_server.domain.restaurant.entity.Restaurant;
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

    private String x;

    private String y;

    private Long total;

    private Long page;

    private Boolean isEnd = false;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private List<Restaurant> restaurantList = new ArrayList<>();

    public void addRestaurants(List<Restaurant> restaurants) {
        restaurants.stream().map(restaurant -> restaurantList.add(restaurant)).collect(Collectors.toList());
    }

    public void deleteAllRestaurants(){
        this.restaurantList.clear();
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurantList.add(restaurant);
    }

    @Builder
    public Room(Long id, String x, String y, Long total, Long page ) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.total = total;
        this.page = page;
    }
    public void incrementTotal(){
        this.total+=1;
    }
    public void updateTotal(Long value){
        this.total = value;
    }
    public void setNextPage(){
        this.page+=1;
    }

    public void setIsEndTrue(){
        this.isEnd = true;
    }

    public int getExposedRoomNumber(){
        return (int) this.restaurantList.stream().filter(Restaurant::getIsExposed).count();
    }

    public boolean isResuggestAllAvailable(){
        return getExposedRoomNumber() < 10;
    }

    public boolean isResuggestOneAvailable(){
        return getExposedRoomNumber() < 6;
    }
}


