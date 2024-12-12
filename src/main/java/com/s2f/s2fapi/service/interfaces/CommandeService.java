package com.s2f.s2fapi.service.interfaces;

import com.s2f.s2fapi.dto.request.CommandeDtoRequest;
import com.s2f.s2fapi.dto.response.CommandeDtoResponse;
import com.s2f.s2fapi.dto.response.ResponseDTOPaging;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface CommandeService {
    long countAllCommande();
    String getNextCommandeNumber();
    CommandeDtoResponse addNewCommande(CommandeDtoRequest commandeDtoRequest);
    ResponseDTOPaging<CommandeDtoResponse> filterCommande(
            String numero, String etatCommande, Long clientId, Date startDate, Date endDate, Pageable pageable);
    ResponseDTOPaging<CommandeDtoResponse> getAllCommandeByEtatCommande(String etatCommande, Pageable pageable);
}
