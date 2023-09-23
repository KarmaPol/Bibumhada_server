package com.bibum_server.domain.restaurant.repository;

import com.bibum_server.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {

    List<Restaurant> findAllByRoomId(Long id);
    List<Restaurant> findByRoomIdAndIdIn(Long roomId, List<Long> restaurantIds);
    Restaurant findByRoomIdAndId(Long roomId, Long restaurantId);

    void deleteAllByRoomId(Long roomId);
}
