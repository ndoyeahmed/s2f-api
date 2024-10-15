package com.s2f.s2fapi.service.administration.impl;

import com.s2f.s2fapi.dto.request.ProduitFilterPayload;
import com.s2f.s2fapi.dto.response.CategorieDto;
import com.s2f.s2fapi.dto.response.ProduitDTO;
import com.s2f.s2fapi.dto.response.ResponseDTOPaging;
import com.s2f.s2fapi.exceptions.EntityNotFoundException;
import com.s2f.s2fapi.exceptions.InternalServerErrorException;
import com.s2f.s2fapi.model.Categorie;
import com.s2f.s2fapi.model.Produit;
import com.s2f.s2fapi.repository.CategorieRepository;
import com.s2f.s2fapi.repository.ProduitRepository;
import com.s2f.s2fapi.service.administration.interfaces.ProduitService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProduitServiceImp implements ProduitService {
    private final CategorieRepository categorieRepository;
    private final ProduitRepository produitRepository;

    @Override
    public List<CategorieDto> getAllCategories() {
        return categorieRepository.findAllByArchiveFalse()
                .stream()
                .map(c -> CategorieDto.builder().id(c.getId()).libelle(c.getLibelle()).build())
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CategorieDto addCategorie(CategorieDto categorieDto) {
        try {
            log.info("[{}][{}] begin add new category", this.getClass(), "addCategorie");
            var categorie = categorieRepository.save(
                    Categorie.builder()
                            .archive(categorieDto.isArchive())
                            .id(categorieDto.getId())
                            .libelle(categorieDto.getLibelle())
                            .build()
            );
            log.info("[{}][{}] category added successfully", this.getClass(), "addCategorie");
            return CategorieDto.builder()
                    .archive(categorie.isArchive())
                    .id(categorie.getId())
                    .libelle(categorie.getLibelle())
                    .build();
        } catch (Exception ex) {
            log.error("[{}][{}] error occur on add category", this.getClass(), "addCategorie");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @Override
    public ResponseDTOPaging<ProduitDTO> getAllProduitByFilters(ProduitFilterPayload produitFilterPayload) {
        Pageable paging = PageRequest.of(produitFilterPayload.getPage(), produitFilterPayload.getSize());
        if (produitFilterPayload.getCategorieDto() != null && produitFilterPayload.getCategorieDto().getId() != null && produitFilterPayload.getLibelle() != null && !produitFilterPayload.getLibelle().equals("")) {
            return getAllProduitByCategorieAndLibelle(produitFilterPayload.getLibelle(),
                    produitFilterPayload.getCategorieDto().toCategorie(), paging);
        } else if (produitFilterPayload.getCategorieDto() != null && produitFilterPayload.getCategorieDto().getId() != null) {
           return getAllProduitByCategorie(produitFilterPayload.getCategorieDto().toCategorie(), paging);
        } else if (produitFilterPayload.getLibelle() != null && !produitFilterPayload.getLibelle().equals("")) {
            return getAllProduitByLibelle(produitFilterPayload.getLibelle(), paging);
        } else {
            return getAllProduit(paging);
        }
    }

    @Override
    public ResponseDTOPaging<ProduitDTO> getAllProduit(Pageable pageable) {
        log.info("[{}][{}] get list of products with pagination", this.getClass(), "getAllProduit");
        var productsPage = produitRepository.findAllByArchiveFalse(pageable);
        return new ResponseDTOPaging<ProduitDTO>(
                productsPage.getContent()
                        .stream()
                        .map(ProduitDTO::toProduitDTO).collect(Collectors.toList()),
                productsPage.getNumber(),
                productsPage.getTotalElements(),
                productsPage.getTotalPages()
        );
    }

    @Override
    public ResponseDTOPaging<ProduitDTO> getAllProduitByLibelle(String libelle, Pageable pageable) {
        log.info("[{}][{}] get list of products with pagination and filter by libelle", this.getClass(), "getAllProduitByLibelle");
        var productsPage = produitRepository.findAllByArchiveFalseAndLibelle(libelle, pageable);
        return new ResponseDTOPaging<ProduitDTO>(
                productsPage.getContent()
                        .stream()
                        .map(ProduitDTO::toProduitDTO).collect(Collectors.toList()),
                productsPage.getNumber(),
                productsPage.getTotalElements(),
                productsPage.getTotalPages()
        );
    }

    @Override
    public ResponseDTOPaging<ProduitDTO> getAllProduitByCategorie(Categorie categorie, Pageable pageable) {
        log.info("[{}][{}] get list of products with pagination and filter by category", this.getClass(), "getAllProduitByCategorie");
        var productsPage = produitRepository.findAllByArchiveFalseAndCategorie(categorie, pageable);
        return new ResponseDTOPaging<ProduitDTO>(
                productsPage.getContent()
                        .stream()
                        .map(ProduitDTO::toProduitDTO).collect(Collectors.toList()),
                productsPage.getNumber(),
                productsPage.getTotalElements(),
                productsPage.getTotalPages()
        );
    }

    @Override
    public ResponseDTOPaging<ProduitDTO> getAllProduitByCategorieAndLibelle(String libelle, Categorie categorie, Pageable pageable) {
        log.info("[{}][{}] get list of products with pagination and filter by libelle and category", this.getClass(), "getAllProduitByCategorieAndLibelle");
        var productsPage = produitRepository.findAllByArchiveFalseAndLibelleAndCategorie(libelle, categorie, pageable);
        return new ResponseDTOPaging<ProduitDTO>(
                productsPage.getContent()
                        .stream()
                        .map(ProduitDTO::toProduitDTO).collect(Collectors.toList()),
                productsPage.getNumber(),
                productsPage.getTotalElements(),
                productsPage.getTotalPages()
        );
    }

    @Override
    @Transactional
    public ProduitDTO addProduct(ProduitDTO produitDTO) {
        try {
            log.info("[{}][{}] begin add new product", this.getClass(), "addProduct");
            var product = produitRepository.save(produitDTO.toProduit());
            log.info("[{}][{}] product added successfully", this.getClass(), "addProduct");
            return ProduitDTO.toProduitDTO(product);
        } catch (Exception ex) {
            log.error("[{}][{}] error occur on add product", this.getClass(), "addProduct");
            throw new InternalServerErrorException(ex.getMessage());
        }
    }

    @Override
    public Produit getProductById(Long id) {
        return produitRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("product not found"));
    }
}
