package ru.otus.impl;
/* наследник первого класса*/
import ru.otus.Log;

public class ClassInheritor extends FirstClass {
    @Override
    public void calculation(int param){
        System.out.printf("Class 'ClassInheritor' method 'calculation' Param: %d%n",param);
    }

    @Log
    @Override
    public void calculation(int param1, int param2){
        System.out.printf("Class 'ClassInheritor' method 'calculation' Params: %d %d%n",param1, param2);
    }
}
