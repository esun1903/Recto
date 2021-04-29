package com.ssafy.recto.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/test/")
@RestController
public class UserController {

    @GetMapping("/info")
    public void getInfo() {
        System.out.println("완료");
        return ;
    }

}

