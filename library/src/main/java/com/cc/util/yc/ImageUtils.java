package com.cc.util.yc;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.apache.http.util.EncodingUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

public class ImageUtils {

    public final static String CACHE = "/Android/data/u";
    public static HashMap<String, SoftReference<Bitmap>> mImageCache = new HashMap<String, SoftReference<Bitmap>>();
    public static String filepath = "";

    /**
     * 缓存网络中的图片
     * 
     * @param path 图片路径
     * @return
     * @throws Exception
     */
    public static Bitmap getImage(String path) throws Exception {
        URL uri = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();// 访问web应用，通过HttpURLConnection
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setRequestMethod("GET");
        // conn.setRequestProperty("Accept-Encoding","musixmatch");
        if (conn.getResponseCode() == 200) {
            InputStream inStream = conn.getInputStream();
            byte[] data = StreamTool.read(inStream);
            if (data == null || data.length == 0) {
                return null;
            }
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            return bitmap;
        }
        return null;
    }

    /**
     * 保存图片到本地Sdcard
     * 
     * @param bitmap
     */
    public static String savaBitmap(Bitmap bitmap, String filename, Context context) {
        String path = getSDPath() + "/dm/";
        String filepath = "";
        File tempFile = new File(path);
        if (!tempFile.exists()) {
            tempFile.mkdirs();
        }
        if (!TextUtils.isEmpty(path)) {
            filepath = path + filename;
            File file = new File(filepath);
            FileOutputStream Out = null;
            try {
                Out = new FileOutputStream(file);
                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, Out);// 把Bitmap对象解析成流
                }
                Out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (Out != null) {
                        Out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return filepath;
    }

    /**
     * 创建文件夹
     * 
     * @return filePath
     */
    public static String isExistsFilePath(Context context) {
        if (!TextUtils.isEmpty(getSDPath())) {
            filepath = getSDPath() + getCachePath(context) + "/";
            File file = new File(filepath);
            if (!file.exists()) {
                file.mkdirs();
            }
            return filepath;
        }
        return filepath;
    }

    /**
     * 获取Sdcard卡路径
     * 
     * @return SDPath
     */
    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();
        } else {
            MyLog.LogE("The cell phone has not sdcard");
        }
        return sdDir.toString();
    }

    // 从assets 文件夹中获取文件并读取数据
    public static String getFromAssets(Context context, String fileName, String ENCODING) {
        String result = "";
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            InputStream inputStream = ImageUtils.getStream(context, fileName);
            byte buf[] = new byte[1024];
            int len;
            try {
                while ((len = inputStream.read(buf)) != -1) {
                    outputStream.write(buf, 0, len);
                }
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            result = outputStream.toString(ENCODING);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 加载本地图片
     * 
     * @param url
     * @return
     */
    public static Bitmap getSDcardBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getCacheBitmap(String imgUrl) {

        Bitmap bitmap = null;
        FileInputStream fis = null;
        boolean sdcard;
        String mCachePath = Environment.getExternalStorageDirectory().getPath() + "/zdtframe/cache/pics/";
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED) && !state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
            sdcard = true;
        } else {
            sdcard = false;
        }
        try {
            String imgName = imgUrl.substring(imgUrl.lastIndexOf("/") + 1) + "_zdt";
            if (sdcard) {
                File imgFile = new File(mCachePath + imgName);
                if (!imgFile.exists()) {
                    return null;
                }
                fis = new FileInputStream(imgFile);
                byte[] bytes = StreamTool.read(fis);
                bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    fis = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bitmap;

    }

    public static String getFileData(String path) {
        StringBuffer sb = new StringBuffer();
        // 2012-11-19 下午11:59:17
        FileInputStream fin = null;
        File temp = new File(path);
        if (!temp.exists()) {
            return "";
        }
        try {
            fin = new FileInputStream(temp);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            sb.append(EncodingUtils.getString(buffer, "UTF-8"));
            fin.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 从jar包中读取资源
     * 
     * @param file
     * @return
     */
    public static InputStream getStream(Context context, String file) {
        boolean isdeubg = false;
        if (isdeubg) {
            // debug
            try {
                return context.getAssets().open(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            // run
            return ImageUtils.class.getResourceAsStream("/assets/" + file);

        }
        return null;
    }

    /**
     * 从assets中获取bitmap
     * 
     * @param image
     */
    public static Bitmap getBitmap(Context context, String image) {
        try {
            if (mImageCache.containsKey(image)) {
                SoftReference<Bitmap> softReference = mImageCache.get(image);
                Bitmap bitmap = softReference.get();
                if (null != bitmap) {
                    return bitmap;
                }
            }
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
            // BitmapFactory.Options opts = new BitmapFactory.Options();
            // opts.inJustDecodeBounds=true;
            InputStream is = getStream(context, image);
            Bitmap bmp = BitmapFactory.decodeStream(is);

            Bitmap resizeBitmap = zoomBitmap(context, bmp);
            if (null != resizeBitmap) {
                mImageCache.put(image, new SoftReference<Bitmap>(resizeBitmap));
            }

            if (is != null)
                is.close();
            return resizeBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 放大缩小图片
    public static Bitmap zoomBitmap(Context context, Bitmap bitmap) {
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            Matrix matrix = new Matrix();
            int mDensityDpi = context.getResources().getDisplayMetrics().densityDpi;

            float SCALE = (float) mDensityDpi / 160;

            if (mDensityDpi >= 320) {
                SCALE = (float) mDensityDpi / 125;
            }

            matrix.postScale(SCALE, SCALE);
            Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            return newBmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 设置布局背景
     * 
     * @param layout
     * @param image
     */
    public static void setBackground9Path(Context context, View layout, String image) {
        try {
            Drawable bg = NinePatchTool.decodeDrawableFromAsset(context, image);
            layout.setBackgroundDrawable(bg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置布局背景
     * 
     * @param context
     * @param layout
     * @param image 相对路径
     */
    public static void setBackground(Context context, View layout, String image) {
        layout.setBackgroundDrawable(getBitmapDrawable(context, image));
    }

    /**
     * 从assets中获取BitmapDrawable
     * 
     * @param image
     */
    public static BitmapDrawable getBitmapDrawable(Context context, String image) {
        return new BitmapDrawable(getBitmap(context, image));
    }

    /**
     * 动态更换view类的背景
     * 
     * @param context
     * @param idNormal
     * @param idPressed
     * @param idFocused
     * @return
     */
    public static StateListDrawable addStateDrawable(Context context, int idNormal, int idPressed, int idFocused) {
        StateListDrawable sd = new StateListDrawable();
        Drawable normal = idNormal == -1 ? null : context.getResources().getDrawable(idNormal);
        Drawable pressed = idPressed == -1 ? null : context.getResources().getDrawable(idPressed);
        Drawable focus = idFocused == -1 ? null : context.getResources().getDrawable(idFocused);
        // 注意该处的顺序，只要有一个状态与之相配，背景就会被换掉
        // 所以不要把大范围放在前面了，如果sd.addState(new[]{},normal)放在第一个的话，就没有什么效果了
        sd.addState(new int[] { android.R.attr.state_enabled, android.R.attr.state_focused }, focus);
        sd.addState(new int[] { android.R.attr.state_pressed, android.R.attr.state_enabled }, pressed);
        sd.addState(new int[] { android.R.attr.state_focused }, focus);
        sd.addState(new int[] { android.R.attr.state_pressed }, pressed);
        sd.addState(new int[] { android.R.attr.state_enabled }, normal);
        sd.addState(new int[] {}, normal);
        return sd;
    }

    // cqiang 20140610 modify begin
    /**
     * 获取apk在sdcard中存放path
     * 
     * @param gid
     * @return path
     */
    public static String getApkSDCardPath(String gid, Context context) {

        return getFileSDcardPath(context) + gid;
    }

    /**
     * 获取在sdcard中存放的文件夹path
     * 
     * @return 文件夹path
     */
    public static String getFileSDcardPath(Context context) {
        return ImageUtils.getSDPath() + getCachePath(context) + File.separator;
    }

    /**
     * 获取广告的缓存目录
     * 
     * @param context
     * @return
     */
    public static String getCachePath(Context context) {
        return CACHE + context.getPackageName().replace(".", "");
    }
    /**    * Drawable 转 bitmap    * @param drawable    * @return    */
    public static Bitmap drawable2Bitmap(Drawable drawable){
        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable)drawable).getBitmap() ;
        }else if(drawable instanceof NinePatchDrawable){
            Bitmap bitmap = Bitmap
                    .createBitmap(
                            drawable.getIntrinsicWidth(),
                            drawable.getIntrinsicHeight(),
                            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                    : Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                    drawable.getIntrinsicHeight());
            drawable.draw(canvas);
            return bitmap;
        }else{
            return null ;
        }
    }
}
