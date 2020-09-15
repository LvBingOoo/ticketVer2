package com.cc.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.widget.Toast;

public class FileHelper {

    /**
     * 20140304hp
     * 
     * @param key
     * @param value
     * @param context
     */
    public static void sharedPreferencesPutString(String key, String value, Context context) {
        SharedPreferences sp = context.getSharedPreferences("yi_hao", Context.MODE_PRIVATE);
        Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 20140304hp
     * 
     * @param key
     * @param context
     * @return
     */
    public static String sharedPreferencesGetString(String key, Context context) {
        SharedPreferences sp = context.getSharedPreferences("yi_hao", Context.MODE_PRIVATE);
        String ret = sp.getString(key, null);
        return ret;
    }

    /**
     * 20140304hp
     * 向文件中写数据 data/data
     * 
     * @param fileName 文件名字 不包含路径
     * @param message
     * @param context
     */
    public static void writeDataFromFile(String fileName, String message, Context context) {

        // newFile(fileName);
        try {
            FileOutputStream fout = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            byte[] buffer = message.getBytes();
            fout.write(buffer);
            fout.close();
            Toast.makeText(context, "写入success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, "写入fath" + e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 20140304hp
     * 向文件中读数据
     * 
     * @param fileName
     * @param context
     * @return
     */
    public static String readDataFromFile(String fileName, Context context) {
        // 判断文件是否存在
        String ret = "";
        try {
            String dir = Environment.getDataDirectory() + "/data/com.example.mybroad/files/";
            File file = new File(dir, fileName);
            if (!file.exists()) {
                Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
                return "";
            }
            FileInputStream fin = context.openFileInput(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            ret = EncodingUtils.getString(buffer, "utf-8");
            fin.close();
        } catch (Exception e) {
            Toast.makeText(context, "读取失败" + e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 20140304hp
     * 向sdcard 写文件
     * 
     * @param dir 目录
     * @param filename 文件名
     * @param content
     * @param context
     */

    public static void writeFileToSDCard(String dir, String filename, String content, Context context) {
        if (!isExternalStorageWritable()) {
			// Toast.makeText(context, "没有存储卡", Toast.LENGTH_SHORT).show();
            return;
        }
        if (dir == null) {
            dir = Environment.getExternalStorageDirectory().getPath();
        }
        File file = new File(dir, filename);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(file, true);
            fos.write(content.getBytes());
            fos.close();
			// Toast.makeText(context, "sdcard", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /**
     * 20140304hp
     * 向sdcard 读数据
     * 
     * @param dir
     * @param filename
     * @param context
     * @return
     */
    public static String readFileFromSDCard(String dir, String filename, Context context) {
        String ret = "";
        if (!isExternalStorageWritable()) {
			// Toast.makeText(context, "没有存储卡", Toast.LENGTH_SHORT).show();
            return "";
        }
        if (dir == null) {
            dir = Environment.getExternalStorageDirectory().getPath();
        }
        File file = new File(dir, filename);
        if (!file.exists()) {
			// Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
            return "";
        }
        try {
            FileInputStream fin = new FileInputStream(file);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            fin.close();
            ret = EncodingUtils.getString(buffer, "utf-8");
        } catch (Exception e) {
			// Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 复制文件
     * 
     * @param srcFile
     * @param dstFile
     * @return
     */
    public static boolean copy(String srcFile, String dstFile) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {

            File dst = new File(dstFile);
            if (!dst.getParentFile().exists()) {
                dst.getParentFile().mkdirs();
            }

            fis = new FileInputStream(srcFile);
            fos = new FileOutputStream(dstFile);

            byte[] buffer = new byte[1024];
            int len = 0;

            while ((len = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return true;
    }

    /**
     * 20140304hp
     * 判断是否存在扩展内存
     * 
     * @return
     */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }
}
