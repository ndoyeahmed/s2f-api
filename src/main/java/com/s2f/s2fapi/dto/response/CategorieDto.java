package com.s2f.s2fapi.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CategorieDto {
    private Long id;

    @NotBlank(message = "Le libellé ne doit pas être vide")
    @Size(min = 2, max = 50, message = "Le libellé doit contenir entre 2 et 50 caractères")
    private String libelle;

    private boolean archive;
}
