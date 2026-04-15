package com.jhulianbatres.kinlapp.controller;

import com.jhulianbatres.kinlapp.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClientWebController {

    public final ClientService clientService;

    public ClientWebController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/client")
    public String client(){
        return ("client");
    }

    @GetMapping
    public String clients(Model model){

        model.addAttribute("DPIClient",clientService.listAll());

        return ("client");

    }



}
