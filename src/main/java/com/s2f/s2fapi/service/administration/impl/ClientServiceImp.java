package com.s2f.s2fapi.service.administration.impl;

import com.s2f.s2fapi.constants.ErrorsMessages;
import com.s2f.s2fapi.dto.request.ClientDtoRequest;
import com.s2f.s2fapi.dto.request.MesureDtoRequest;
import com.s2f.s2fapi.dto.response.ClientDtoResponse;
import com.s2f.s2fapi.dto.response.MesureDtoResponse;
import com.s2f.s2fapi.dto.response.ResponseDTOPaging;
import com.s2f.s2fapi.exceptions.BadRequestException;
import com.s2f.s2fapi.exceptions.EntityNotFoundException;
import com.s2f.s2fapi.exceptions.InternalServerErrorException;
import com.s2f.s2fapi.mapper.ClientMapper;
import com.s2f.s2fapi.mapper.MesureMapper;
import com.s2f.s2fapi.model.Client;
import com.s2f.s2fapi.model.Mesure;
import com.s2f.s2fapi.model.Produit;
import com.s2f.s2fapi.repository.ClientRepository;
import com.s2f.s2fapi.repository.MesureRepository;
import com.s2f.s2fapi.service.administration.interfaces.ClientService;
import com.s2f.s2fapi.specifications.ClientSpecification;
import com.s2f.s2fapi.specifications.ProduitSpecifications;
import com.s2f.s2fapi.utils.LoggingUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ClientServiceImp implements ClientService {
    private final ClientRepository clientRepository;
    private final MesureRepository mesureRepository;
    private final ClientMapper clientMapper;
    private final MesureMapper mesureMapper;

    /**
     * Add new client wth measures
     * @param clientDtoRequest the client and measures to be added
     * @return the added {@link ClientDtoResponse}
     * @throws BadRequestException if a client already exists.
     * @throws InternalServerErrorException if an error occurs while adding the client.
     */
    @Override
    @Transactional
    public ClientDtoResponse addClient(ClientDtoRequest clientDtoRequest) {
        try {
            LoggingUtil.logInfo(this.getClass(), "addClient", "begin add new client with measures");
            var client = clientRepository.save(clientMapper.toClientEntity(clientDtoRequest));
            var mesures = clientMapper.toMesureEntityList(clientDtoRequest.getMesures());
            LoggingUtil.logInfo(
                    this.getClass(), "addClient", "client and measures added successfully");
            mesures = mesureRepository.saveAll(mesures);
            var clientResponseDto = clientMapper.toClientDTOResponse(client);
            var mesureResponseDto = clientMapper.toMesureDTOResponseList(mesures);
            clientResponseDto.setMesures(mesureResponseDto);
            return clientResponseDto;
        } catch (DataIntegrityViolationException ex) {
            LoggingUtil.logError(this.getClass(), "addClient", ErrorsMessages.CLIENT_EXISTS);
            throw new BadRequestException(ErrorsMessages.CLIENT_EXISTS);
        } catch (Exception ex) {
            LoggingUtil.logError(this.getClass(), "addClient", ErrorsMessages.ADD_CLIENT_ERROR);
            throw new InternalServerErrorException(ErrorsMessages.ADD_CLIENT_ERROR);
        }
    }

    @Override
    public MesureDtoResponse addMesureClient(MesureDtoRequest mesureDtoRequest) {
        return null;
    }

    @Override
    public List<ClientDtoResponse> getAllClientNotArchive() {
        return clientRepository.findAllByArchiveFalse().stream()
                .map(clientMapper::toClientDTOResponse)
                .toList();
    }

    @Override
    public ResponseDTOPaging<ClientDtoResponse> filterClients(String nom, String telephone, Pageable pageable) {
        LoggingUtil.logInfo(
                this.getClass(),
                "filterClients",
                "entrer un nom ou prenom valable");
        Specification<Client> clientSpecification =
                Specification.where(ClientSpecification.isArchiveFalse())
                        .and(ClientSpecification.hasNom(nom))
                        .and(ClientSpecification.hasTelephone(telephone));
        var clientsPage = clientRepository.findAll(clientSpecification, pageable);
        return new ResponseDTOPaging<>(
                clientsPage.getContent().stream()
                        .map(clientMapper::toClientDTOResponse)
                        .collect(Collectors.toList()),
                clientsPage.getNumber(),
                clientsPage.getTotalElements(),
                clientsPage.getTotalPages());
    }

    @Override
    public Mesure getMesureById(Long id) {
        return mesureRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ErrorsMessages.PRODUCT_NOT_FOUND));
    }

    @Override
    public MesureDtoResponse archiveProduct(Long id) {
        var mesure = getMesureById(id);
        mesure.setArchive(true);
        return mesureMapper.toDTO(mesureRepository.save(mesure));
    }
}
