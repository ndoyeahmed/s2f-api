package com.s2f.s2fapi.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MesureDtoRequest {
    private String libelle;
    private double valeur;
}
