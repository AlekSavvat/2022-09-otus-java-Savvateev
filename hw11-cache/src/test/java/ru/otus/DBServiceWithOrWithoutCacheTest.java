package ru.otus;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.DbServiceClientWithCache;
import ru.otus.core.repository.executor.DbExecutorImpl;
import ru.otus.core.sessionmanager.TransactionRunnerJdbc;
import ru.otus.crm.datasource.DriverManagerDataSource;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.jdbc.mapper.*;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.stream.LongStream;

@DisplayName("Сравниваем скорость работы с cache и без ")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DBServiceWithOrWithoutCacheTest {
    private static final Logger logger = LoggerFactory.getLogger(DBServiceWithOrWithoutCacheTest.class);
    private DataSource dataSource;
    private DBServiceClient serviceWithCache;
    private DBServiceClient serviceWithoutCache;

    @BeforeAll
    void initSchema(){
        dataSource = new DriverManagerDataSource("jdbc:postgresql://localhost:5430/demoDB"
                , "usr", "pwd");
        flywayMigrations(dataSource);
    }

    @BeforeEach
    void setUp() {
        var transactionRunner = new TransactionRunnerJdbc(dataSource);

        var dbExecutor = new DbExecutorImpl();
        EntityClassMetaData<Client> entityClassMetaDataClient = new EntityClassMetaDataImpl<>(Client.class);
        EntitySQLMetaData entitySQLMetaDataClient = new EntitySQLMetaDataImpl(entityClassMetaDataClient);
        var dataTemplateClient = new DataTemplateJdbc<>(dbExecutor,  entitySQLMetaDataClient, entityClassMetaDataClient);

        this.serviceWithCache = new DbServiceClientWithCache(transactionRunner, dataTemplateClient);
        this.serviceWithoutCache = new DbServiceClientImpl(transactionRunner, dataTemplateClient);
    }

    @DisplayName("Создание \\ чтение записей")
    @ParameterizedTest(name = " с использованием cache: {0}")
    @ValueSource(booleans = {false, true})
    void doRun(boolean useCache) {
        logger.info("Use cache: {}", useCache);
        DBServiceClient dbServiceClient;
        if (useCache) {
            dbServiceClient = serviceWithCache;
        } else {
            dbServiceClient = serviceWithoutCache;
        }

        var startTime = System.currentTimeMillis();

        var ids = LongStream.range(1, 500)
                .map(i -> dbServiceClient.saveClient(new Client(String.format("client[%s]", i))).getId())
                .toArray();
        var saveCompleteTime = System.currentTimeMillis();

        Arrays.stream(ids).forEach(dbServiceClient::getClient);
        var getCompleteTime = System.currentTimeMillis();

        logger.info("""
                        Time to save: {}
                        Time to get all by id: {}
                        Total time: {}""",
                saveCompleteTime - startTime,
                getCompleteTime - saveCompleteTime,
                getCompleteTime - startTime);
    }

    private static void flywayMigrations(DataSource dataSource) {
        logger.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/migr")
                .load();
        flyway.migrate();
        logger.info("db migration finished.");
        logger.info("***");
    }

}
