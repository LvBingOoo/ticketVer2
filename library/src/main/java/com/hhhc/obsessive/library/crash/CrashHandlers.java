package com.hhhc.obsessive.library.crash;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hhhc.obsessive.library.base.BaseAppManager;

/**
 * Author:    hup
 * Date:      2017/8/22.
 * Description:
 */

public class CrashHandlers implements Thread.UncaughtExceptionHandler {
    public static final String TGA = "CrashHandlers";

    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    // CrashHandler实例
    private static CrashHandlers instance;
    // 程序的Context对象
    private Context mContext;


    /** 保证只有一个CrashHandler实例 */
    private CrashHandlers() {
    }

    /** 获取CrashHandler实例 ,单例模式 */
    public synchronized static CrashHandlers getInstance() {
        if (instance == null) {
            instance = new CrashHandlers();
        }
        return instance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
        Log.e(TGA,"init");
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(thread, ex) && mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Thread thread, Throwable ex) {
        if (ex == null) {
            return false;
        }
        String content = "thread: " + thread + "\n  name: "
                + thread.getName() + "\n id: " + thread.getId()
                + "\n exception: " + ex ;
        Log.e(TGA,content);
        Intent intent = new Intent();
        intent.setAction("android.intent.action.homeAc");
        PendingIntent restartIntent = PendingIntent.getActivity( mContext.getApplicationContext(), 0, intent,0);
        //退出程序
        AlarmManager mgr = (AlarmManager)mContext.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis(),restartIntent);
        //结束进程之前可以把你程序的注销或者退出代码放在这段代码之前
        BaseAppManager.getInstance().clear();
        android.os.Process.killProcess(android.os.Process.myPid());
        return true;
    }

}
