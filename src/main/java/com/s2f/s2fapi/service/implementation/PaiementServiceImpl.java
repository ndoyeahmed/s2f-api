package com.s2f.s2fapi.service.implementation;

import com.s2f.s2fapi.exceptions.BadRequestException;
import com.s2f.s2fapi.exceptions.EntityNotFoundException;
import com.s2f.s2fapi.model.Paiement;
import com.s2f.s2fapi.model.Reglement;
import com.s2f.s2fapi.repository.PaiementRepository;
import com.s2f.s2fapi.repository.ReglementRepository;
import com.s2f.s2fapi.service.interfaces.PaiementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PaiementServiceImpl implements PaiementService {
    private final PaiementRepository paiementRepository;
    private final ReglementRepository reglementRepository;

    @Override
    @Transactional
    public Reglement addReglement(Long paiementId, double montant) {
        Paiement paiement = paiementRepository.findById(paiementId)
                .orElseThrow(() -> new EntityNotFoundException("Paiement introuvable avec l'ID : " + paiementId));

        if (paiement.getMontantRestant() < montant) {
            throw new BadRequestException("Le montant du règlement dépasse le montant restant.");
        }

        // Mettre à jour les montants du paiement
        paiement.setMontantRecu(paiement.getMontantRecu() + montant);
        paiement.setMontantRestant(paiement.getMontantRestant() - montant);
        paiementRepository.save(paiement);

        // Ajouter un nouveau règlement
        Reglement reglement = Reglement.builder()
                .paiement(paiement)
                .date(new Date())
                .montant(montant)
                .archive(false)
                .build();
        return reglementRepository.save(reglement);
    }

    @Override
    public List<Reglement> getReglementsByPaiement(Long paiementId) {
        return reglementRepository.findAllByArchiveFalseAndPaiementId(paiementId);
    }
}
