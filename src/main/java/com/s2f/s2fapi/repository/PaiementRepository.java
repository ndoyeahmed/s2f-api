package com.s2f.s2fapi.repository;

import com.s2f.s2fapi.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaiementRepository extends JpaRepository<Paiement, Long> {}
