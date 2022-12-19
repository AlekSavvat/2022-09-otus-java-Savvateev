package ru.otus.impl;

import ru.otus.Banknot;
import ru.otus.CustomInteger;
import ru.otus.interfaces.IContainer;

import java.util.*;

public class Container implements IContainer {
    TreeMap<Banknot, Integer> moneyBox = new TreeMap<>(Comparator.comparing(Banknot::getValue).reversed());

    public void put(Banknot banknot, CustomInteger count){
        moneyBox.put(banknot, getCountBanknots(banknot).getValue() + count.getValue());
    }

    public Map<Banknot, CustomInteger> extract(Banknot banknot, CustomInteger count) {
        if(moneyBox.getOrDefault(banknot,0) < count.getValue())
            throw  new IllegalArgumentException("Not enough banknots");

        moneyBox.compute(banknot,(k,v) -> v - count.getValue());
        return Map.of(banknot, count);
    }

    public Map<Banknot, CustomInteger> extract(Map<Banknot, CustomInteger> mapForExtract) {
        final Map<Banknot, CustomInteger> money = new HashMap<>();

        mapForExtract.entrySet()
                .stream()
                .map(entry -> extract( entry.getKey(),entry.getValue()))
                .forEach(money::putAll);
        return money;
    }

    public NavigableSet<Banknot> getSetBanknot() {
        return moneyBox.navigableKeySet();
    }

    public CustomInteger getCountBanknots(Banknot banknot) {
        return new CustomInteger(moneyBox.getOrDefault(banknot,0));
    }

//    public int getBalance(){
//        moneyBox.entrySet().stream().
//        return moneyBox.toString();
//    }

    public Map<Banknot, Integer> getBalanceAsMap(){
        return Map.copyOf(moneyBox);
    }
}
