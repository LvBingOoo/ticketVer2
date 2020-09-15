package com.xhh.ticketver2.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.TextView;

import java.util.List;

/**
 * Author:    hup
 * Date:      2016/6/22.S
 * Description:
 */
public class CommUtil {

    public static float stringToFloat(String num){
        try{
           return  Float.parseFloat(num);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public static int stringToInt(String num){
        try{
           return  Integer.parseInt(num);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public static long stringToLong(String num){
        try{
           return  Long.parseLong(num);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public static double stringToDouble(String num){
        try{
           return  Double.parseDouble(num);
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
    public static void setTextOldPriceTip(TextView tv){
       tv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );
    }

    /**
     * 根据图片需要显示的宽度 计算应该显示的图片高度（比例拉伸）
     * ImageView.ScaleType.FIT_XY
     * @return
     */
    public static int getImgShowHeight(int imgShowWidth, float bmpWidth, float bmpHeight){
        float pid = bmpWidth / imgShowWidth;
        return (int) (bmpHeight / pid);
    }
    public static  void doTel(Context context, String tel){
        if (TextUtils.isEmpty(tel)){
            return;
        }
//        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+tel));
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);//内部类
    }
    public static void startQQ(Context context , String qq){
        try{
            String url11 = "mqqwpa://im/chat?chat_type=wpa&uin="+qq+"&version=1";
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url11)));
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * 判断GPS是否开启，GPS或者AGPS开启一个就认为是开启的
     * @param context
     * @return true 表示开启
     */
    public static final boolean isOPenGPS(final Context context) {
        LocationManager locationManager
                = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//        if (gps || network) {
//            return true;
//        }else {
            return gps;
//        }
    }
    /**
     * 强制帮用户打开GPS
     * @param context
     */
    public static final void openGPS(Context context) {
        // 转到手机设置界面，用户设置GPS
        Intent intent = new Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        context.startActivity(intent); // 设置完成后返回到原来的界面
    }
    /**
     * 打开网络设置页
     * @param context
     */
    public static final void openNetwork(Context context) {
        Intent intent = new Intent();
        ComponentName component = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
        intent.setComponent(component);
        intent.setAction("android.intent.action.VIEW");
        context.startActivity(intent);
        context.startActivity(intent); // 设置完成后返回到原来的界面
    }

    /**
     * 检查当前网络是否可用
     *
     * @param context
     * @return
     */

    public static boolean isNetworkAvailable(Context context)
    {
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 是否安裝微信
     * @param context
     * @return
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    public static String getKfQq(Context context){
        String kfqq= FileUtil.getString(context,FileUtil.PRE_FILE_KFQQ);
        return  kfqq;
    }
    public static String TXT_ZFB_1="使用扫码充值时请及时与客服QQ（";
    public static String TXT_ZFB_2="）联系。";
    public static String getZfbNote(Context context,String qq){
        StringBuffer sb=new StringBuffer();
        sb.append(TXT_ZFB_1);
        sb.append(qq);
        sb.append(TXT_ZFB_2);
        return  sb.toString();
    }

    public static String getStatusText(String staus){
        if ("0".equals(staus)){
            return "进行中";
        }else if ("1".equals(staus)){
            return "待开奖";
        }else if ("2".equals(staus)){
            return "撤单";
        }else if ("3".equals(staus)){
            return "购买失败";
        }else if ("4".equals(staus)){
            return "已中奖";
        }else if ("5".equals(staus)){
            return "未中奖";
        }else{
            return "完成";
        }
    }
    public static String getCutUserName(String userName) {
        if (!TextUtils.isEmpty(userName) && userName.length() > 2) {
            int le = userName.length() - 2;
            StringBuffer sb = new StringBuffer();
            if (le > 0) {
                if (le > 3){
                    le = 3;
                }
                for (int i = 0; i < le; i++) {
                    sb.append("*");
                }
            }
            if (!TextUtils.isEmpty(sb.toString())) {
                userName = userName.substring(0, 2) + sb.toString();
            }

        }
        return userName;
    }
}
