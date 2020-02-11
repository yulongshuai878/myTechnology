package com.myTechnology.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-02-11 17:07
 **/
public class InheritableSon extends InheritableFather{
    public InheritableSon() {
        super();    // 调用父类的构造函数
        // InheritableSon类是否具有 Inheritable Annotation
        System.out.println("InheritableSon:"+InheritableSon.class.isAnnotationPresent(Inheritable.class));
    }
    public static void main(String[] args)
    {
        new InheritableSon();
    }
}

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@interface Inheritable{}

@Inheritable
class InheritableFather{
    public InheritableFather(){
        // InheritableBase是否具有 Inheritable Annotation
        System.out.println("InheritableFather:"+InheritableFather.class.isAnnotationPresent(Inheritable.class));
    }
}