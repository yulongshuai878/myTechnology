package com.mytechnology.common_model.bean;

/**
 * @program: main_model
 * @description:
 * @author: ShiYulong
 * @create: 2020-01-16 17:13
 **/
public class Test {
    public static void main(String[] args) {
        for(int i = 1;i <= 5;i++){
            for(int k=0;k<5-i;k++){
                System.out.print(" ");
            }
            for(int j = 1 ;j<=i ;j++){
                System.out.print("* ");
            }
            System.out.println(" ");
            if(i>0 && i<5){
                System.out.println(" ");
            }
        }
    }
}
