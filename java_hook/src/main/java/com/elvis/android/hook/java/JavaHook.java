package com.elvis.android.hook.java;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 *
 * JavaHook封装
 *
 * Created by conghongjie on 2018/5/14.
 */

public class JavaHook {


    public static final int SUPPORT_MIN_VERSION = 21;// Android 5.0(API 21)
    public static final int SUPPORT_MAX_VERSION = 27;// Android 8.1(API 27)
    public static final int nowSDKVersion = android.os.Build.VERSION.SDK_INT;


    static {
        System.loadLibrary("yahfa");
        init(nowSDKVersion);
    }




    public static void hookMethod(Method targetMethod, Method hookMethod, Method backMethod){
        if (nowSDKVersion<SUPPORT_MIN_VERSION || nowSDKVersion>SUPPORT_MAX_VERSION){
            return;
        }
        Class targetClass = targetMethod.getDeclaringClass();
        String methodName = targetMethod.getName();
        String methodSig = MethodUtil.getDesc(targetMethod);
        boolean isStatic = Modifier.isStatic(targetMethod.getModifiers());

        findAndBackupAndHook(targetClass, methodName, methodSig, isStatic, hookMethod, backMethod);
    }




    private static native void init(int SDK_version);
    public static native void findAndBackupAndHook(Class targetClass, String methodName, String methodSig,
                                                     boolean isStatic,
                                                     Method hook, Method backup);

}
