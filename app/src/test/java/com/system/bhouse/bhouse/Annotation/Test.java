package com.system.bhouse.bhouse.Annotation;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2018-07-05.
 * <p>
 * by author wz
 * <p>
 * com.system.bhouse.bhouse.Annotation
 */

public class Test {
    @org.junit.Test
    public void test1(){
        Method[] declaredMethods = AnnotationTest.class.getDeclaredMethods();
        for (Method m:declaredMethods)
        {
            GET annotation = m.getAnnotation(GET.class);
            System.out.println(annotation.value());
        }
    }
}
