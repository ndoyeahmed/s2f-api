package com.s2f.s2fapi.specifications;

import com.s2f.s2fapi.model.Client;
import com.s2f.s2fapi.model.Produit;
import org.springframework.data.jpa.domain.Specification;

public class ClientSpecification {
    public static Specification<Client> hasNom(String nom) {
        return (root, query, criteriaBuilder) -> {
            if (nom == null || nom.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("nom"), "%" + nom + "%");
        };
    }

    public static Specification<Client> hasTelephone(String telephone) {
        return (root, query, criteriaBuilder) -> {
            if (telephone == null || telephone.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("nom"), "%" + telephone + "%");
        };
    }

    public static Specification<Client> isArchiveFalse() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("archive"));
    }
}
