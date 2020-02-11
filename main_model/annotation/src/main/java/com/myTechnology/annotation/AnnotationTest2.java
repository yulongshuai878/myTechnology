package com.myTechnology.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-02-11 20:19
 **/
public class AnnotationTest2 {
    public static void main(String[] args) throws Exception {
        Person person = new Person();
        Class<Person> c = Person.class;
        Method mSomebody = c.getMethod("somebody",new Class[]{String.class,int.class});
        //执行该方法
        mSomebody.invoke(person,new Object[]{"YLsTone",30});
        iteratorAnnotation(mSomebody);
        Method mEmpty = c.getMethod("empty",new Class[]{});
        mEmpty.invoke(person,new Object[]{});
        iteratorAnnotation(mEmpty);

    }
    public static void iteratorAnnotation(Method method) {
        // 判断 somebody() 方法是否包含MyAnnotation注解
        if(method.isAnnotationPresent(MyAnnotation.class)) {
            // 获取该方法的MyAnnotation注解实例
            MyAnnotation myAnnotation = method.getAnnotation(MyAnnotation.class);
            // 获取 myAnnotation的值，并打印出来
            String[] values = myAnnotation.value();
            for(String str : values) {
                System.out.printf(str+", ");
            }
            System.out.println();
         }
        Annotation[] annotations = method.getAnnotations();
        for(Annotation annotation: annotations) {
            System.out.println(annotation);
        }
    }
}

/**
 * Annotation在反射函数中的使用示例
 */
@Retention(RetentionPolicy.RUNTIME)
@interface MyAnnotation {
    String[] value() default "unknown";
}

/**
 * Person类，它会使用MyAnnotation注解
 */
class Person {
    /**
     * empty()方法同时被 "@Deprecated" 和 "@MyAnnotation(value={"a","b"})"所标注
     * (01) @Deprecated，意味着empty()方法，不再被建议使用
     * (02) @MyAnnotation, 意味着empty() 方法对应的MyAnnotation的value值是默认值"unknown"
     */
    @MyAnnotation
    @Deprecated
    public void empty(){
        System.out.println("empty");
    }
    /**
     * sombody() 被 @MyAnnotation(value={"girl","boy"}) 所标注，
     * @MyAnnotation(value={"girl","boy"}), 意味着MyAnnotation的value值是{"girl","boy"}
     */
    @MyAnnotation(value={"girl","boy"})
    public void somebody(String name,int age) {
        System.out.println("somebody: "+name+", "+age);
    }
}