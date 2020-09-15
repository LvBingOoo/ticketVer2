package com.xhh.ticketver2.ui;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.multidex.MultiDex;
import android.text.TextUtils;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.unionpay.tsmservice.data.Constant;
import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.ui.homechart.SealAppContext;
import com.xhh.ticketver2.utils.FileUtil;
import com.tencent.stat.StatService;
import com.xhh.ticketver2.utils.MLog;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;


public class TicketApplication extends Application {

    private static Context mContext;
    public static String TOKEN;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mContext = getApplicationContext();
        StatService.setContext(this);
        StatService.registerActivityLifecycleCallbacks(this);

        String s = sHA1(this);
        MLog.e(s);
        com.lling.photopicker.Application.setContext(mContext);
        TOKEN = FileUtil.getString(mContext, FileUtil.TOKEN);
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {
//            RongIM.init(this);
//            SealAppContext.init(this);
            initErr();
        }

        Bugly.init(getApplicationContext(), "5b7e63cb4c", false);
//        CrashReport.initCrashReport(getApplicationContext(), "5b7e63cb4c", false);
//        CrashReport.testJavaCrash();

//        UMConfigure.init(this, "", "wanhao", UMConfigure.DEVICE_TYPE_PHONE, "");
//        PlatformConfig.setWeixin("ID", "KEY");
    }

    private void initErr() {
        String domain = FileUtil.getString(mContext, FileUtil.PRE_FILE_DOMAIN);
        if (!TextUtils.isEmpty(domain)) {
//            if ("--".equals(BuildConfig.ENDPOINT_ID)) {
//                ApiH.ENDPOINT = BuildConfig.ENDPOINT;
//            } else {
//                ApiH.ENDPOINT = domain + "/";
//            }
            ApiH.initEndpoint();
        }
    }

    public static Context getAppContext() {
        return mContext;
    }

    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length() - 1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
