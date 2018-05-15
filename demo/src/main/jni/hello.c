//
// Created by lrk on 5/4/17.
//
#include <jni.h>
#include <android/log.h>

jstring Java_com_elvis_android_hook_demo_example_TargetClass_nativeMethod(JNIEnv *env, jclass clazz) {
    return (*env)->NewStringUTF(env, "hello from JNI");
}