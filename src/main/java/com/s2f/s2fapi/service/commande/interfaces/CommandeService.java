package com.s2f.s2fapi.service.commande.interfaces;

import com.s2f.s2fapi.dto.request.CommandeDtoRequest;
import com.s2f.s2fapi.dto.response.CommandeDtoResponse;

public interface CommandeService {
    long countAllCommande();
    String getNextCommandeNumber();
    CommandeDtoResponse addNewCommande(CommandeDtoRequest commandeDtoRequest);
}
