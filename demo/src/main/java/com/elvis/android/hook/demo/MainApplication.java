package com.elvis.android.hook.demo;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.elvis.android.hook.demo.example.HookClass;
import com.elvis.android.hook.demo.example.TargetClass;
import com.elvis.android.hook.java.JavaHook;

import java.lang.reflect.Method;

/**
 * Created by conghongjie on 2018/5/14.
 */

public class MainApplication extends Application{




    public MainApplication(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);




        HookClass.startHook();

        Log.e("Elvis",new TargetClass().normalMethod("a"));
        Log.e("Elvis",new TargetClass().staticMethod("a"));
        Log.e("Elvis",new TargetClass().nativeMethod());



    }


    public static Method getMethod(Class<?> klass, String name, Class<?>... parameterTypes) {
        try {
            return klass.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            Log.e("Elvis", Log.getStackTraceString(e));
            return null;
        }
    }










}
