package com.jhulianbatres.kinlapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.jhulianbatres.kinlapp.entity.Client;
import com.jhulianbatres.kinlapp.service.ClientService;

@Controller
@RequestMapping("/client")
public class ClientWebController {

    private final ClientService clientService;

    public ClientWebController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public String viewClients(Model model) {
        List<Client> clients = clientService.listAll();
        model.addAttribute("listClients", clients);

        Client newClient = new Client();
        newClient.setState(1);
        model.addAttribute("client", newClient);

        return "client";
    }

    @GetMapping("/search")
    public String searchClient(@RequestParam(value = "dpi", required = false) String dpi, Model model) {
        List<Client> clients;

        if (dpi != null && !dpi.trim().isEmpty()) {
            Optional<Client> found = clientService.findByClientDpi(dpi);
            if (found.isPresent()) {
                clients = List.of(found.get());
            } else {
                clients = List.of();
            }
        } else {
            clients = clientService.listAll();
        }

        model.addAttribute("listClients", clients);

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
        List<Client> clients = clientService.listAll();
        model.addAttribute("listClients", clients);

        Client newClient = new Client();
        newClient.setState(1);
        model.addAttribute("client", newClient);

        Optional<Client> clientOptional = clientService.findByClientDpi(dpi);
        if (clientOptional.isPresent()) {
            model.addAttribute("editClient", clientOptional.get());
        }

        return "client";
    }

    @PostMapping("/edit/{dpi}")
    public String updateClient(@PathVariable("dpi") String dpi, @ModelAttribute("editClient") Client client) {
        boolean exists = clientService.existByDpi(dpi);
        if (exists) {
            clientService.update(dpi, client);
        }
        return "redirect:/client";
    }

    @PostMapping("/delete/{dpi}")
    public String deleteClient(@PathVariable("dpi") String dpi) {
        boolean exists = clientService.existByDpi(dpi);
        if (exists) {
            clientService.delete(dpi);
        }
        return "redirect:/client";
    }
}