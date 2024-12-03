package com.s2f.s2fapi.repository;

import com.s2f.s2fapi.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findAllByArchiveFalse();
}
