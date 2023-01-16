package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.*;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        final Map<String,Double> result_map = new TreeMap<String,Double>();

        for(Measurement item: data){
            result_map.put(item.getName()
                    , result_map.getOrDefault(item.getName(),0D) + item.getValue());
        }

        return result_map;
    }
}
