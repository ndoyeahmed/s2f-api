package com.s2f.s2fapi.repository;

import com.s2f.s2fapi.model.Categorie;
import com.s2f.s2fapi.model.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    Page<Produit> findAllByArchiveFalse(Pageable pageable);
    Page<Produit> findAllByArchiveFalseAndCategorie(Categorie categorie, Pageable pageable);
    @Query("select p from Produit  p where p.libelle like %:libelle%")
    Page<Produit> findAllByArchiveFalseAndLibelle(@Param("libelle") String libelle, Pageable pageable);
    @Query("select p from Produit  p where p.libelle like %:libelle% and p.categorie = :categorie")
    Page<Produit> findAllByArchiveFalseAndLibelleAndCategorie(@Param("libelle") String libelle, @Param("categorie") Categorie categorie, Pageable pageable);
}
