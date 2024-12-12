package com.s2f.s2fapi.controller;

import com.s2f.s2fapi.dto.response.CategorieDto;
import com.s2f.s2fapi.dto.response.ProduitDTO;
import com.s2f.s2fapi.dto.response.ResponseDTOPaging;
import com.s2f.s2fapi.service.interfaces.ProduitService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProduitController {
    private final ProduitService produitService;

    @PostMapping("/v1/category")
    public ResponseEntity<CategorieDto> addCategorie(
            @RequestBody @Valid CategorieDto categorieDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(produitService.addCategorie(categorieDto));
    }

    @GetMapping("/v1/categories")
    public ResponseEntity<List<CategorieDto>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(produitService.getAllCategories());
    }

    @GetMapping("/v1/products/filter")
    public ResponseEntity<ResponseDTOPaging<ProduitDTO>> getAllProductByFilters(
            @RequestParam(required = false) String libelle,
            @RequestParam(required = false) Long categorieId,
            Pageable pageable) {
        return ResponseEntity.ok(produitService.filterProduits(libelle, categorieId, pageable));
    }

    @GetMapping("/v1/products")
    public ResponseEntity<List<ProduitDTO>> getAllProducts() {
        return ResponseEntity.ok(produitService.getAllProduits());
    }

    @PostMapping("/v1/product")
    public ResponseEntity<ProduitDTO> addProduit(@RequestBody @Valid ProduitDTO produitDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(produitService.addProduct(produitDTO));
    }

    @DeleteMapping("/v1/product/{id}")
    public ResponseEntity<ProduitDTO> archiveProduct(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(produitService.archiveProduct(id));
    }
}
