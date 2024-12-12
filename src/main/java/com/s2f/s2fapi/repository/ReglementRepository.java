package com.s2f.s2fapi.repository;

import com.s2f.s2fapi.model.Reglement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReglementRepository extends JpaRepository<Reglement, Long> {
    List<Reglement> findAllByArchiveFalseAndPaiementId(Long paiementId);
}
