package ru.otus;

import ru.otus.interfaces.IContainer;
import ru.otus.interfaces.ICalcCash;
import ru.otus.impl.Container;
import ru.otus.impl.CalcCash;

import java.util.*;

public class ATM {
    private final IContainer container;
    private final ICalcCash operator;

    public ATM() {
        this.container = new Container();
        this.operator = new CalcCash(container);
    }

    public void receive(Map<Banknot, Integer> banknotes) {
        operator.receive(banknotes);
    }
    public void receive(List<Banknot> banknotes) {
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