package com.s2f.s2fapi.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class CommandeProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double quantite;
    private double prixVente;

    @Column(columnDefinition = "boolean default false")
    private boolean archive;

    @ManyToOne
    @JoinColumn(name = "produit", referencedColumnName = "id")
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "commande", referencedColumnName = "id")
    private Commande commande;
}
