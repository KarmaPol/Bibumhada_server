package com.bibum_server.domain.restaurant.repository;

import com.bibum_server.domain.restaurant.entity.Restaurant;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@DataJpaTest
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void saveRestaurantTest(){
        Restaurant restaurant = Restaurant.builder()
                .category("something")
                .count(0L)
                .distance(1L)
                .link("www.test.com")
                .title("test")
                .build();


    }

}