package ru.otus.impl;
/* первая реализация интерфейса*/
import ru.otus.Log;
import ru.otus.MyInterface;

public class FirstClass implements MyInterface {
    public void calculation(){
        System.out.println("Class 'FirstClass' method 'calculation' without param%");
    }
    @Log
    public void calculation(int param){
        System.out.printf("Class 'FirstClass' method 'calculation' Param: %d%n",param);
    }

    public void calculation(int param1, int param2){
        System.out.printf("Class 'FirstClass' method 'calculation' Params: %d %d%n",param1, param2);
    }

    @Log
    public void calculation(int param1, int param2, int param3){
        System.out.printf("Class 'FirstClass' method 'calculation'. Params: %d %d %d%n",param1, param2, param3);
    }
}
