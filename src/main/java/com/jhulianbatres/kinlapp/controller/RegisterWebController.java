package com.jhulianbatres.kinlapp.controller;


import com.jhulianbatres.kinlapp.entity.User;
import com.jhulianbatres.kinlapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterWebController {

    private final UserService userService;

    public RegisterWebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model){

        model.addAttribute("newUser",new User());

        return ("register");
    }

    @PostMapping("/register")
    public String processRegister(
            @ModelAttribute("newUser") User newUser,
            Model model) {


        if (userService.existByUserCode(newUser.getUserCode())) {
            model.addAttribute("error", "Ya existe una cuenta con ese correo.");
            return "register";
        }
        userService.register(newUser);

        return "redirect:/login?registered";
    }


}
