package com.cc.util.yc;

import java.lang.reflect.Method;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.PowerManager;

public class ScreenObserver{
	private Context mContext;
	private ScreenBroadcastReceiver mScreenReceiver;
    private ScreenStateListener mScreenStateListener;
    private static Method mReflectScreenState;
    
	public ScreenObserver(Context context){
		mContext = context.getApplicationContext();
		mScreenReceiver = new ScreenBroadcastReceiver();
		try {
			mReflectScreenState = PowerManager.class.getMethod("isScreenOn", new Class[] {});
		} catch (NoSuchMethodException e) {
		}
	}
	
	/**
     * screen状态广播接收者
     *
     */
    private class ScreenBroadcastReceiver extends BroadcastReceiver{
    	@Override
    	public void onReceive(Context context, Intent intent) {
			if (Intent.ACTION_SCREEN_OFF.equals(intent.getAction())) {
				stopScreenStateUpdate();
				mScreenStateListener.onScreenOff();
			}
    	}
    }
    
	
	/**
	 * 请求screen状态更新
	 * @param listener
	 */
	public void requestScreenStateUpdate(ScreenStateListener listener) {
		mScreenStateListener = listener;
		startScreenBroadcastReceiver();
		firstGetScreenState();
	}
	
	/**
	 * 第一次请求screen状态
	 */
	private void firstGetScreenState(){
		PowerManager manager = (PowerManager) mContext
				.getSystemService(Activity.POWER_SERVICE);
		if (!isScreenOn(manager)) {
			if (mScreenStateListener != null) {
				mScreenStateListener.onScreenOff();
			}
		} 
	}
	
	/**
	 * 停止screen状态更新
	 */
	public void stopScreenStateUpdate(){
		mContext.unregisterReceiver(mScreenReceiver);
	}
	
	/**
	 * 启动screen状态广播接收器
	 */
    private void startScreenBroadcastReceiver(){
    	IntentFilter filter = new IntentFilter();
    	filter.addAction(Intent.ACTION_SCREEN_OFF);
    	mContext.registerReceiver(mScreenReceiver, filter);
    }
	
    /**
     * screen是否打开状态
     * @param pm
     * @return
     */
	private static boolean isScreenOn(PowerManager pm) {
		boolean screenState;
		try {
			screenState = (Boolean) mReflectScreenState.invoke(pm);
		} catch (Exception e) {
			screenState = false;
		}
		return screenState;
	}
	
	public interface ScreenStateListener {
		public void onScreenOff();
	}
}