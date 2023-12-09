package com.bibum_server.domain.exception;

public class RestaurantNotFoundException extends CustomException{
    public RestaurantNotFoundException() {
        super("404", "존재하지 않는 식당 입니다.");
    }
}
