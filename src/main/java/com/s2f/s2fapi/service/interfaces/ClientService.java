package com.s2f.s2fapi.service.interfaces;

import com.s2f.s2fapi.dto.request.ClientDtoRequest;
import com.s2f.s2fapi.dto.response.ClientDtoResponse;

import java.util.List;

public interface ClientService {
    ClientDtoResponse addClient(ClientDtoRequest client);
    List<ClientDtoResponse> getAllClientNotArchive();
}
