package com.s2f.s2fapi.dto.request;

import java.util.List;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ClientDtoRequest {
    private String nom;
    private String prenom;
    private String telephone;
    private boolean archive;
    private List<MesureDtoRequest> mesures;
}
