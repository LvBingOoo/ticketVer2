/**
 * 
 */
package com.cc.util.code;

import java.security.MessageDigest;

/**
 * 
 *
 * @Desc: <p></p>
 */
public class Md5Utils {

    /**
     * 功能简述: 测试MD5单向加密.
     * 
     * @throws Exception
     */
    public static String mCodeMd5(String content) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance("md5");
        byte[] cipherData = md5.digest(content.getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte cipher : cipherData) {
            String toHexStr = Integer.toHexString(cipher & 0xff);
            builder.append(toHexStr.length() == 1 ? "0" + toHexStr : toHexStr);
        }
        //System.out.println("md5加密：" + builder.toString());
        return builder.toString();
        // c0bb4f54f1d8b14caf6fe1069e5f93ad
    }
}
