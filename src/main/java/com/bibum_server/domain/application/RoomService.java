package com.bibum_server.domain.application;

import com.bibum_server.domain.dto.request.LocationReq;
import com.bibum_server.domain.room.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    /*public Optional<LocationReq> convertLocation(LocationReq locationReq){

    }*/
}
