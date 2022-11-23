package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;

class ProxyInvocationHandler implements InvocationHandler {
    private final MyInterface clazz;
    private final HashSet<Method> listLogMethod = new HashSet<>();

    ProxyInvocationHandler(MyInterface clazz) {
        this.clazz = clazz;

        for (Method method : clazz.getClass().getMethods()) {
            if (method.isAnnotationPresent(Log.class))
                // т.к. invoke получает в качестве параметра method интерфейса
                // нельзя просто сравнивать его с списком аннотированных методов из класса
                listLogMethod.add(getMethodFromInterface(method));
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (listLogMethod.contains(method)){
            System.out.printf("executed method: %s, param: %s [class: %s]%n"
                    , method.getName(), Arrays.toString(args), clazz.getClass().getSimpleName());}

        return method.invoke(clazz, args);
    }

    //получаем ссылку на метод из интерфейса
    private Method getMethodFromInterface(Method method){
        try{
            return MyInterface.class.getDeclaredMethod(method.getName(),method.getParameterTypes());
        }catch (NoSuchMethodException e){
            throw new RuntimeException(e);
        }
    }

}
