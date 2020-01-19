package com.mytechnology.common_model.bean;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-16 17:13
 **/
public class Test {
    public static void main(String[] args) {
        int n = 5;
        for(int i = 0;i<n;i++){
            for(int k=0;k<n-i;k++){
                System.out.print(" ");
            }
            for(int j = 0 ;j<=i ;j++){
                System.out.print("*");
            }
            System.out.println(" ");
        }
    }
}
