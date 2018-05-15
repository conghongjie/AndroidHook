package com.elvis.android.hook.demo.example;

import android.app.Activity;
import android.app.Instrumentation;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.elvis.android.hook.demo.MainApplication;
import com.elvis.android.hook.java.HookUtil;
import com.elvis.android.hook.java.JavaHook;

/**
 * Created by conghongjie on 2018/5/15.
 */

public class HookClass {


    public static void startHook() {
        JavaHook.hookMethod(
                HookUtil.getMethod(TargetClass.class, "normalMethod", String.class),
                HookUtil.getMethod(HookClass.class, "normalMethod_hook", Object.class, String.class),
                HookUtil.getMethod(HookClass.class, "normalMethod_back", Object.class, String.class)
        );
        JavaHook.hookMethod(
                HookUtil.getMethod(TargetClass.class, "staticMethod", String.class),
                HookUtil.getMethod(HookClass.class, "staticMethod_hook", String.class),
                HookUtil.getMethod(HookClass.class, "staticMethod_back", String.class)
        );
        JavaHook.hookMethod(
                HookUtil.getMethod(TargetClass.class, "nativeMethod"),
                HookUtil.getMethod(HookClass.class, "nativeMethod_hook"),
                HookUtil.getMethod(HookClass.class, "nativeMethod_back")
        );
        JavaHook.hookMethod(
                HookUtil.getMethod(SQLiteDatabase.class, "openDatabase", String.class, SQLiteDatabase.CursorFactory.class, int.class, DatabaseErrorHandler.class),
                HookUtil.getMethod(HookClass.class, "openDatabase_hook", String.class, SQLiteDatabase.CursorFactory.class, int.class, DatabaseErrorHandler.class),
                HookUtil.getMethod(HookClass.class, "openDatabase_back", String.class, SQLiteDatabase.CursorFactory.class, int.class, DatabaseErrorHandler.class)
        );
        if (JavaHook.SUPPORT_MIN_VERSION>=-1 && JavaHook.SUPPORT_MIN_VERSION<=99){
            JavaHook.hookMethod(
                    HookUtil.getMethod(Instrumentation.class, "callActivityOnCreate", Activity.class, Bundle.class),
                    HookUtil.getMethod(HookClass.class, "callActivityOnCreate_hook", Object.class, Activity.class, Bundle.class),
                    HookUtil.getMethod(HookClass.class, "callActivityOnCreate_back", Object.class, Activity.class, Bundle.class)
            );
        }
    }




    //normalMethod
    public static String normalMethod_hook(Object thiz, String a) {
        return "normalMethod_hook";
    }
    public static String normalMethod_back(Object thiz, String a) {
        return "staticMethod_back：此代码不应该被执行到！";
    }


    // staticMethod
    public static String staticMethod_hook(String a) {
        return "staticMethod_hook";
    }
    public static String staticMethod_back(String a) {
        return "staticMethod_back：此代码不应该被执行到！";
    }


    // nativeMethod
    public static String nativeMethod_hook(){
        return "nativeMethod_hook";
    }

    public static String nativeMethod_back() {
        return "staticMethod_back：此代码不应该被执行到！";
    }


    /* 检测数据库打开的时长*/
    public static SQLiteDatabase openDatabase_hook(String path, SQLiteDatabase.CursorFactory factory, int flags, DatabaseErrorHandler errorHandler) {
        Log.e("Elvis","SQLiteDatabase.openDatabase start:"+System.currentTimeMillis());
        SQLiteDatabase result = openDatabase_back(path,factory,flags,errorHandler);
        Log.e("Elvis","SQLiteDatabase.openDatabase end:"+System.currentTimeMillis());
        return result;
    }
    public static SQLiteDatabase openDatabase_back(String path, SQLiteDatabase.CursorFactory factory, int flags, DatabaseErrorHandler errorHandler) {
        Log.d("Elvis", "SQLiteDatabase.openDatabase 这个日志出现了就爆炸了");
        return null;
    }



    /* hook Activity 函数*/
    public static void callActivityOnCreate_hook(Object thiz, Activity activity, Bundle icicle) {
        Log.e("Elvis","Instrumentation.callActivityOnCreate start:"+System.currentTimeMillis());
        callActivityOnCreate_back(thiz,activity, icicle);
        Log.e("Elvis","Instrumentation.callActivityOnCreate end:"+System.currentTimeMillis());
    }

    public static void callActivityOnCreate_back(Object thiz, Activity activity, Bundle icicle) {
        Log.d("Elvis", "Instrumentation.callActivityOnCreate 这个日志出现了就爆炸了");
    }

}
