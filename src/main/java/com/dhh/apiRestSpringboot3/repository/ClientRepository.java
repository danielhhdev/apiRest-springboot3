package com.dhh.apiRestSpringboot3.repository;

import com.dhh.apiRestSpringboot3.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository  extends JpaRepository<Client, Long> {
}
