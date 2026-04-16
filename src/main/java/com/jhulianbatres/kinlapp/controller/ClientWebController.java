package com.jhulianbatres.kinlapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jhulianbatres.kinlapp.entity.Client;
import com.jhulianbatres.kinlapp.service.ClientService;

@Controller
@RequestMapping("/client")
public class ClientWebController {

    public final ClientService clientService;

    public ClientWebController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String clients(Model model) {
        model.addAttribute("listClients", clientService.listAll());
        Client newClient = new Client();
        newClient.setState(1);
        model.addAttribute("client", newClient);
        return "client";
    }
    
    @GetMapping("/search")
    public String searchClient(@RequestParam(value = "dpi", required = false) String dpi, Model model) {
        if (dpi != null && !dpi.trim().isEmpty()) {
            clientService.findByClientDpi(dpi).ifPresentOrElse(
                    found -> model.addAttribute("listClients", List.of(found)),
                    () -> model.addAttribute("listClients", List.of())
            );
        } else {
            model.addAttribute("listClients", clientService.listAll());
        }
        Client newClient = new Client();
        newClient.setState(1);
        model.addAttribute("client", newClient);
        return "client";
    }

    @PostMapping("/add")
    public String addClient(@ModelAttribute("client") Client client) {
        if (client.getState() == null) {
            client.setState(1);
        }
        clientService.save(client);
        return "redirect:/client";
    }
    
    @GetMapping("/edit/search")
    public String searchClientToEdit(@RequestParam("dpi") String dpi, Model model) {
        clientService.findByClientDpi(dpi).ifPresentOrElse(
                found -> {
                    model.addAttribute("editClient", found);
                    model.addAttribute("listClients", clientService.listAll());
                    Client newClient = new Client();
                    newClient.setState(1);
                    model.addAttribute("client", newClient);
                },
                () -> {
                    model.addAttribute("listClients", clientService.listAll());
                    Client newClient = new Client();
                    newClient.setState(1);
                    model.addAttribute("client", newClient);
                }
        );
        return "client";
    }

    @PostMapping("/edit/{dpi}")
    public String updateClient(@PathVariable("dpi") String dpi,
                    @ModelAttribute("editClient") Client client) {
        if (clientService.existByDpi(dpi)) {
            clientService.update(dpi, client);
        }
        return "redirect:/client";
    }

    @PostMapping("/delete/{dpi}")
    public String deleteClient(@PathVariable("dpi") String dpi) {
        if (clientService.existByDpi(dpi)) {
            clientService.delete(dpi);
        }
        return "redirect:/client";
    }
}