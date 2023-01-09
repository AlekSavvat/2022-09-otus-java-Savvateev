package ru.otus.impl;

import ru.otus.Banknot;
import ru.otus.interfaces.IContainer;

import java.util.*;

public class Container implements IContainer {
    private final TreeMap<Banknot, Integer> moneyBox = new TreeMap<>(Comparator.comparing(Banknot::getValue).reversed());

    public void put(Banknot banknot, Integer count){
        if(count < 0)
            throw new IllegalArgumentException(String.format("argument can't be lower than zero: %d", count));

        moneyBox.put(banknot, getCountBanknots(banknot) + count);
    }

    private Map<Banknot, Integer> extract(Banknot banknot, Integer count) {
        if(count < 0)
            throw new IllegalArgumentException(String.format("argument can't be lower than zero: %d", count));

        if(moneyBox.getOrDefault(banknot,0) < count)
            throw  new IllegalArgumentException("Not enough banknots");

        moneyBox.compute(banknot,(k,v) -> v - count);
        return Map.of(banknot, count);
    }

    public Map<Banknot, Integer> extract(Map<Banknot, Integer> mapForExtract) {
        final Map<Banknot, Integer> money = new HashMap<>();

        mapForExtract.entrySet()
                .stream()
                .map(entry -> extract( entry.getKey(),entry.getValue()))
                .forEach(money::putAll);

        return money;
    }

    public NavigableSet<Banknot> getSetBanknot() {
        return moneyBox.navigableKeySet();
    }

    public Integer getCountBanknots(Banknot banknot) {return moneyBox.getOrDefault(banknot,0);}

    public Integer getBalance(){
        return moneyBox.entrySet()
                .stream()
                .map(x -> x.getValue()*x.getKey().getValue())
                .reduce(0,Integer::sum);
    }

    public Map<Banknot, Integer> getBalanceAsMap(){
        return Map.copyOf(moneyBox);
    }
}
