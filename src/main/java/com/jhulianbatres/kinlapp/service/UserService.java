package com.jhulianbatres.kinlapp.service;

import com.jhulianbatres.kinlapp.entity.User;
import com.jhulianbatres.kinlapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import java.util.List;
import java.util.Optional;

@Service

@Transactional

public class UserService implements IUserService{

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listAll(){
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        validateUser(user);

        if (user.getUserState() == 0)
            user.setUserState(1);

        return userRepository.save(user);
    }

    @Override
    public Optional<User> findByUserCode(Long userCode) {
        return userRepository.findById(userCode);
    }

    @Override
    public User update(Long userCode, User user) {
        if (!userRepository.existsById(userCode))
            throw new RuntimeException("No se encontro ningun usuario con este codigo: " + userCode);

        user.setUserCode(userCode);

        return userRepository.save(user);
    }

    @Override
    public void delete(Long userCode) {
        if (!userRepository.existsById(userCode))
            throw new RuntimeException("No se encontro ningun usuario con este codigo: " + userCode);

        userRepository.deleteById(userCode);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existByDpi(Long userCode) {
        return userRepository.existsById(userCode);
    }

    private void validateUser(User user){

        if (user.getUserCode()==null || user.getUserCode().toString().trim().isEmpty()){
            throw new IllegalArgumentException("El codigo de usuario no puede estar vacio!");
        }

        if (user.getUserName()==null || user.getUserName().trim().isEmpty()){
            throw new IllegalArgumentException("El nombre es un dato obligatorio!");
        }

        if (user.getUserEmail()==null || user.getUserEmail().trim().isEmpty()){
            throw new IllegalArgumentException("El correo no puede estar vacio!");
        }

        if (user.getUserPassword()==null || user.getUserPassword().trim().isEmpty()){
            throw new IllegalArgumentException("La contraseña es un dato obligatorio!");
        }

        if (user.getUserRol()==null || user.getUserRol().trim().isEmpty()){
            throw new IllegalArgumentException("El roll no puede estar vacio!");
        }

    }







}
