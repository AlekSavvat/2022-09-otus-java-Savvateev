package ru.otus;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import java.util.List;

public class HelloOtus {
    public static void main(String[] args) {
        List<Integer> values = Lists.newArrayList(3, null, 4, 7,
                8, null, 7);

        Iterable<Integer> filtered = Iterables.filter(values,
                Predicates.notNull());

        for (Integer i: filtered) {
            System.out.println(i);
        }
    }
}