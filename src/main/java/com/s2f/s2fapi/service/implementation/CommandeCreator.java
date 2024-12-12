package com.s2f.s2fapi.service.implementation;

import com.s2f.s2fapi.dto.request.CommandeDtoRequest;
import com.s2f.s2fapi.model.Client;
import com.s2f.s2fapi.model.Commande;
import com.s2f.s2fapi.model.CommandeProduit;
import com.s2f.s2fapi.model.Produit;
import com.s2f.s2fapi.repository.CommandeProduitRepository;
import com.s2f.s2fapi.repository.CommandeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandeCreator {

    private final CommandeRepository commandeRepository;
    private final CommandeProduitRepository commandeProduitRepository;

    public Commande createCommande(CommandeDtoRequest commandeDtoRequest, Client client, List<Produit> produits) {
        var commande = Commande.builder()
                .date(commandeDtoRequest.getDate())
                .numero(commandeDtoRequest.getNumero())
                .etatCommande(commandeDtoRequest.getEtatCommande())
                .client(client)
                .build();
        var savedCommande = commandeRepository.save(commande);

        var commandeProduits = produits.stream()
                .map(produit -> CommandeProduit.builder()
                        .commande(savedCommande)
                        .produit(produit)
                        .quantite(commandeDtoRequest.getOrderedProducts().stream()
                                .filter(p -> p.getProduitId() == produit.getId())
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"))
                                .getQuantite())
                        .prixVente(commandeDtoRequest.getOrderedProducts().stream()
                                .filter(p -> p.getProduitId() == produit.getId())
                                .findFirst()
                                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"))
                                .getPrixVente())
                        .build())
                .collect(Collectors.toList());

        commandeProduitRepository.saveAll(commandeProduits);

        return savedCommande;
    }
}

