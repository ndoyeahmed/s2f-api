package com.s2f.s2fapi.dto.request;

import com.s2f.s2fapi.model.EtatCommande;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommandeDtoRequest {
    @NotNull
    private Date date;
    @NotBlank
    private String numero;
    private EtatCommande etatCommande = EtatCommande.ENCOURS;
    private double montantRecu;
    @NotNull
    private Long clientId;
    @NotNull
    @NotEmpty
    private List<CommandeProduitDtoRequest> orderedProducts;
}
