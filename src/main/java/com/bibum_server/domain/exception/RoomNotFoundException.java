package com.bibum_server.domain.exception;

public class RoomNotFoundException extends CustomException{
    public RoomNotFoundException() {
        super("404", "존재하지 않는 room 입니다.");
    }
}
