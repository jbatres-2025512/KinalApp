package com.jhulianbatres.kinlapp.controller;

import com.jhulianbatres.kinlapp.entity.User;
import com.jhulianbatres.kinlapp.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("{/users}")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>>list(){
        List<User> users = userService.listAll();

        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userCode}")
    public ResponseEntity<User> searchByUserCode(@PathVariable Long userCode){

        return userService.findByUserCode(userCode)

                .map(ResponseEntity::ok)

                .orElse(ResponseEntity.notFound().build());
                    
    }





}
