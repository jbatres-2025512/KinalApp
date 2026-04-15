package com.jhulianbatres.kinlapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientWebController {

    @GetMapping("/client")
    public String client(){
        return ("client");
    }

}
