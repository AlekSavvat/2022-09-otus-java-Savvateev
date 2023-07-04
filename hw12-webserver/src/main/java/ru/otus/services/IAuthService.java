package ru.otus.services;

public interface IAuthService {
    boolean authenticate(String login, String password);
}
