package com.elvis.android.hook.java;

/**
 * Created by conghongjie on 2018/5/15.
 */

import java.lang.reflect.Method;
/**
 * 获取Java的方法签名
 * @author conghongjie
 */
public class MethodUtil {

    public static void main(String[] args) throws Exception {

//        System.out.println(MethodUtil.getDesc(System.class));
//        System.out.println(MethodUtil.getDesc(String.class));
//        System.out.println(MethodUtil.getDesc(Integer.class));
//        System.out.println(MethodUtil.getDesc(int.class));
//
//        Method method=MethodUtil.class.getDeclaredMethod("main", String[].class);
//        System.out.println("javah -jni");
//        System.out.println(MethodUtil.getDesc(method));
//        System.out.println(MethodUtil.getType(System.class));
//        System.out.println(MethodUtil.getType(MethodUtil.class));
    }

    public static boolean isAndroid(final String vmName) {
        final String lowerVMName = vmName.toLowerCase();
        return lowerVMName.contains("dalvik") || lowerVMName.contains("lemur");
    }

    public static boolean isAndroid() {
        return isAndroid(System.getProperty("java.vm.name"));
    }

    public static String getDesc(final Method method) {
        final StringBuffer buf = new StringBuffer();
        buf.append("(");
        final Class<?>[] types = method.getParameterTypes();
        for (int i = 0; i < types.length; ++i) {
            buf.append(getDesc(types[i]));
        }
        buf.append(")");
        buf.append(getDesc(method.getReturnType()));
        return buf.toString();
    }

    public static String getDesc(final Class<?> returnType) {
        if (returnType.isPrimitive()) {
            return getPrimitiveLetter(returnType);
        }
        if (returnType.isArray()) {
            return "[" + getDesc(returnType.getComponentType());
        }
        return "L" + getType(returnType) + ";";
    }

    public static String getType(final Class<?> parameterType) {
        if (parameterType.isArray()) {
            return "[" + getDesc(parameterType.getComponentType());
        }
        if (!parameterType.isPrimitive()) {
            final String clsName = parameterType.getName();
            return clsName.replaceAll("\\.", "/");
        }
        return getPrimitiveLetter(parameterType);
    }

    public static String getPrimitiveLetter(final Class<?> type) {
        if (Integer.TYPE.equals(type)) {
            return "I";
        }
        if (Void.TYPE.equals(type)) {
            return "V";
        }
        if (Boolean.TYPE.equals(type)) {
            return "Z";
        }
        if (Character.TYPE.equals(type)) {
            return "C";
        }
        if (Byte.TYPE.equals(type)) {
            return "B";
        }
        if (Short.TYPE.equals(type)) {
            return "S";
        }
        if (Float.TYPE.equals(type)) {
            return "F";
        }
        if (Long.TYPE.equals(type)) {
            return "J";
        }
        if (Double.TYPE.equals(type)) {
            return "D";
        }
        throw new IllegalStateException("Type: " + type.getCanonicalName() + " is not a primitive type");
    }

}