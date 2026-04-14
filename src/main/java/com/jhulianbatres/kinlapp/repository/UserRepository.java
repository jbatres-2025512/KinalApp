package com.jhulianbatres.kinlapp.repository;

import com.jhulianbatres.kinlapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User,Long> {

    boolean existByEmail(String correo);

    User findByEmail(String correo);
}
