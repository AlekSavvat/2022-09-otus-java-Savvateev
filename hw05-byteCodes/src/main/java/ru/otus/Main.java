package ru.otus;

import ru.otus.impl.*;

public class Main {
    public static void main(String[] args) {
        MyInterface firstClass = Ioc.createLogProxy(new FirstClass());
        firstClass.calculation();
        firstClass.calculation(2);
        firstClass.calculation(2, 3);
        firstClass.calculation(2, 3, 4);

        System.out.println("====================");
        MyInterface secondClass = Ioc.createLogProxy(new AnotherClass());
        secondClass.calculation();
        secondClass.calculation(4);
        secondClass.calculation(4,5);
        secondClass.calculation(4,5,6);

        System.out.println("====================");
        MyInterface inheritorClass = Ioc.createLogProxy(new ClassInheritor());
        inheritorClass.calculation();
        inheritorClass.calculation(7);
        inheritorClass.calculation(7,8);
        inheritorClass.calculation(7,8,9);
    }
}