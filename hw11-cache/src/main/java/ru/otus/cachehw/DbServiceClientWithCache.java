package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionRunner;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;

import java.util.List;
import java.util.Optional;

public class DbServiceClientWithCache implements DBServiceClient {
    private static final Logger log = LoggerFactory.getLogger(DbServiceClientWithCache.class);

    private final DataTemplate<Client> dataTemplate;
    private final TransactionRunner transactionRunner;

    private final HwCache<Long, Client> cache;

    public DbServiceClientWithCache(TransactionRunner transactionRunner,
                                        DataTemplate<Client> dataTemplate,
                                    HwCache<Long, Client> cache
                                    ) {
        this.transactionRunner = transactionRunner;
        this.dataTemplate = dataTemplate;
        this.cache = cache;
    }

    @Override
    public Client saveClient(Client client) {
        // в случае сохранения кладем клиента в кэш
        var saved = transactionRunner.doInTransaction(connection -> {
            if (client.getId() == null) {
                var clientId = dataTemplate.insert(connection, client);
                var newClient = new Client(clientId, client.getName());
                log.info("created client: {}", newClient);
                return newClient;
            }
            dataTemplate.update(connection, client);
            log.info("updated client: {}", client);
            return client;
        });

        cache.put(saved.getId(), client);

        return saved;
    }

    @Override
    public Optional<Client> getClient(long id) {
        // в случае запроса сначала пытаемся вытащить клиента из кэша
        var client = cache.get(id);
        if (client == null) {
            var optionalClient = transactionRunner.doInTransaction(connection -> {
                var clientOptional = dataTemplate.findById(connection, id);
                log.info("client: {}", clientOptional);
                return clientOptional;
            });
            optionalClient.ifPresent(cl -> cache.put(cl.getId(), cl));
            return optionalClient;
        } else {
            return Optional.of(client);
        }
    }

    @Override
    public List<Client> findAll() {
        return transactionRunner.doInTransaction(connection -> {
            var clientList = dataTemplate.findAll(connection);
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }
}
