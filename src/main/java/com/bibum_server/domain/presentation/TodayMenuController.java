package com.bibum_server.domain.presentation;

import com.bibum_server.domain.application.RoomService;
import com.bibum_server.domain.dto.request.LocationReq;
import com.bibum_server.domain.dto.response.LocationRes;
import com.bibum_server.domain.util.WebClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class TodayMenuController {

    private final WebClientUtil webClientUtil;

    @GetMapping("/location")
    public Mono<String> getLocation(@RequestBody LocationReq locationReq){
        return webClientUtil.locationSearch(locationReq);
    }

}
