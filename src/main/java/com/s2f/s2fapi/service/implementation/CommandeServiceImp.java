package com.s2f.s2fapi.service.implementation;

import com.s2f.s2fapi.dto.request.CommandeDtoRequest;
import com.s2f.s2fapi.dto.response.CommandeDtoResponse;
import com.s2f.s2fapi.dto.response.ResponseDTOPaging;
import com.s2f.s2fapi.mapper.CommandeResponseMapper;
import com.s2f.s2fapi.model.Commande;
import com.s2f.s2fapi.model.Paiement;
import com.s2f.s2fapi.model.Reglement;
import com.s2f.s2fapi.repository.CommandeRepository;
import com.s2f.s2fapi.repository.PaiementRepository;
import com.s2f.s2fapi.repository.ReglementRepository;
import com.s2f.s2fapi.service.interfaces.CommandeService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;

import com.s2f.s2fapi.specifications.CommandeSpecification;
import com.s2f.s2fapi.utils.LoggingUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommandeServiceImp implements CommandeService {
    private final CommandeRepository commandeRepository;
    private final PaiementRepository paiementRepository;
    private final ReglementRepository reglementRepository;
    private final CommandeValidator commandeValidator;
    private final CommandeCreator commandeCreator;
    private final CommandeResponseMapper responseMapper;

    @Override
    public long countAllCommande() {
        return commandeRepository.count();
    }

    @Override
    public String getNextCommandeNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String datePart = dateFormat.format(new Date());
        long numeroSequentiel = countAllCommande() + 1;
        // Construction du numéro de commande final
        return "CMD-" + datePart + "-" + String.format("%04d", numeroSequentiel);
    }

    @Override
    @Transactional
    public CommandeDtoResponse addNewCommande(CommandeDtoRequest commandeDtoRequest) {
        // Valider la commande
        LoggingUtil.logInfo(this.getClass(), "addNewCommande", "begin : find client by ID : " + commandeDtoRequest.getClientId());
        var client = commandeValidator.validateClientExists(commandeDtoRequest.getClientId());
        LoggingUtil.logInfo(this.getClass(), "addNewCommande", "begin validation products ");
        var produits = commandeValidator.validateProductsExist(commandeDtoRequest.getOrderedProducts());

        double montantTotal = produits.stream()
                .mapToDouble(produit -> {
                    var produitRequest = commandeDtoRequest.getOrderedProducts().stream()
                            .filter(p -> p.getProduitId() == produit.getId())
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("Produit non trouvé dans la commande."));
                    return produitRequest.getPrixVente() * produitRequest.getQuantite();
                }).sum();

        // Étape 3 : Créer et enregistrer la commande
        var savedCommande = commandeCreator.createCommande(commandeDtoRequest, client, produits);

        // Étape 4 : Créer le paiement associé à la commande
        Paiement paiement = Paiement.builder()
                .commande(savedCommande)
                .montantAPayer(montantTotal)
                .montantRecu(commandeDtoRequest.getMontantRecu())
                .montantRestant(montantTotal - commandeDtoRequest.getMontantRecu())
                .archive(false)
                .build();
        var savedPaiement = paiementRepository.save(paiement);

        // Étape 5 : Enregistrer le règlement initial
        Reglement reglement = Reglement.builder()
                .paiement(savedPaiement)
                .date(new Date())
                .montant(commandeDtoRequest.getMontantRecu())
                .archive(false)
                .build();
        reglementRepository.save(reglement);

        // Étape 6 : Générer la réponse
        return responseMapper.toCommandeDtoResponse(savedCommande);
    }

    @Override
    public ResponseDTOPaging<CommandeDtoResponse> filterCommande(String numero, String etatCommande, Long clientId, Date startDate, Date endDate, Pageable pageable) {
        LoggingUtil.logInfo(
                this.getClass(),
                "filterCommande",
                "get list of orders with pagination and filter");

        Specification<Commande> commandeSpecification = Specification.where(CommandeSpecification.isArchiveFalse())
                .or(CommandeSpecification.hasEtatCommande(etatCommande))
                .or(CommandeSpecification.hasClient(clientId))
                .or(CommandeSpecification.hasNumero(numero))
                .or(CommandeSpecification.betweenDate(startDate, endDate));
        var commandePage = commandeRepository.findAll(commandeSpecification, pageable);
        return new ResponseDTOPaging<CommandeDtoResponse>(
                commandePage.getContent().stream()
                        .map(responseMapper::toCommandeDtoResponse)
                        .collect(Collectors.toList()),
                commandePage.getNumber(),
                commandePage.getTotalElements(),
                commandePage.getTotalPages());
    }

    @Override
    public ResponseDTOPaging<CommandeDtoResponse> getAllCommandeByEtatCommande(String etatCommande, Pageable pageable) {
        Specification<Commande> commandeSpecification = Specification.where(CommandeSpecification.isArchiveFalse())
                .and(CommandeSpecification.hasEtatCommande(etatCommande.toUpperCase()));
        var commandePage = commandeRepository.findAll(commandeSpecification, pageable);
        return new ResponseDTOPaging<CommandeDtoResponse>(
                commandePage.getContent().stream()
                        .map(responseMapper::toCommandeDtoResponse)
                        .collect(Collectors.toList()),
                commandePage.getNumber(),
                commandePage.getTotalElements(),
                commandePage.getTotalPages());
    }
}
