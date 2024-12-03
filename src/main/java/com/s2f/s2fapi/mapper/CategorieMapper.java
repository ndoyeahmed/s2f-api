package com.s2f.s2fapi.mapper;

import com.s2f.s2fapi.dto.response.CategorieDto;
import com.s2f.s2fapi.model.Categorie;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategorieMapper {
    CategorieDto toDTO(Categorie entity);

    Categorie toEntity(CategorieDto dto);

    List<CategorieDto> toDTOList(List<Categorie> entities);

    List<Categorie> toEntityList(List<CategorieDto> dtos);
}
