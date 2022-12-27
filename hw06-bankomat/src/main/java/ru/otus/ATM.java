package ru.otus;

import ru.otus.interfaces.IContainer;
import ru.otus.interfaces.IOperator;
import ru.otus.impl.Container;
import ru.otus.impl.Operator;

import java.util.*;

public class ATM {
    private final IContainer container;
    private final IOperator operator;

    public ATM() {
        this.container = new Container();
        this.operator = new Operator(container);
    }

    public void receive(Map<Banknot, Integer> banknotes) {
        operator.receive(banknotes);
    }

    public Map<Banknot, Integer> give(Integer sum) {
        return operator.give(sum);
    }

    public Integer getBalance() {
        return container.getBalance();
    }

    public Map<Banknot, Integer> getBalanceAsMap() {
        return container.getBalanceAsMap();
    }
}