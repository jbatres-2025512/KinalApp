package com.jhulianbatres.kinlapp.controller;

import com.jhulianbatres.kinlapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("{/users}")
public class User {

    private final UserService userService;

    public User(UserService userService) {
        this.userService = userService;
    }

    




}
