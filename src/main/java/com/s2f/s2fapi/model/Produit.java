package com.s2f.s2fapi.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    private String description;
    private double prix;
    @Column(columnDefinition = "boolean default false")
    private boolean archive;

    @ManyToOne
    @JoinColumn(name = "categorie", referencedColumnName = "id")
    private Categorie categorie;
}
