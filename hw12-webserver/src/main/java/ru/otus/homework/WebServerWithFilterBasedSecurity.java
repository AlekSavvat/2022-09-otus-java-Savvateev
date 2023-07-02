package ru.otus.homework;

import org.hibernate.cfg.Configuration;
import ru.otus.core.repository.DataTemplateHibernate;
import ru.otus.core.repository.HibernateUtils;
import ru.otus.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.crm.model.Address;
import ru.otus.crm.model.Client;
import ru.otus.crm.model.Phone;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.crm.service.DbServiceClientImpl;
import ru.otus.server.ClientWebServer;
import ru.otus.server.ClientWebServerWithFilterBasedSecurity;
import ru.otus.services.AdminAuthService;
import ru.otus.services.IAuthService;
import ru.otus.services.TemplateProcessor;
import ru.otus.services.TemplateProcessorImpl;

import java.util.List;

/*
    // Стартовая страница
    http://localhost:8080

    // Клиенты
    http://localhost:8080/api/client
    // Логин: admin
    // Пароль: pwd
*/
public class WebServerWithFilterBasedSecurity {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";


    public static void main(String[] args) throws Exception {
        DBServiceClient dbServiceClient = setUpClientService();
        initClients(dbServiceClient);
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        IAuthService authService = new AdminAuthService();

        ClientWebServer webServer = new ClientWebServerWithFilterBasedSecurity(WEB_SERVER_PORT,
                authService, dbServiceClient, templateProcessor);

        webServer.start();
        webServer.join();
    }

    private static DBServiceClient setUpClientService(){
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory = HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate);
        return dbServiceClient;
    }

    private static void initClients(DBServiceClient dbServiceClient) {
        dbServiceClient.saveClient(
            new Client(
                null, "Путин В.В.",
                new Address(null, "Россия"),
                List.of(new Phone(null, "+1 234"))
            )
        );
        dbServiceClient.saveClient(
            new Client(
                    null,"Зеленский В.",
            new Address(null, "Украина"),
            List.of(new Phone(null, "+9 234"))
        ));

        dbServiceClient.saveClient(
                new Client(
                        null,
                        "Байден Дж.",
                        new Address(null, "Пиндосия"),
                        List.of(new Phone(null, "+5 678"))
                )
        );
    }
}
