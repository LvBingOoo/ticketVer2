package com.cc.util.yc;

import android.app.NotificationManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.telephony.TelephonyManager;
import android.view.WindowManager;

public class SystemServiceUtils {
	
	public static NotificationManager notificationManager = null;
	
	public static TelephonyManager telephonyManager = null;
	
	public static ConnectivityManager connectivityManager = null;
	
	public static WindowManager windowManager = null;
	
	/**
	 * 取得通知管理器服务
	 * @param context
	 * @return
	 */
	public static NotificationManager getNotificationManager(Context context){
		if(context!=null && notificationManager==null){
			notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		}
		return notificationManager;
	}
	/**
	 * 取得电话管理器服务
	 * @param context
	 * @return
	 */
	public static TelephonyManager getTelephonyManager(Context context){
		if(context!=null && telephonyManager==null){
			telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		}
		return telephonyManager;
	}
	/**
	 * 网络连接的服务
	 * @param context
	 * @return
	 */
	public static ConnectivityManager getConnectivityManager(Context context){
		if(context!=null && connectivityManager==null){
			connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		}
		return connectivityManager;
	}
	/**
	 * 窗口管理器服务
	 * @param context
	 * @return
	 */
	public static WindowManager getWindowManager(Context context){
		if(context!=null && windowManager==null){
			windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		}
		return windowManager;
	}
	
}
