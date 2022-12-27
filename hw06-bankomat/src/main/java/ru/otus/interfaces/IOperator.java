package ru.otus.interfaces;

import ru.otus.Banknot;
import ru.otus.CustomInteger;

import java.util.Map;

public interface IOperator {
    
    void receive(Map<Banknot, Integer> mapOfBanknots);

    Map<Banknot, Integer> give(Integer sum);
}
