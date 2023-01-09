package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProcessorGenerateExceptionTest {
    @Test
    @DisplayName("Testing process in odd second")
    void oddSecTest(){
        var processor = new ProcessorGenerateException(
                LocalDateTime.of(2023, 1, 8, 17, 31, 1));
        var message = new Message.Builder(1L).build();
        assertDoesNotThrow(() -> processor.process(message));
    }

    @Test
    @DisplayName("Testing process in even second")
    void EvenSecTest(){
        var processor = new ProcessorGenerateException(
                LocalDateTime.of(2023, 1, 8, 17, 31, 0));
        var message = new Message.Builder(1L).build();
        assertThrows(RuntimeException.class,
                () -> processor.process(message));
    }
}
