package com.s2f.s2fapi.service.implementation;

import com.s2f.s2fapi.constants.ErrorsMessages;
import com.s2f.s2fapi.dto.response.CategorieDto;
import com.s2f.s2fapi.dto.response.ProduitDTO;
import com.s2f.s2fapi.dto.response.ResponseDTOPaging;
import com.s2f.s2fapi.exceptions.BadRequestException;
import com.s2f.s2fapi.exceptions.EntityNotFoundException;
import com.s2f.s2fapi.exceptions.InternalServerErrorException;
import com.s2f.s2fapi.mapper.CategorieMapper;
import com.s2f.s2fapi.mapper.ProduitMapper;
import com.s2f.s2fapi.model.Produit;
import com.s2f.s2fapi.repository.CategorieRepository;
import com.s2f.s2fapi.repository.ProduitRepository;
import com.s2f.s2fapi.service.interfaces.ProduitService;
import com.s2f.s2fapi.specifications.ProduitSpecifications;
import com.s2f.s2fapi.utils.LoggingUtil;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ProduitServiceImp implements ProduitService {
    private final CategorieRepository categorieRepository;
    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;
    private final CategorieMapper categorieMapper;

    /**
     * Retrieves all categories that are not archived.
     *
     * @return a list of {@link CategorieDto} representing the non-archived categories.
     */
    @Override
    public List<CategorieDto> getAllCategories() {
        return categorieRepository.findAllByArchiveFalse().stream()
                .map(categorieMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Adds a new category to the repository.
     *
     * @param categorieDto the category data to be added.
     * @return the added {@link CategorieDto}.
     * @throws BadRequestException if a category with the same name already exists.
     * @throws InternalServerErrorException if an error occurs while adding the category.
     */
    @Override
    @Transactional
    public CategorieDto addCategorie(CategorieDto categorieDto) {
        try {
            LoggingUtil.logInfo(this.getClass(), "addCategorie", "begin add new category");
            var categorie = categorieRepository.save(categorieMapper.toEntity(categorieDto));
            LoggingUtil.logInfo(this.getClass(), "addCategorie", "category added successfully");
            return categorieMapper.toDTO(categorie);
        } catch (DataIntegrityViolationException ex) {
            LoggingUtil.logError(
                    this.getClass(), "addCategorie", ErrorsMessages.CATEGORY_NAME_EXISTS);
            throw new BadRequestException(ErrorsMessages.CATEGORY_NAME_EXISTS);
        } catch (Exception ex) {
            LoggingUtil.logError(
                    this.getClass(), "addCategorie", ErrorsMessages.ADD_CATEGORY_ERROR);
            throw new InternalServerErrorException(ErrorsMessages.ADD_CATEGORY_ERROR);
        }
    }

    /**
     * Retrieves products based on filters with pagination.
     *
     * @param libelle the product name filter.
     * @param categorieId the category ID filter.
     * @param pageable pagination information.
     * @return a paginated response of {@link ProduitDTO}.
     */
    @Override
    public ResponseDTOPaging<ProduitDTO> filterProduits(
            String libelle, Long categorieId, Pageable pageable) {
        LoggingUtil.logInfo(
                this.getClass(),
                "filterProduits",
                "get list of products with pagination and filter");
        Specification<Produit> produitSpecification =
                Specification.where(ProduitSpecifications.isArchiveFalse())
                        .and(ProduitSpecifications.hasLibelle(libelle))
                        .and(ProduitSpecifications.hasCategorie(categorieId));
        var productsPage = produitRepository.findAll(produitSpecification, pageable);
        return new ResponseDTOPaging<>(
                productsPage.getContent().stream()
                        .map(produitMapper::toDTO)
                        .collect(Collectors.toList()),
                productsPage.getNumber(),
                productsPage.getTotalElements(),
                productsPage.getTotalPages());
    }

    @Override
    public List<ProduitDTO> getAllProduits() {
        Specification<Produit> produitSpecification =
                Specification.where(ProduitSpecifications.isArchiveFalse());
        return produitRepository.findAll(produitSpecification).stream()
                .map(produitMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Adds a new product to the repository.
     *
     * @param produitDTO the product data to be added.
     * @return the added {@link ProduitDTO}.
     * @throws InternalServerErrorException if an error occurs while adding the product.
     */
    @Override
    @Transactional
    public ProduitDTO addProduct(ProduitDTO produitDTO) {
        try {
            LoggingUtil.logInfo(this.getClass(), "addProduct", "begin add new product");
            var product = produitRepository.save(produitMapper.toEntity(produitDTO));
            LoggingUtil.logInfo(this.getClass(), "addProduct", "product added successfully");
            return produitMapper.toDTO(product);
        } catch (Exception ex) {
            LoggingUtil.logError(this.getClass(), "addProduct", ErrorsMessages.PRODUCT_ADD_ERROR);
            throw new InternalServerErrorException(ErrorsMessages.PRODUCT_ADD_ERROR);
        }
    }

    /**
     * Retrieves a product by its ID.
     *
     * @param id the ID of the product to retrieve.
     * @return the {@link Produit} if found.
     * @throws EntityNotFoundException if the product with the given ID does not exist.
     */
    @Override
    public Produit getProductById(Long id) {
        return produitRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorsMessages.PRODUCT_NOT_FOUND));
    }

    @Override
    public ProduitDTO archiveProduct(Long id) {
        var produit = getProductById(id);
        produit.setArchive(true);
        return produitMapper.toDTO(produitRepository.save(produit));
    }
}
