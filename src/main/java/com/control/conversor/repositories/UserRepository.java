package com.control.conversor.repositories;

import com.control.conversor.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserRepository extends JpaRepository<User,String> {

    UserDetails findByLogin(String login);

    UserDetails findByLoginAndActive(String login,boolean active);

    @Query(value = "SELECT * FROM users WHERE role <> 0",nativeQuery = true)
    List<User> findFromList();
}
