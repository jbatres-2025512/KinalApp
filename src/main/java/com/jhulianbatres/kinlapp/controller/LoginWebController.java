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

    @PostMapping("/login")
    public String processLogin(
            @RequestParam("username") String username,
            @RequestParam("userpassword") String userpassword,
            HttpSession session,
            Model model) {

        User user = userService.login(username, userpassword);

        if (user == null) {
            model.addAttribute("error", "Usuario o contraseña incorrectos.");
            return "login";
        }


        if (user.getUserState() == 0) {
            model.addAttribute("error", "Tu cuenta está desactivada.");
            return "login";
        }

        session.setAttribute("usuarioLogueado", user);
        return "redirect:/";
    }

}
