package ru.otus.interfaces;

import ru.otus.Banknot;
//import ru.otus.CustomInteger;

import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

public interface IContainer {
    void put(Banknot banknot, Integer count);

    Map<Banknot, Integer> extract(Map<Banknot, Integer> mapForExtract);
    // получить список вариантов банкнот
    NavigableSet<Banknot> getSetBanknot();

    //    получить кол-во определнных банкнот
    Integer getCountBanknots(Banknot banknot);

    Integer getBalance();

    Map<Banknot, Integer> getBalanceAsMap();
}
