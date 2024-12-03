package com.s2f.s2fapi.model;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Mesure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
    private double valeur;

    @ManyToOne
    @JoinColumn(name = "client", referencedColumnName = "id")
    private Client client;
}
