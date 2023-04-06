package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        // You code here...

        Object appConfig = instantiate(configClass);

        // пробегаемся по всем методам класса-конфигуратора
        // если метод аннотирован 'AppComponent' то фильтруем,
        // сортируем и вызываем метод
        Arrays.stream(configClass.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                .forEachOrdered(method -> processComponent(method, appConfig));
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    private Object instantiate(Class<?> configClass) {
        try {
            return configClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processComponent(Method method, Object context) {
    // проверем компонент на дубликаты и если все ок, то добавлем в список и мапу
        String name = method.getAnnotation(AppComponent.class).name();
        checkComponentWithSameName(name);

        Object component = createComponent(method, context);

        appComponents.add(component);
        appComponentsByName.put(name, component);
    }


    private void checkComponentWithSameName(String name) {
        // проверка, что нет компонента с таким же именем
        if (name == null) {
            throw new IllegalArgumentException("AppComponent name is null");
        }

        if (appComponentsByName.containsKey(name)) {
            throw new RuntimeException(
                String.format(
                    "Component with the name %s already exists: %s",
                    name, appComponentsByName.get(name)
                )
            );
        }
    }

    private Object createComponent(Method method, Object context) {
        // создаем компонент, если объект с параметрами
        try {
            Object[] params = Arrays.stream(method.getParameterTypes())
                .map(this::getAppComponent)
                .toArray();

            return method.invoke(context, params);
        } catch (Exception e) {
            throw new RuntimeException("Error during component initialization", e);
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        var components = appComponents.stream()
                .filter(componentClass::isInstance)
                .map(c -> (C) c)
                .toList();

        if (components.isEmpty()) {
            throw new RuntimeException(
                String.format("There are no components of %s",
                    componentClass.getName()));

        } else if (components.size() > 1) {
            throw new RuntimeException(
                String.format(
                    "There are %s components of the class %s: %s",
                    components.size(),
                    componentClass.getName(),
                    components
                )
            );
        }

        return components.get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        if (!appComponentsByName.containsKey(componentName)) {
            throw new RuntimeException(
                String.format("There are no components with name %s",
                    componentName));
        }

        return (C) appComponentsByName.get(componentName);
    }



}
