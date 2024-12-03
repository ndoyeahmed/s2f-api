package com.s2f.s2fapi.dto.response;

import com.s2f.s2fapi.model.EtatCommande;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommandeDtoResponse {
    private Long id;
    private Date date;
    private String numero;
    private EtatCommande etatCommande;
    private ClientDtoResponse client;
    private List<CommandeProduitDtoResponse> orderedProducts;
}
