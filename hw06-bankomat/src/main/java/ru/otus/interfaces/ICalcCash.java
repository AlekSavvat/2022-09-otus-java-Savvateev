package ru.otus.interfaces;

import ru.otus.Banknot;


import java.util.Collection;
import java.util.Map;

public interface ICalcCash {

    void receive(Map<Banknot, Integer> mapOfBanknots);

    void receive(Collection<Banknot> banknotes);

    Map<Banknot, Integer> give(Integer sum);
}
