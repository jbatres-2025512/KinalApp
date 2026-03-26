package com.jhulianbatres.kinlapp.service;

import com.jhulianbatres.kinlapp.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    //Metodo para listar todos los usuarios
    List<User> listAll();

    //Metodo para listar todos los usarios segun su estado
    List<User> findByUserState();

    //Metodo para guardar a los usuarios en la base de datos
    User save(User user);

    //Metodo para buscar por codigo de usuario
    Optional<User> findByUserCode(Long userCode);

    //Metodo para actualizar usuario
    User update(Long userCode,User user);

    //Metodo para eliminar un usuario por su codigo de usuario
    void delete(Long userCode);

    //boolean - Retornara true si existe y false si no exite
    boolean existByUserCode(Long userCode);
}
