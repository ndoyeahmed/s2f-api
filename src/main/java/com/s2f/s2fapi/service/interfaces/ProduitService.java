package com.s2f.s2fapi.service.interfaces;

import com.s2f.s2fapi.dto.response.CategorieDto;
import com.s2f.s2fapi.dto.response.ProduitDTO;
import com.s2f.s2fapi.dto.response.ResponseDTOPaging;
import com.s2f.s2fapi.model.Produit;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ProduitService {
    List<CategorieDto> getAllCategories();

    CategorieDto addCategorie(CategorieDto categorieDto);

    ResponseDTOPaging<ProduitDTO> filterProduits(
            String libelle, Long categorieId, Pageable pageable);

    List<ProduitDTO> getAllProduits();

    ProduitDTO addProduct(ProduitDTO produitDTO);

    Produit getProductById(Long id);

    ProduitDTO archiveProduct(Long id);
}
