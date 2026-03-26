package com.jhulianbatres.kinlapp.controller;

import com.jhulianbatres.kinlapp.entity.User;
import com.jhulianbatres.kinlapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/actives")
    public ResponseEntity<List<User>> findByUserState(){
        return ResponseEntity.ok(userService.findByUserState());
    }


    @PostMapping
    public ResponseEntity<?> save(@RequestBody User user){

        try {

            User newUser = userService.save(user);

            return new ResponseEntity<>(newUser, HttpStatus.CREATED);


        }catch (IllegalArgumentException e){

            return ResponseEntity.badRequest().body(e.getMessage());

        }


    }

    @DeleteMapping("/{userCode}")
    public ResponseEntity<Void> delete(@PathVariable Long userCode){

        try {

            if (!userService.existByUserCode(userCode)){
                return ResponseEntity.notFound().build();
            }

            userService.delete(userCode);
            return ResponseEntity.noContent().build();

        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }


    }

    @PutMapping("/{userCode}")
    public ResponseEntity<?> update(@PathVariable Long userCode, @RequestBody User user){

        try {

            if (!userService.existByUserCode(userCode)){
                return ResponseEntity.notFound().build();
            }

            User updateUser = userService.update(userCode,user);

            return ResponseEntity.ok(updateUser);


        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }


    }



}
