package com.greattone.greattone.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.wanjian.cockroach.Cockroach;

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
//        Thread.setDefaultUncaughtExceptionHandler(this);
        Cockroach.install(new Cockroach.ExceptionHandler() {

// handlerException内部建议手动try{  你的异常处理逻辑  }catch(Throwable e){ } ，以防handlerException内部再次抛出异常，导致循环调用handlerException
            @Override
            public void handlerException(final Thread thread, final Throwable throwable) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.e("AndroidRuntime","--->CockroachException:"+thread+"<---",throwable);
//                            Toast.makeText(context, "Exception Happend\n" + thread + "\n" + throwable.toString(), Toast.LENGTH_SHORT).show();
//                        throw new RuntimeException("..."+(i++));
                        } catch (Throwable e) {

                        }
                    }
                });
            }
        });
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
                Looper.loop();
            }
        }.start();
    }

}
