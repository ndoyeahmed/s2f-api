package com.s2f.s2fapi.service.implementation;

import com.s2f.s2fapi.dto.request.CommandeProduitDtoRequest;
import com.s2f.s2fapi.exceptions.EntityNotFoundException;
import com.s2f.s2fapi.model.Client;
import com.s2f.s2fapi.model.Produit;
import com.s2f.s2fapi.repository.ClientRepository;
import com.s2f.s2fapi.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandeValidator {

    private final ClientRepository clientRepository;
    private final ProduitRepository produitRepository;

    public Client validateClientExists(Long clientId) {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with ID: " + clientId));
    }

    public List<Produit> validateProductsExist(List<CommandeProduitDtoRequest> orderedProducts) {
        return orderedProducts.stream()
                .map(productRequest -> produitRepository.findById(productRequest.getProduitId())
                        .orElseThrow(() -> new EntityNotFoundException("Produit not found with ID: " + productRequest.getProduitId())))
                .collect(Collectors.toList());
    }
}

