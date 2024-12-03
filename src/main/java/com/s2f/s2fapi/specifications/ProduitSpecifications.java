package com.s2f.s2fapi.specifications;

import com.s2f.s2fapi.model.Produit;
import org.springframework.data.jpa.domain.Specification;

public class ProduitSpecifications {

    public static Specification<Produit> hasLibelle(String libelle) {
        return (root, query, criteriaBuilder) -> {
            if (libelle == null || libelle.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("libelle"), "%" + libelle + "%");
        };
    }

    public static Specification<Produit> hasCategorie(Long categorieId) {
        return (root, query, criteriaBuilder) -> {
            if (categorieId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("categorie").get("id"), categorieId);
        };
    }

    public static Specification<Produit> isArchiveFalse() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("archive"));
    }
}
