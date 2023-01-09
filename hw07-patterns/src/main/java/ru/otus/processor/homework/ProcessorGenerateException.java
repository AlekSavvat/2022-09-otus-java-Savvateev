package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;
    // Decorator
public class ProcessorGenerateException implements Processor {
    private final LocalDateTime provider;

    public ProcessorGenerateException(LocalDateTime provider) {
        this.provider = provider;
    }

    public Message process(Message message){
        //LocalDateTime dateTime = provider.get();
        if(provider.getSecond()%2 == 0)
            throw new RuntimeException(String.format("Even second: %d", provider.getSecond()));

        return message;
    }
}
