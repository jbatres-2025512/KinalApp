package com.jhulianbatres.kinlapp.controller;

import com.jhulianbatres.kinlapp.entity.Client;
import com.jhulianbatres.kinlapp.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/client")
public class ClientWebController {

    public final ClientService clientService;

    public ClientWebController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String clients(Model model){

        model.addAttribute("listClients",clientService.listAll());

        return ("client");

    }

    @PostMapping("/add")
    public String addClient(@ModelAttribute("client") Client client){

        clientService.save(client);

        return "redirect:/client";

    }

    



}
