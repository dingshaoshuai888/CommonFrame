package com.retrofit.demo.util;

import android.util.Log;

import com.retrofit.demo.BuildConfig;

public class MLog {

    private static String className;//类名
    private static String methodName;//方法名
    private static int lineNumber;//行数

    /**
     * 判断是否可以调试
     *
     * @return
     */
    public static boolean isDebuggable() {
        return BuildConfig.DEBUG;
    }

    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("===");
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")===:");
        buffer.append(log);
        return buffer.toString();
    }

    /**
     * 获取文件名、方法名、所在行数
     *
     * @param sElements
     */
    private static void updateMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    public static void e(String tag, String message) {
        if (!isDebuggable())
            return;
        updateMethodNames(new Throwable().getStackTrace());
        Log.e(tag, createLog(message));
    }

    public static void i(String tag, String message) {
        if (!isDebuggable())
            return;
        updateMethodNames(new Throwable().getStackTrace());
        Log.i(tag, createLog(message));
    }

    public static void d(String tag, String message) {
        if (!isDebuggable())
            return;
        updateMethodNames(new Throwable().getStackTrace());
        Log.d(tag, createLog(message));
    }

    public static void v(String tag, String message) {
        if (!isDebuggable())
            return;
        updateMethodNames(new Throwable().getStackTrace());
        Log.v(tag, createLog(message));
    }

    public static void w(String tag, String message) {
        if (!isDebuggable())
            return;
        updateMethodNames(new Throwable().getStackTrace());
        Log.w(tag, createLog(message));
    }
}
