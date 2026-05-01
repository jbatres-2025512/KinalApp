package com.jhulianbatres.kinlapp.controller;


import com.jhulianbatres.kinlapp.entity.User;
import com.jhulianbatres.kinlapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class LoginWebController {

    private final UserService userService;

    public LoginWebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }


}
