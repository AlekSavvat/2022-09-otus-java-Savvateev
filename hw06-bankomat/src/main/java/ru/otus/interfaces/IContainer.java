package ru.otus.interfaces;

import ru.otus.Banknot;
import ru.otus.CustomInteger;

import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;

public interface IContainer {
    void put(Banknot banknot, CustomInteger count);

    Map<Banknot, CustomInteger> extract(Banknot banknot, CustomInteger count);

    Map<Banknot, CustomInteger> extract(Map<Banknot, CustomInteger> mapForExtract);
    // список разных банконот
    NavigableSet<Banknot> getSetBanknot();

    CustomInteger getCountBanknots(Banknot banknot);

    Integer getBalance();

    Map<Banknot, Integer> getBalanceAsMap();
}
