package com.s2f.s2fapi.mapper;

import com.s2f.s2fapi.dto.response.ProduitDTO;
import com.s2f.s2fapi.model.Produit;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProduitMapper {
    ProduitDTO toDTO(Produit entity);

    Produit toEntity(ProduitDTO dto);

    List<ProduitDTO> toDTOList(List<Produit> entities);

    List<Produit> toEntityList(List<ProduitDTO> dtos);
}
