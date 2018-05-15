package com.elvis.android.hook.java;

import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by conghongjie on 2018/5/15.
 */

public class HookUtil {


    public static Class getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            Log.e("Elvis", "ClassNotFoundException:"+Log.getStackTraceString(e));
            return null;
        }
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
