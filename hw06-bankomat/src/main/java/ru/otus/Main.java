package ru.otus;

import ru.otus.impl.Container;
import ru.otus.impl.Operator;
import ru.otus.interfaces.IContainer;
import ru.otus.interfaces.IOperator;

import java.util.Map;

import static ru.otus.Banknot.*;

public class Main {
    public static void main(String[] args) {
        IContainer container = new Container();
        IOperator operator = new Operator(container);

        var moneyMap = Map.of(
                BANKNOTE_50, new CustomInteger(1),
                BANKNOTE_100, new CustomInteger(1),
                BANKNOTE_500, new CustomInteger(1),
                BANKNOTE_1000, new CustomInteger(1),
                BANKNOTE_5000, new CustomInteger(1)
        );

        operator.receive(moneyMap);
        System.out.println(container.getBalanceAsString());
    }
}