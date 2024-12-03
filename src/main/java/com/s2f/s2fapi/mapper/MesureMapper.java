package com.s2f.s2fapi.mapper;

import com.s2f.s2fapi.dto.response.CategorieDto;
import com.s2f.s2fapi.dto.response.MesureDtoResponse;
import com.s2f.s2fapi.model.Categorie;
import com.s2f.s2fapi.model.Mesure;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MesureMapper {
    MesureDtoResponse toDTO(Mesure entity);

    Mesure toEntity(MesureDtoResponse dto);

    List<MesureDtoResponse> toDTOList(List<Mesure> entities);

    List<Mesure> toEntityList(List<MesureDtoResponse> dtos);
}
