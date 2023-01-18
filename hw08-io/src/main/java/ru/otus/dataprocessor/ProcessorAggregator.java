package ru.otus.dataprocessor;

import ru.otus.model.Measurement;

import java.util.*;
import java.util.stream.Collectors;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        try {
            var resultMap =  data.stream()
                    .collect(Collectors.groupingBy(Measurement::getName
                            , Collectors.summingDouble(Measurement::getValue)));

            return new TreeMap<String, Double>(resultMap);
        }catch (Exception ex){
            throw new FileProcessException(ex);
        }
    }
}
