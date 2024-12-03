package com.s2f.s2fapi.repository;

import com.s2f.s2fapi.model.Client;
import com.s2f.s2fapi.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long>, JpaSpecificationExecutor<Client> {
    List<Client> findAllByArchiveFalse();
}
