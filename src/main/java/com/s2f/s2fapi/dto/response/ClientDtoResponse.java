package com.s2f.s2fapi.dto.response;

import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClientDtoResponse {
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private boolean archive;
    private List<MesureDtoResponse> mesures;
}
