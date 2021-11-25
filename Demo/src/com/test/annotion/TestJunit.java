package com.test.annotion;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestJunit {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, InvocationTargetException {

        Class UserTestClass = UserTest.class;

        Object userTest =  UserTestClass.newInstance();
        Method[] methods = UserTestClass.getMethods();

        List<Method> beforemethodList = new ArrayList<>();
        List<Method> aftermethodList = new ArrayList<>();
        List<Method> methodList = new ArrayList<>();

        for(Method method: methods) {

            if (method.isAnnotationPresent(Before.class)) {
                beforemethodList.add(method);
            }
            if (method.isAnnotationPresent(After.class)) {
                aftermethodList.add(method);
            }
            if (method.isAnnotationPresent(Test.class)) {
                methodList.add(method);
            }
        }


        for(Method method: methodList) {
            for(Method before: beforemethodList) {
                before.invoke(userTest,null);
            }

            method.invoke(userTest,null);

            for(Method after: aftermethodList) {
                after.invoke(userTest,null);
            }
        }
    }

}
