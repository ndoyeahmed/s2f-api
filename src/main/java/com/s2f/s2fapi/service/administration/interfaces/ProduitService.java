package com.s2f.s2fapi.service.administration.interfaces;

import com.s2f.s2fapi.dto.response.CategorieDto;

import java.util.List;

public interface ProduitService {
    List<CategorieDto> getAllCategories();
    CategorieDto addCategorie(CategorieDto categorieDto);
}
