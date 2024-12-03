package com.s2f.s2fapi.service.administration.interfaces;

import com.s2f.s2fapi.dto.request.ClientDtoRequest;
import com.s2f.s2fapi.dto.request.MesureDtoRequest;
import com.s2f.s2fapi.dto.response.ClientDtoResponse;
import com.s2f.s2fapi.dto.response.MesureDtoResponse;
import com.s2f.s2fapi.dto.response.ProduitDTO;
import com.s2f.s2fapi.dto.response.ResponseDTOPaging;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClientService {
    ClientDtoResponse addClient(ClientDtoRequest client);
    MesureDtoResponse addMesureClient(MesureDtoRequest mesureDtoRequest);
    List<ClientDtoResponse> getAllClientNotArchive();

    ResponseDTOPaging<ClientDtoResponse> filterClients(
            String nom, String telephone, Pageable pageable);
}
