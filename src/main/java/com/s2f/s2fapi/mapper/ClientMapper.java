package com.s2f.s2fapi.mapper;

import com.s2f.s2fapi.dto.request.ClientDtoRequest;
import com.s2f.s2fapi.dto.request.MesureDtoRequest;
import com.s2f.s2fapi.dto.response.ClientDtoResponse;
import com.s2f.s2fapi.dto.response.MesureDtoResponse;
import com.s2f.s2fapi.model.Client;
import com.s2f.s2fapi.model.Mesure;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "mesures", source = "mesures")
    ClientDtoResponse toClientDTOResponse(Client client);

    @Mapping(target = "mesures", source = "mesures")
    Client toClientEntity(ClientDtoRequest clientDtoRequest);

    List<Mesure> toMesureEntityList(List<MesureDtoRequest> mesureDtoRequests);

    List<MesureDtoResponse> toMesureDTOResponseList(List<Mesure> mesures);
}
