package ru.otus.server;

public interface IClientWebServer {
    void start() throws Exception;

    void join() throws Exception;

    void stop() throws Exception;
}
