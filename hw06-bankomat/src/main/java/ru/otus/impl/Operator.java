package ru.otus.impl;

import ru.otus.Banknot;
import ru.otus.CustomInteger;
import ru.otus.interfaces.IContainer;
import ru.otus.interfaces.IOperator;

import java.util.HashMap;
import java.util.Map;

public class Operator implements IOperator {
    private final IContainer container;

    public Operator(IContainer container){
        this.container = container;
    }

    public void receive(Map<Banknot, CustomInteger> mapOfBanknots){
        mapOfBanknots.forEach(container::put);
    }

    public Map<Banknot, CustomInteger> give(Integer sum){
        var calculatedBanknots = calculate(new CustomInteger(sum));
        return container.extract(calculatedBanknots);
    }

    private Map<Banknot, CustomInteger> calculate(CustomInteger sum){
        // получить список купюр
        var sortedListNominal = container.getSetBanknot();
        Map<Banknot, CustomInteger> mapForExtract = new HashMap<>();

        var tmpSum = 0;
        for(var nominal: sortedListNominal){
           var countBanknot = (sum.getValue() - tmpSum)/nominal.getValue();
           if(countBanknot >0 ){
               int allBanknots = container.getCountBanknots(nominal).getValue();
               int countBanknotForGive = Math.min(allBanknots, countBanknot);
               if(countBanknotForGive > 0){
                   mapForExtract.put(nominal, new CustomInteger(countBanknotForGive));
                   tmpSum += countBanknotForGive * nominal.getValue();
                   if(tmpSum == sum.getValue()){
                       break;
                   }
               }
           }
        }

        if (tmpSum != sum.getValue()){
            throw new RuntimeException(String.format("Can't give requested amount of money"));
        }

        return mapForExtract;
    }
}
