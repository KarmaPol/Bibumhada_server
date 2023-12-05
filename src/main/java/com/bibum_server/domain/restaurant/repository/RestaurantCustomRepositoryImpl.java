package com.bibum_server.domain.restaurant.repository;

import com.bibum_server.domain.restaurant.entity.QRestaurant;
import com.bibum_server.domain.restaurant.entity.Restaurant;
import com.bibum_server.domain.room.entity.Room;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class RestaurantCustomRepositoryImpl implements RestaurantCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public List<Restaurant> getRestaurantByRoomLimit5(Room room) {

        QRestaurant restaurant = QRestaurant.restaurant;

        return jpaQueryFactory
                .selectFrom(restaurant)
                .where(restaurant.isExposed.eq(false))
                .limit(5)
                .fetch();
    }
}
