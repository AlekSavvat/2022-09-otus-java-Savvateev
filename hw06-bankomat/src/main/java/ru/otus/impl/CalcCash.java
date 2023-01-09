package ru.otus.impl;

import ru.otus.Banknot;
import ru.otus.interfaces.IContainer;
import ru.otus.interfaces.ICalcCash;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
// класс рассчитывает сколько именно купюр необходимо выдать/положить в Контейнер
public class CalcCash implements ICalcCash {
    private final IContainer container;

    public CalcCash(IContainer container){
        this.container = container;
    }

    public void receive(Map<Banknot, Integer> mapOfBanknots){
        mapOfBanknots.forEach(container::put);
    }

    public void receive(Collection<Banknot> banknotes) {
        Map<Banknot, Integer> nominalMap = new HashMap<>();
        banknotes.forEach(banknote -> nominalMap.put(banknote, nominalMap.getOrDefault(banknote, 0) + 1));
        receive(nominalMap);
    }

    public Map<Banknot, Integer> give(Integer sum){
        // получить список доступных купюр
        var sortedListBanknot = container.getSetBanknot();
        var calculatedBanknots = calculate(sum, sortedListBanknot);
        return container.extract(calculatedBanknots);
    }

    private Map<Banknot, Integer> calculate(Integer sum, Set<Banknot> sortedListBanknot){

        Map<Banknot, Integer> mapForExtract = new HashMap<>();

        var tmpSum = 0;
        for(var nominal: sortedListBanknot){
           var countBanknot = (sum- tmpSum)/nominal.getValue();
           if(countBanknot > 0 ){
               int allBanknots = container.getCountBanknots(nominal);
               int countBanknotForGive = Math.min(allBanknots, countBanknot);
               if(countBanknotForGive > 0){
                   mapForExtract.put(nominal,countBanknotForGive);
                   tmpSum += countBanknotForGive * nominal.getValue();
                   if(tmpSum == sum){
                       break;
                   }
               }
           }
        }

        if (tmpSum != sum){
            throw new RuntimeException(String.format("Can't give requested amount of money"));
        }

        return mapForExtract;
    }
}
