package ru.otus.crm.service;

import ru.otus.crm.model.Client;
import ru.otus.dto.ClientDto;

import java.util.List;
import java.util.Optional;

public interface DBServiceClient {

    Client saveClient(Client client);

    Optional<Client> getClient(long id);

    List<Client> findAll();

    List<ClientDto> findAllDto();
}