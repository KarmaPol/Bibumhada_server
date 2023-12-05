package com.bibum_server.domain.restaurant.repository;

import com.bibum_server.domain.restaurant.entity.Restaurant;
import com.bibum_server.domain.room.entity.Room;

import java.util.List;

public interface RestaurantCustomRepository {

    List<Restaurant> getRestaurantByRoomLimit5(Room room);
}
