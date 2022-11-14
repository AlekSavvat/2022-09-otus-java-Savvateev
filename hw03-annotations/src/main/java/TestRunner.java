import annotations.*;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestRunner {
    private ArrayList<Method> before = new ArrayList<>();
    private ArrayList<Method> test = new ArrayList<>();
    private ArrayList<Method> after = new ArrayList<>();
    private int success = 0;
    private int fail = 0;
    private Class<?> clazz;

    public TestRunner(Class<?> clazz){
        this.clazz = clazz;
    }

    public void runner(){
        System.out.println("Class Name:" + clazz.getSimpleName());
        parser();

        for (Method testMethod: test) {
            System.out.println("----------------------------");
            execute(testMethod);
        }

        System.out.println(String.format("Total: %d, success: %d, fail: %d", test.size(), success, fail));
    }

    private void parser(){
        for(Method method: clazz.getDeclaredMethods()){
            if(method.isAnnotationPresent(Before.class)){
                before.add(method);
            }else if(method.isAnnotationPresent(Test.class)){
                test.add(method);
            }else if(method.isAnnotationPresent(After.class)){
                after.add(method);
            }
        }
    }

    private void execute( Method testMethod){
        Object instanceClass  = ReflectionHelper.instantiate(clazz);
        try{
            before.forEach( beforeMethod ->
                    ReflectionHelper.callMethod(instanceClass, beforeMethod.getName())
            );

            ReflectionHelper.callMethod(instanceClass, testMethod.getName());
            System.out.println("Test is successful: " + testMethod.getName());
            success++;
        }
        catch (Exception ex){
            System.out.println("Something wrong: " + testMethod.getName());
            System.out.println(ex.getMessage());
            fail++;
        }
        finally {
            after.forEach( afterMethod ->
                    ReflectionHelper.callMethod(instanceClass, afterMethod.getName())
            );
        }
    }
  }
