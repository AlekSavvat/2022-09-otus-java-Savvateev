package ru.otus.listener.homework;

import ru.otus.listener.Listener;
import ru.otus.model.Message;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class HistoryListener implements Listener, HistoryReader {
    private final Map<Long, Message> journal = new HashMap<>();
    @Override
    public void onUpdated(Message msg) {
        if(Objects.nonNull(msg)) {
            journal.put(msg.getId(), msg.clone());
        }
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(journal.get(id));
    }
}
