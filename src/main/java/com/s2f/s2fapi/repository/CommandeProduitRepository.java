package com.s2f.s2fapi.repository;

import com.s2f.s2fapi.model.CommandeProduit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeProduitRepository extends JpaRepository<CommandeProduit, Long> {

    List<CommandeProduit> findAllByArchiveFalseAndAndCommandeId(Long commandeId);
}
