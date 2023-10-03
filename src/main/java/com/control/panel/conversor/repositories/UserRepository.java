package com.control.panel.conversor.repositories;

import com.control.panel.conversor.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User,String> {

    UserDetails findByLogin(String login);
}
