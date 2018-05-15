package com.elvis.android.hook.demo.example;

/**
 * 将被Hook的函数
 * Created by conghongjie on 2018/5/15.
 */

public class TargetClass {

    public String normalMethod(String a) {
        if (a==null){
            a = "null";
        }
        return "normalMethod: "+a;
    }


    public static String staticMethod(String a) {
        if (a==null){
            a = "null";
        }
        return "staticMethod: "+a;
    }





    static {
        System.loadLibrary("hello");
    }

    public native static String nativeMethod();



}
