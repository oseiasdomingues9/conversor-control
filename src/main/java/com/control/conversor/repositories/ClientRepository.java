package com.control.conversor.repositories;

import com.control.conversor.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,String> {
    Client findByKey(String key);
}
