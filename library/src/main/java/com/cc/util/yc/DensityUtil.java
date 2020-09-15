package com.cc.util.yc;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

public class DensityUtil {
	
	private static final String TAG = DensityUtil.class.getSimpleName();
    
    // 当前屏幕的densityDpi
    private static float dmDensityDpi = 0.0f;
    private static DisplayMetrics dm;
    private static float scale = 0.0f;
 
    
    public DensityUtil(Context context) {
        // 获取当前屏幕
        dm = new DisplayMetrics();
 
        //返回当前资源对象的DispatchMetrics信息。
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        // 设置DensityDpi
        setDmDensityDpi(dm.densityDpi);
        // 密度因子
        scale = getDmDensityDpi() / 160;//等于 scale=dm.density;
        Log.i(TAG, toString());
     }
 
    
     public static float getDmDensityDpi() {
        return dmDensityDpi;
     }
 
    
     public static void setDmDensityDpi(float dmDensityDpi) {
        DensityUtil.dmDensityDpi = dmDensityDpi;
     }
 
    
     public static int dip2px(float dipValue) {
 
        return (int) (dipValue * scale + 0.5f);
 
     }
 
    
     public int px2dip(float pxValue) {
        return (int) (pxValue / scale + 0.5f);
     }
 
     @Override
     public String toString() {
        return " dmDensityDpi:" + dmDensityDpi;
     }
 
	 /**
	  * dip转像素
	  * @param context
	  * @param dip
	  * @return
	  */
	 public static int DipToPixels(Context context,int dip) {
		  final float SCALE = context.getResources().getDisplayMetrics().density;
		  float valueDips =  dip;
		  int valuePixels = (int)(valueDips * SCALE + 0.5f); 
		  return valuePixels;
	 }
 
 
	 /**
	  * 像素转dip
	  */
	 public static float PixelsToDip(Context context,int Pixels) {
		  final float SCALE = context.getResources().getDisplayMetrics().density;
		  float dips =Pixels / SCALE ;
		  return dips;
	 }
 

    /**
     * 
     * @param context
     * @return 得到屏幕高度
     */
    public static int getPhoneHeigh(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeigh = dm.heightPixels;
        return screenHeigh;
    }

    /**
     * 
     * @param context
     * @return 得到屏幕宽度
     */
    public static int getPhoneWidth(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenHeigh = dm.widthPixels;
        return screenHeigh;
    }

}