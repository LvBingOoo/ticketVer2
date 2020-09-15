package com.cc.util.yc;

import java.io.File;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

public class PhoneInfoUtils {

    private static final String NETWORKNAME_YD = "ChinaMobile";
    private static final String NETWORKNAME_LT = "ChinaUnicom";
    private static final String NETWORKNAME_DX = "ChinaTelecom";
    private static final String NETWORKNAME_UN = "UNKNOWN";
    private static String mNetName = "";
    private static String mMid = "";
    private static String mQid = "";
    private static String packageName = "";
    private static String appName = "";
    private static String UNKEY = "UN_KEY";

    public Context context;

    public PhoneInfoUtils(Context context) {
        this.context = context;
    }

    /**
     * 获取当前应用的包名
     * 
     * @return
     */
    public static String getPackname(Context context) {
        if (context != null && TextUtils.isEmpty(packageName)) {
            packageName = context.getPackageName();
        }
        return packageName;
    }

    /**
     * 获取当前应用的名字
     * 
     * @return
     * @throws NameNotFoundException
     */
    public String getAppname(Context context) throws NameNotFoundException {
        if (context != null && TextUtils.isEmpty(appName)) {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(getPackname(context),
                    PackageManager.GET_META_DATA);
            CharSequence appname = packageManager.getApplicationLabel(applicationInfo);
            appName = appname.toString();
        }
        return appName;
    }

    /**
     * 获取网络运营商名字：移动，联通，电信
     * 
     * @param context
     * @return
     * 
     * public static String getNetName(Context context){ if(context !=
     * null && TextUtils.isEmpty(mNetName)){ TelephonyManager tm =
     * (TelephonyManager
     * )SystemServiceUtils.getTelephonyManager(context); String
     * networkoperatorName = tm.getNetworkOperatorName(); if
     * (networkoperatorName != null) { if
     * (networkoperatorName.contains("中国联通") ||
     * networkoperatorName.contains(NETWORKNAME_LT)) { mNetName =
     * NETWORKNAME_LT; } else if (networkoperatorName.contains("中国移动")
     * || networkoperatorName.contains(NETWORKNAME_YD)) { mNetName =
     * NETWORKNAME_YD; } else if (networkoperatorName.contains("中国电信")
     * || networkoperatorName.contains(NETWORKNAME_DX)) { mNetName =
     * NETWORKNAME_DX; } else { mNetName = NETWORKNAME_DX; } } } return
     * mNetName; }
     */
    /**
     * 获取网络运营商名字：移动，联通，电信
     * 
     * @param context
     * @return
     */
    public static String getNetName(Context context) {
        if (context != null && TextUtils.isEmpty(mNetName)) {
            TelephonyManager tm = (TelephonyManager) SystemServiceUtils.getTelephonyManager(context);
            String operator = tm.getSimOperator();
            if (!TextUtils.isEmpty(operator)) {
                if (operator.equals("46000") || operator.equals("46002") || operator.equals("46007")) {
                    mNetName = NETWORKNAME_YD;
                } else if (operator.equals("46001")) {
                    mNetName = NETWORKNAME_LT;
                } else if (operator.equals("46003")) {
                    mNetName = NETWORKNAME_DX;
                } else {
                    mNetName = NETWORKNAME_UN;
                }
            } else {
                mNetName = NETWORKNAME_UN;
            }
        }
        return mNetName;
    }

    /**
     * 获取IMEI
     * 
     * @param context
     * @return
     */
    public static String getMID(Context context) {
        if (context != null && TextUtils.isEmpty(mMid)) {
            TelephonyManager tm = (TelephonyManager) SystemServiceUtils.getTelephonyManager(context);
            mMid = tm.getDeviceId();

            // If running on an emulator
            if (mMid == null || mMid.trim().length() == 0 || mMid.matches("0+")) {
                mMid = "000000000000000";
            }
        }
        return mMid;
    }

    /**
     * 文件安装
     * 
     * @param file
     * 文件路径
     * @return 意图对象
     */
    public static Intent startInstall(File file) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        return intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
    }

    /**
     * 网络连接状态
     * 
     * @return true 是已经连接;false 未连接
     */
    public static boolean isConnectInternet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) SystemServiceUtils
                .getConnectivityManager(context);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null) {
            return networkInfo.isAvailable();
        }
        return false;
    }

    /**
     * 传入包名开启安装
     * 
     * @param context
     * @param packName
     * @return
     */
    public static Intent getStartAPPIntent(Context context, String packName) {
        if (context == null || TextUtils.isEmpty(packName)) {
            return null;
        }

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return intent;
    }

    /**
     * 获取IP地址
     * 
     * @return
     */
    public String getIpAddress(Context context) {
        try {
            String permission = "android.permission.ACCESS_WIFI_STATE";
            if (existPermissions(context, permission)) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                if (wifiManager.isWifiEnabled()) {
                    int ipInt = wifiManager.getConnectionInfo().getIpAddress();
                    if (ipInt != 0) {
                        return intToIpString(ipInt);
                    }
                }
            }

            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private String intToIpString(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF) + "." + (i >> 24 & 0xFF);
    }

    /** 判断有没有指定的权限 */
    private boolean existPermissions(Context context, String permission) {
        try {
            if (context == null || permission == null) {
                return false;
            }
            String pn = context.getPackageName();
            PackageManager pm = context.getPackageManager();
            String[] ps = pm.getPackageInfo(pn, PackageManager.GET_PERMISSIONS).requestedPermissions;
            for (int i = 0; i < ps.length; i++) {
                if (permission.equals(ps[i])) {
                    return true;
                }
            }
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isMiSystem(Context context) {
        if (havePermissions(context)) {
            return true;
        }
        return TextUtils.isEmpty(Build.MANUFACTURER) ? false : Build.MANUFACTURER.toLowerCase().contains("xiaomi");
    }

    public static boolean isMeizuSystem() {
        if (!TextUtils.isEmpty(Build.MANUFACTURER)) {
            if (Build.MANUFACTURER.toLowerCase().contains("meizu")) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param context
     * @param intent
     */

    public static void mStartActivity(Context context, Intent intent) {
        try {
            PendingIntent pendi = PendingIntent.getActivity(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            // context.startIntentSender(pendi.getIntentSender(), null,Intent.FLAG_ACTIVITY_NEW_TASK,
            // Intent.FLAG_ACTIVITY_NEW_TASK, 0, null);
            context.startIntentSender(pendi.getIntentSender(), null, Intent.FLAG_ACTIVITY_NEW_TASK,
                    Intent.FLAG_ACTIVITY_NEW_TASK, 0);
        } catch (SendIntentException e) {
            context.startActivity(intent);
            e.printStackTrace();
        }
    }

    /**
     *
     * @param context
     * @param path
     */
    public static void startInstallOS(Context context, String path, Intent intent) {
        // loadLibrary(context,path); //16版本被360拦截 有来源
        mStartActivity(context, intent);
    }

    private final static String ALERT_WINDOW = "android.permission.SYSTEM_ALERT_WINDOW";

    private static boolean havePermissions(Context context) {
        PackageManager packageManager = context.getPackageManager();
        PhoneInfoUtils phoneInfo = new PhoneInfoUtils(context);
        String permission = "";
        try {
            String[] a = packageManager.getPackageInfo(phoneInfo.getPackname(context), PackageManager.GET_PERMISSIONS).requestedPermissions;
            for (int i = 0; i < a.length; i++) {
                permission = permission + a[i] + "-";
            }

            if (permission.indexOf(ALERT_WINDOW) == -1) {
                // showToast(context, "您少添加了一个权限：" + ALERT_WINDOW);
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    public static String getVersionName(Context context){
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
            return packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getVersionCode(Context context){
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
             return packInfo.versionCode+"";
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
