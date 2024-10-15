package com.s2f.s2fapi.controller.administration;

import com.s2f.s2fapi.dto.request.ProduitFilterPayload;
import com.s2f.s2fapi.dto.response.CategorieDto;
import com.s2f.s2fapi.dto.response.ProduitDTO;
import com.s2f.s2fapi.dto.response.ResponseDTOPaging;
import com.s2f.s2fapi.service.administration.interfaces.ProduitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProduitController {
    private final ProduitService produitService;

    @PostMapping("/v1/category")
    public ResponseEntity<CategorieDto> addCategorie(@RequestBody @Valid CategorieDto categorieDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produitService.addCategorie(categorieDto));
    }

    @GetMapping("/v1/categories")
    public ResponseEntity<List<CategorieDto>> getAllCategories() {
        return ResponseEntity.status(HttpStatus.OK).body(produitService.getAllCategories());
    }

    @PostMapping("/v1/products")
    public ResponseEntity<ResponseDTOPaging<ProduitDTO>> getAllProductByFilters(@RequestBody ProduitFilterPayload produitFilterPayload) {
        return ResponseEntity.ok(produitService.getAllProduitByFilters(produitFilterPayload));
    }

    @PostMapping("/v1/product")
    public ResponseEntity<ProduitDTO> addProduit(@RequestBody @Valid ProduitDTO produitDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(produitService.addProduct(produitDTO));
    }

    @DeleteMapping("/v1/product/{id}")
    public ResponseEntity<ProduitDTO> archiveProduct(@PathVariable Long id) {
        var produitDTO = ProduitDTO.toProduitDTO(produitService.getProductById(id));
        produitDTO.setArchive(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(produitService.addProduct(produitDTO));
    }
}
