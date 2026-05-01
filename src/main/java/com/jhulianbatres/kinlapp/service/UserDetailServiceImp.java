package com.jhulianbatres.kinlapp.service;


import com.jhulianbatres.kinlapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImp implements UserDetailsService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        com.jhulianbatres.kinlapp.entity.User user = userRepository.findByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("No se encontro el usuario: " + username);
        }

        return org.springframework.security.core.userdetails.User.withUsername(user.getUserName())
                .password(user.getUserPassword())
                .roles(user.getUserRol())
                .build();
    }




}
