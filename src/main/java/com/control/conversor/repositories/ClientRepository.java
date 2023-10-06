package com.control.conversor.repositories;

import com.control.conversor.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client,String> {
    Optional<Client> findByKey(String key);
}
