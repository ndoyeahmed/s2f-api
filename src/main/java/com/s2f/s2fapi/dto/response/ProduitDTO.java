package com.s2f.s2fapi.dto.response;

import com.s2f.s2fapi.model.Produit;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProduitDTO {
    private Long id;
    private String libelle;
    private String description;
    private double prix;
    private boolean archive;
    private CategorieDto categorieDto;

    public Produit toProduit() {
        return Produit.builder()
                .id(getId())
                .libelle(getLibelle())
                .description(getDescription())
                .prix(getPrix())
                .archive(isArchive())
                .categorie(getCategorieDto().toCategorie())
                .build();
    }

    public static ProduitDTO toProduitDTO(Produit produit) {
        return ProduitDTO.builder()
                .id(produit.getId())
                .libelle(produit.getLibelle())
                .description(produit.getDescription())
                .prix(produit.getPrix())
                .archive(produit.isArchive())
                .categorieDto(CategorieDto.toCategorie(produit.getCategorie()))
                .build();
    }
}
