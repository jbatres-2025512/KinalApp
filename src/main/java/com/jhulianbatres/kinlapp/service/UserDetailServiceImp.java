package com.jhulianbatres.kinlapp.service;

import com.jhulianbatres.kinlapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailServiceImp implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        com.jhulianbatres.kinlapp.entity.User user = userRepository.findByUserName(username);

        if (user == null) {
            throw new UsernameNotFoundException("No se encontró el usuario: " + username);
        }


        String rol = user.getUserRol();

        if (rol == null || rol.trim().isEmpty()) {
            rol = "ROLE_USER";
        }


        if (!rol.startsWith("ROLE_")) {
            rol = "ROLE_" + rol.toUpperCase();
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getUserPassword())
                .authorities(List.of(new SimpleGrantedAuthority(rol)))
                .build();
    }
}