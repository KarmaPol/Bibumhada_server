package com.bibum_server.domain.presentation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequiredArgsConstructor
public class TodayMenuController {

    @GetMapping(value = "/")
    public String printHelloWorld(){
        return "Hello world! ";
    }
}
