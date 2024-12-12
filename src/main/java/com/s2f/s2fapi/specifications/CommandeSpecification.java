package com.s2f.s2fapi.specifications;

import com.s2f.s2fapi.model.Commande;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class CommandeSpecification {
    public static Specification<Commande> betweenDate(Date startDate, Date endDate) {
        return (root, query, criteriaBuilder) -> {
            if (startDate == null || endDate == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.between(root.get("date"), startDate, endDate);
        };
    }

    public static Specification<Commande> hasNumero(String numero) {
        return (root, query, criteriaBuilder) -> {
            if (numero == null || numero.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("numero"), "%" + numero + "%");
        };
    }

    public static Specification<Commande> hasEtatCommande(String etatCommande) {
        return (root, query, criteriaBuilder) -> {
            if (etatCommande == null || etatCommande.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(root.get("etatCommande"), "%" + etatCommande + "%");
        };
    }

    public static Specification<Commande> hasClient(Long clientId) {
        return (root, query, criteriaBuilder) -> {
            if (clientId == null) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.equal(root.get("client").get("id"), clientId);
        };
    }

    public static Specification<Commande> isArchiveFalse() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("archive"));
    }
}
