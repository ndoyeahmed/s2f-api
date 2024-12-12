package com.s2f.s2fapi.mapper;

import com.s2f.s2fapi.dto.response.CommandeProduitDtoResponse;
import com.s2f.s2fapi.model.CommandeProduit;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommandeProduitMapper {
    CommandeProduitDtoResponse toDTOList(CommandeProduit commandeProduits);
}
