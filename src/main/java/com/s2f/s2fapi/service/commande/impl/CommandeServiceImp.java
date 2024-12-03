package com.s2f.s2fapi.service.commande.impl;

import com.s2f.s2fapi.dto.request.CommandeDtoRequest;
import com.s2f.s2fapi.dto.response.CommandeDtoResponse;
import com.s2f.s2fapi.repository.CommandeRepository;
import com.s2f.s2fapi.service.commande.interfaces.CommandeService;
import jakarta.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CommandeServiceImp implements CommandeService {
    private final CommandeRepository commandeRepository;

    @Override
    public long countAllCommande() {
        return commandeRepository.count();
    }

    @Override
    public String getNextCommandeNumber() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String datePart = dateFormat.format(new Date());

        long numeroSequentiel = countAllCommande() + 1;

        // Construction du numéro de commande final
        return "CMD-" + datePart + "-" + String.format("%04d", numeroSequentiel);
    }

    @Override
    public CommandeDtoResponse addNewCommande(CommandeDtoRequest commandeDtoRequest) {
        return null;
    }
}
