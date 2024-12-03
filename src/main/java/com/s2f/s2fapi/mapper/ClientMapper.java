package com.s2f.s2fapi.mapper;

import com.s2f.s2fapi.dto.request.ClientDtoRequest;
import com.s2f.s2fapi.dto.request.MesureDtoRequest;
import com.s2f.s2fapi.dto.response.ClientDtoResponse;
import com.s2f.s2fapi.dto.response.MesureDtoResponse;
import com.s2f.s2fapi.model.Client;
import com.s2f.s2fapi.model.Mesure;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDtoResponse toClientDTOResponse(Client client);

    Client toClientEntity(ClientDtoRequest clientDtoRequest);

    List<Mesure> toMesureEntityList(List<MesureDtoRequest> mesureDtoRequests);

    List<MesureDtoResponse> toMesureDTOResponseList(List<Mesure> mesures);
}
