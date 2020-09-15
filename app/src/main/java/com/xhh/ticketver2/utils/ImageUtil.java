/**
 * 
 */
package com.xhh.ticketver2.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;

/**
 * 
 *
 * @Desc: <p></p>
 */
public class ImageUtil {

    /**
     * 圆角图片
     * 
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
 
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
 
        Canvas canvas = new Canvas(output);
 
        final int color = 0xff424242;  
 
        final Paint paint = new Paint();
 
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
 
        final RectF rectF = new RectF(rect);
 
        final float roundPx = pixels;  
 
        paint.setAntiAlias(true);  
 
        canvas.drawARGB(0, 0, 0, 0);  
 
        paint.setColor(color);  
 
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);  
 
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
 
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;  
 
    }

    /**
     * 根据原图和变长绘制圆形图片
     * 
     * @param source
     * @param min
     * @return
     */
    public static Bitmap createCircleImage(Bitmap source, int min) {
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Config.ARGB_8888);
        /**
         * 产生一个同样大小的画布
         */
        Canvas canvas = new Canvas(target);
        /**
         * 首先绘制圆形
         */
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        /**
         * 使用SRC_IN
         */
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        /**
         * 绘制图片
         */
        canvas.drawBitmap(source, 0, 0, paint);
        return target;
    }

    public static int requestCode19Blow = 201;
    public static int requestCode19Above = 202;
    /**
     * 调用系统图库
     * @param context
     */
    public static void startPic(Activity context) {
        if (Build.VERSION.SDK_INT < 19) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            context.startActivityForResult(intent, requestCode19Blow);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            context.startActivityForResult(intent, requestCode19Above);
        }
    }
}
