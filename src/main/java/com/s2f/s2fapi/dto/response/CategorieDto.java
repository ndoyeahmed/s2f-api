package com.s2f.s2fapi.dto.response;

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
}
