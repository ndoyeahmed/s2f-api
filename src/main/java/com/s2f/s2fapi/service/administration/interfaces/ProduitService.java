package com.s2f.s2fapi.service.administration.interfaces;

import com.s2f.s2fapi.dto.request.ProduitFilterPayload;
import com.s2f.s2fapi.dto.response.CategorieDto;
import com.s2f.s2fapi.dto.response.ProduitDTO;
import com.s2f.s2fapi.dto.response.ResponseDTOPaging;
import com.s2f.s2fapi.model.Categorie;
import com.s2f.s2fapi.model.Produit;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProduitService {
    List<CategorieDto> getAllCategories();
    CategorieDto addCategorie(CategorieDto categorieDto);
    ResponseDTOPaging<ProduitDTO> getAllProduitByFilters(ProduitFilterPayload produitFilterPayload);
    ResponseDTOPaging<ProduitDTO> getAllProduit(Pageable pageable);
    ResponseDTOPaging<ProduitDTO> getAllProduitByLibelle(String libelle, Pageable pageable);
    ResponseDTOPaging<ProduitDTO> getAllProduitByCategorie(Categorie categorie, Pageable pageable);
    ResponseDTOPaging<ProduitDTO> getAllProduitByCategorieAndLibelle(String libelle, Categorie categorie, Pageable pageable);
    ProduitDTO addProduct(ProduitDTO produitDTO);
    Produit getProductById(Long id);
}
