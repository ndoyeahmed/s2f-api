package com.s2f.s2fapi.service.administration.impl;

import com.s2f.s2fapi.dto.response.CategorieDto;
import com.s2f.s2fapi.exceptions.InternalServerErrorException;
import com.s2f.s2fapi.model.Categorie;
import com.s2f.s2fapi.repository.CategorieRepository;
import com.s2f.s2fapi.service.administration.interfaces.ProduitService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProduitServiceImp implements ProduitService {
    private final CategorieRepository categorieRepository;

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
}
