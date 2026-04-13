package com.jhulianbatres.kinlapp.controller;



import com.jhulianbatres.kinlapp.entity.User;
import com.jhulianbatres.kinlapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class UserWebController {

    private final UserService userService;

    public UserWebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/view/users")
    public String listUsers(Model userModel){

        userModel.addAttribute("User",userService.listAll());

        return "users";

    }

    @GetMapping("/search")
    public String searchUser(@RequestParam Long userCode, Model userModel){

        userModel.addAttribute("User",userService.findByUserCode(userCode));

        return "users";

    }

    @PostMapping("/save")
    public String addUser(@ModelAttribute ("user")User user, Model userModel){

        userModel.addAttribute("User",userService.save(user));

        return "redirect:/view/users";

    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam Long userCode){

       userService.delete(userCode);

        return "redirect:/view/users";

    }




}
