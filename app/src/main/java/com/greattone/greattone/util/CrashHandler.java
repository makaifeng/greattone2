package com.greattone.greattone.util;

import android.content.Context;
import android.os.Looper;

/***
 * @Description: 捕获未处理的崩溃并进行相关处理的机制 --- 单例模式
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    private static CrashHandler INSTANCE = new CrashHandler();

    private Context mContext;

    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    public void init(Context context) {
        mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
//        DebugTraceTool.debugTraceE(TAG, "some uncaughtException happend");
        ex.printStackTrace();

        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                ActiivtyStack.getScreenManager().clearAllActivity();
                System.exit(0);
                Looper.loop();
            }
        }.start();
    }

}
