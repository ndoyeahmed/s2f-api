package com.s2f.s2fapi.dto.response;

import com.s2f.s2fapi.model.Categorie;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategorieDto {
    private Long id;
    private String libelle;
    private boolean archive;

    public Categorie toCategorie() {
        return Categorie.builder()
                .libelle(getLibelle())
                .id(getId())
                .archive(isArchive())
                .build();
    }

    public static CategorieDto toCategorie(Categorie categorie) {
        return CategorieDto.builder()
                .libelle(categorie.getLibelle())
                .id(categorie.getId())
                .archive(categorie.isArchive())
                .build();
    }
}
