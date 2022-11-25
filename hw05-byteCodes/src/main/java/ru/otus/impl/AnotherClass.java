package ru.otus.impl;
/* другая реализация интерфейса*/
import ru.otus.Log;
import ru.otus.MyInterface;

public class AnotherClass implements MyInterface {
    @Log
    public void calculation(){
        System.out.println("Class 'AnotherClass' method 'calculation' without param%n");
    }

    public void calculation(int param){
        System.out.printf("Class 'AnotherClass' method 'calculation' Param: %d%n",param);
    }
    @Log
    public void calculation(int param1, int param2){
        System.out.printf("Class 'AnotherClass' method 'calculation' Params: %d %d%n",param1, param2);
    }

    public void calculation(int param1, int param2, int param3){
        System.out.printf("Class 'AnotherClass' method 'calculation'. Params: %d %d %d%n",param1, param2, param3);
    }
}
