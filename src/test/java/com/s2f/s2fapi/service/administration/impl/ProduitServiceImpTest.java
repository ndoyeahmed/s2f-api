package com.s2f.s2fapi.service.administration.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.s2f.s2fapi.constants.ErrorsMessages;
import com.s2f.s2fapi.dto.response.CategorieDto;
import com.s2f.s2fapi.dto.response.ProduitDTO;
import com.s2f.s2fapi.exceptions.BadRequestException;
import com.s2f.s2fapi.exceptions.EntityNotFoundException;
import com.s2f.s2fapi.exceptions.InternalServerErrorException;
import com.s2f.s2fapi.mapper.CategorieMapper;
import com.s2f.s2fapi.mapper.ProduitMapper;
import com.s2f.s2fapi.model.Produit;
import com.s2f.s2fapi.repository.CategorieRepository;
import com.s2f.s2fapi.repository.ProduitRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

class ProduitServiceImpTest {

    @Mock private CategorieRepository categorieRepository;

    @Mock private ProduitRepository produitRepository;

    @Mock private ProduitMapper produitMapper;

    @Mock private CategorieMapper categorieMapper;

    @InjectMocks private ProduitServiceImp produitService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCategoriesSuccess() {
        // Arrange
        CategorieDto categorieDto = new CategorieDto();
        when(categorieRepository.findAllByArchiveFalse()).thenReturn(List.of());
        when(categorieMapper.toDTO(any())).thenReturn(categorieDto);

        // Act
        List<CategorieDto> result = produitService.getAllCategories();

        // Assert
        assertThat(result).isNotNull();
        verify(categorieRepository, times(1)).findAllByArchiveFalse();
        verify(categorieMapper, times(0)).toDTO(any());
    }

    @Test
    void testAddCategorieSuccess() {
        // Arrange
        CategorieDto categorieDto = new CategorieDto();
        when(categorieRepository.save(any())).thenReturn(null); // Simule l'ajout
        when(categorieMapper.toEntity(any())).thenReturn(null);
        when(categorieMapper.toDTO(any())).thenReturn(categorieDto);

        // Act
        CategorieDto result = produitService.addCategorie(categorieDto);

        // Assert
        assertThat(result).isNotNull();
        verify(categorieRepository, times(1)).save(any());
        verify(categorieMapper, times(1)).toEntity(any());
    }

    @Test
    void testAddCategorieThrowsBadRequestException() {
        // Arrange
        CategorieDto categorieDto = new CategorieDto();
        when(categorieRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        // Act & Assert
        BadRequestException exception =
                assertThrows(
                        BadRequestException.class, () -> produitService.addCategorie(categorieDto));

        assertThat(exception.getMessage()).isEqualTo(ErrorsMessages.CATEGORY_NAME_EXISTS);
        verify(categorieRepository, times(1)).save(any());
    }

    @Test
    void testAddCategorieThrowsInternalServerErrorException() {
        // Arrange
        CategorieDto categorieDto = new CategorieDto();
        when(categorieRepository.save(any())).thenThrow(RuntimeException.class);

        // Act & Assert
        InternalServerErrorException exception =
                assertThrows(
                        InternalServerErrorException.class,
                        () -> produitService.addCategorie(categorieDto));

        assertThat(exception.getMessage()).isEqualTo(ErrorsMessages.ADD_CATEGORY_ERROR);
        verify(categorieRepository, times(1)).save(any());
    }

    @Test
    void testFilterProduitsSuccess() {
        // Arrange
        Produit produit = new Produit();
        ProduitDTO produitDTO = new ProduitDTO();
        Page<Produit> page = new PageImpl<>(List.of(produit));

        when(produitRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(page);
        when(produitMapper.toDTO(any())).thenReturn(produitDTO);

        // Act
        var result = produitService.filterProduits("libelle", 1L, Pageable.unpaged());

        // Assert
        assertThat(result.getResult()).hasSize(1);
        verify(produitRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void testAddProductSuccess() {
        // Arrange
        Produit produit = new Produit();
        ProduitDTO produitDTO = new ProduitDTO();

        when(produitRepository.save(any())).thenReturn(produit);
        when(produitMapper.toEntity(any())).thenReturn(produit);
        when(produitMapper.toDTO(any())).thenReturn(produitDTO);

        // Act
        ProduitDTO result = produitService.addProduct(produitDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(produitRepository, times(1)).save(any());
    }

    @Test
    void testAddProductThrowsInternalServerErrorException() {
        // Arrange
        ProduitDTO produitDTO = new ProduitDTO();
        when(produitRepository.save(any())).thenThrow(RuntimeException.class);

        // Act & Assert
        InternalServerErrorException exception =
                assertThrows(
                        InternalServerErrorException.class,
                        () -> produitService.addProduct(produitDTO));

        assertThat(exception.getMessage()).isEqualTo(ErrorsMessages.PRODUCT_ADD_ERROR);
        verify(produitRepository, times(1)).save(any());
    }

    @Test
    void testGetProductByIdSuccess() {
        // Arrange
        Produit produit = new Produit();
        when(produitRepository.findById(anyLong())).thenReturn(Optional.of(produit));

        // Act
        Produit result = produitService.getProductById(1L);

        // Assert
        assertThat(result).isNotNull();
        verify(produitRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetProductByIdThrowsEntityNotFoundException() {
        // Arrange
        when(produitRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception =
                assertThrows(
                        EntityNotFoundException.class, () -> produitService.getProductById(1L));

        assertThat(exception.getMessage()).isEqualTo(ErrorsMessages.PRODUCT_NOT_FOUND);
        verify(produitRepository, times(1)).findById(anyLong());
    }
}
