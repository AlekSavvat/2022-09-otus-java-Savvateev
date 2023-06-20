package ru.otus.services;

import java.util.Properties;

public class AdminAuthService implements IAuthService {

    private final String adminLogin;
    private final String adminPassword;

    public AdminAuthService() {
        Properties properties = new Properties();

        try(var inputStream= this.getClass().getClassLoader().getResourceAsStream("auth.properties");
        ){
            properties.load(inputStream);
            adminLogin = properties.getProperty("login");
            adminPassword = properties.getProperty("password");
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean authenticate(String login, String password) {
        return adminLogin.equals(login) && adminPassword.equals(password);
    }

}
