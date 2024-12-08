package com.s2f.s2fapi.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MesureDtoResponse {
    private Long id;
    private String libelle;
    private double valeur;
    private ClientDtoResponse clientDtoResponse;
}
