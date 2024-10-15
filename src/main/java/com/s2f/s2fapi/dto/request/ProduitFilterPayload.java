package com.s2f.s2fapi.dto.request;

import com.s2f.s2fapi.dto.response.CategorieDto;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ProduitFilterPayload {
    private String libelle;
    private CategorieDto categorieDto;
    private int page;
    private int size;
}
