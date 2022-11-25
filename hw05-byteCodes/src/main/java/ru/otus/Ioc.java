package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

class Ioc {

    private Ioc() {}

    static MyInterface createLogProxy(MyInterface clazz) {
        InvocationHandler handler = new ProxyInvocationHandler(clazz);
        return (MyInterface) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class[]{MyInterface.class}, handler);
    }

}

