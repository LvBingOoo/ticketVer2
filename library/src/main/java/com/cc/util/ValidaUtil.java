/**
 * 
 */
package com.cc.util;

import android.content.Context;
import android.content.pm.Signature;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidaUtil {
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][2,3,4,5,6,7,8,9][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }
    /**
     * 校验银行卡卡号
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
    		 char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
    		 if(bit == 'N'){
    			 return false;
    		 }
    		 return cardId.charAt(cardId.length() - 1) == bit;
    }
   
    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId){
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0
                || !nonCheckCodeCardId.matches("\\d+")) {
        	//如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;           
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }
    public static boolean checkSignature(Context context)
    {
        try
        {
            int length = 64;
            Signature[] arrayOfSignature = context.getPackageManager().getPackageInfo(context.getPackageName(), length).signatures;     //获得签名数组
            if (arrayOfSignature != null)
            {
                if (arrayOfSignature.length == 0) {
                    return false;
                }
                int j = arrayOfSignature.length;
                int i = 0;
                while (i < j)   //如果数组中的某个元素值与 'ac6fc3fe' 相等，返回校验成功；如果直到结束也没有相等的元素，返回失败
                {               //只比较一个特定的元素，可能也是为了不把整个签名泄露出来，同时也做到了一定程度的校验
                    String str = Integer.toHexString(arrayOfSignature[i].toCharsString().hashCode());
                    if (!"c5406cbc".equalsIgnoreCase(str))
                    {
                        boolean bool = "54135b73".equalsIgnoreCase(str);
//                        boolean bool = "6b96a70b".equalsIgnoreCase(str);
                        if (bool) {
                            return  true;
                        }
                    }
                    else
                    {
                        return true;
                    }
                    i += 1;
                }
            }
            return false;
        }
        catch (Exception localException) {}
        return  false;
    }
}
