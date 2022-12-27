package ru.otus.impl;

import ru.otus.Banknot;
import ru.otus.CustomInteger;
import ru.otus.interfaces.IContainer;
import ru.otus.interfaces.IOperator;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Operator implements IOperator {
    private final IContainer container;

    public Operator(IContainer container){
        this.container = container;
    }

    public void receive(Map<Banknot, Integer> mapOfBanknots){
           mapOfBanknots.entrySet().stream()
            .collect(Collectors.toMap(p->p.getKey(), t->new CustomInteger(t.getValue())))
            .forEach(container::put);
    }


    public Map<Banknot, Integer> give(Integer sum){
        // получить список доступных купюр
        var sortedListBanknot = container.getSetBanknot();
        var calculatedBanknots = calculate(new CustomInteger(sum), sortedListBanknot);
        return container.extract(calculatedBanknots).entrySet().stream()
                .collect(Collectors.toMap(p->p.getKey(), t->t.getValue().getValue()));
    }

    private Map<Banknot, CustomInteger> calculate(CustomInteger sum, Set<Banknot> sortedListBanknot){

        Map<Banknot, CustomInteger> mapForExtract = new HashMap<>();

        var tmpSum = 0;
        for(var nominal: sortedListBanknot){
           var countBanknot = (sum.getValue() - tmpSum)/nominal.getValue();
           if(countBanknot > 0 ){
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
