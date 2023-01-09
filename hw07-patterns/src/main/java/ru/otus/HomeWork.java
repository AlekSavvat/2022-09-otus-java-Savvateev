package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.homework.ProcessorGenerateException;
import ru.otus.processor.homework.ProcessorSwapFields;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HomeWork {
    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
             Секунда должна определяьться во время выполнения.
             Тест - важная часть задания
             Обязательно посмотрите пример к паттерну Мементо!
       4. Сделать Listener для ведения истории (ьподумайте, как сделат, чтобы сообщения не портились)
          Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
          Для него уже есть тест, убедитесь, что тест проходит
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
        var processors = List.of(
            new ProcessorSwapFields(),
            new ProcessorGenerateException(LocalDateTime.of(2023,1,9,0,36,2))
        );

        var complexProcessor = new ComplexProcessor(processors
            , ex -> {System.out.println("EXCEPTION:" + ex);});
        var historyListener = new HistoryListener();

        complexProcessor.addListener(historyListener);

        var field13 = new ObjectForMessage();
        var field13Data = new ArrayList<String>();
        field13Data.add("11");
        field13Data.add("12");
        field13Data.add("13");
        field13.setData(field13Data);

        var message = new Message.Builder(1L)
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field6("field6")
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .field13(field13)
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result:" + result);

        complexProcessor.removeListener(historyListener);

        ////////////////////
        // I got this part from Test just output extend_info
        message.getField13().setData(new ArrayList<>());
        System.out.println("message: " + message);
        System.out.println("message.field13: " + message.getField13().getData());

        var messageFromHistory = historyListener.findMessageById(1L);
        if(messageFromHistory.isPresent()){
            System.out.println("messageFromHistory: " + messageFromHistory.get());
            System.out.println("messageFromHistory.field13: " + messageFromHistory.get().getField13().getData());
        }

    }
}
