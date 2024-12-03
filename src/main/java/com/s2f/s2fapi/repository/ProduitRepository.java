package com.s2f.s2fapi.repository;

import com.s2f.s2fapi.model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProduitRepository
        extends JpaRepository<Produit, Long>, JpaSpecificationExecutor<Produit> {}
