package com.s2f.s2fapi.repository;

import com.s2f.s2fapi.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategorieRepository extends JpaRepository<Categorie, Long> {
    List<Categorie> findAllByArchiveFalse();
}
