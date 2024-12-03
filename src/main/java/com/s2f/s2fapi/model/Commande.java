package com.s2f.s2fapi.model;

import jakarta.persistence.*;
import java.util.Date;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private String numero;

    @Column(columnDefinition = "boolean default false")
    private boolean archive;

    @Enumerated(EnumType.STRING)
    private EtatCommande etatCommande;

    @ManyToOne
    @JoinColumn(name = "client", referencedColumnName = "id")
    private Client client;
}
