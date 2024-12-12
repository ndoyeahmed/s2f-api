package com.s2f.s2fapi.service.interfaces;

import com.s2f.s2fapi.model.Reglement;

import java.util.List;

public interface PaiementService {
    Reglement addReglement(Long paiementId, double montant);
    List<Reglement> getReglementsByPaiement(Long paiementId);
}
