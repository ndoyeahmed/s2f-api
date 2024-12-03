package com.s2f.s2fapi.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommandeProduitDtoResponse {
    private Long id;
    private double quantite;
    private double prixVente;
    private ProduitDTO produit;
}
