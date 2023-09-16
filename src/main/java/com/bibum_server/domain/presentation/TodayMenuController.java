package com.bibum_server.domain.presentation;

import com.bibum_server.domain.application.RoomService;
import com.bibum_server.domain.dto.response.RoomDto;
import com.bibum_server.domain.dto.request.LocationReq;
import com.bibum_server.domain.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TodayMenuController {

    private final RoomService roomService;

    @PostMapping("/create")
    public RoomDto CreateRoom(@RequestBody LocationReq locationReq){
        return roomService.createRoom(locationReq);
    }

}
