package com.xhh.ticketver2.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.util.Log;

import net.bither.util.NativeUtil;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Fan on 2014-12-19.
 */
public class BmpUtil {

    /**
     *  对图片压缩处理
     * @param bmp 压缩的图片
     * @param localFile 压缩完成后保存到本地的图片
     */
    public static Bitmap compressBitmap(Bitmap bmp, String localFile){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int options = 80;//个人喜欢从80开始,
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        while (baos.toByteArray().length / 1024 > 300) {
            baos.reset();
            options -= 10;
            bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        //-----------------------------return bitmap----------------------------------
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片

        File file = new File(localFile);

        if(file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(  new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }
    public static Bitmap compressToBmp(String srcPath, float with){
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = with;//这里设置高度为800f
        float ww = with;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        try {
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        }catch (OutOfMemoryError outOfMemoryError){
            outOfMemoryError.printStackTrace();
            newOpts.inSampleSize = be + 4;
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        }catch (Exception e){
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String compressByScale(String srcPath){
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空

        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
        float hh = 800f;//这里设置高度为800f
        float ww = 480f;//这里设置宽度为480f
        //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        try {
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        }catch (OutOfMemoryError outOfMemoryError){
            outOfMemoryError.printStackTrace();
            newOpts.inSampleSize = be + 4;
            bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
        }catch (Exception e){
            e.printStackTrace();
        }
        return compress2(bitmap);
    }
    public static String compressByScale(String srcPath, int with){
    	BitmapFactory.Options newOpts = new BitmapFactory.Options();
    	//开始读入图片，此时把options.inJustDecodeBounds 设回true了
    	newOpts.inJustDecodeBounds = true;
    	Bitmap bitmap = BitmapFactory.decodeFile(srcPath,newOpts);//此时返回bm为空
    	
    	newOpts.inJustDecodeBounds = false;
    	int w = newOpts.outWidth;
    	int h = newOpts.outHeight;
    	//现在主流手机比较多是800*480分辨率，所以高和宽我们设置为
    	float hh = with;//这里设置高度为800f
    	float ww = with;//这里设置宽度为480f
    	//缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
    	int be = 1;//be=1表示不缩放
    	if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
    		be = (int) (newOpts.outWidth / ww);
    	} else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
    		be = (int) (newOpts.outHeight / hh);
    	}
    	if (be <= 0)
    		be = 1;
    	newOpts.inSampleSize = be;//设置缩放比例
    	//重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
    	bitmap = BitmapFactory.decodeFile(srcPath, newOpts);
    	return compress(bitmap);
    }

    /**
     * 压缩图片
     * @param bmp
     * @return
     */
    public static String compress(Bitmap bmp) {
        try {
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), System.currentTimeMillis()+ ".png");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 这里100表示不压缩，将不压缩的数据存放到baos中
            int per = 90;

            while (baos.toByteArray().length / 1024 > 1024) { // 循环判断如果压缩后图片是否大于500kb,大于继续压缩
                if(per <= 20)
                    break;
                baos.reset();// 重置baos即清空baos
                bmp.compress(Bitmap.CompressFormat.PNG, per, baos);// 将图片压缩为原来的(100-per)%，把压缩后的数据存放到baos中
                per -= 10;// 每次都减少10
            }

            f.createNewFile();
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(f);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
            Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            try {
                fOut.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 回收图片，清理内存
            if (bmp != null && !bmp.isRecycled()) {
                bmp.recycle();
                bmp = null;
                System.gc();
            }
            return f.getPath();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block 
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

	// 生成圆角图片
	@SuppressWarnings("deprecation")
	public static Bitmap GetRoundedCornerBitmap(Bitmap bitmap) {
		try {
			Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
			Canvas canvas = new Canvas(output);
			final Paint paint = new Paint();
			final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
			final RectF rectF = new RectF(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
			final float roundPx = 14;
			paint.setAntiAlias(true);
			canvas.drawARGB(0, 0, 0, 0);
			paint.setColor(Color.WHITE);
			canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
			paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

			final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

			canvas.drawBitmap(bitmap, src, rect, paint);
			return output;
		} catch (Exception e) {
			return bitmap;
		}
	}
    public static String compress2(Bitmap bmp) {
        try {
            String cu = System.currentTimeMillis() + "";
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/imgo/", "compressed" + cu
                    + ".png");
            File para = f.getParentFile();
            if (para != null && !para.exists()) {
                para.mkdirs();
            }
            int per = 90;
            NativeUtil.compressBitmap(bmp, per, f.getAbsolutePath(), true);
            if (f.exists()) {
                while (getFileSizes(f) / 1024 > 500) { // 循环判断如果压缩后图片是否大于500kb,大于继续压缩
                    Log.i("wzh", "le=" + f.length());
                    if (per <= 20)
                        break;
                    f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/imgo/", "compressed"
                            + cu + ".png");
                    NativeUtil.compressBitmap(bmp, per, f.getAbsolutePath(), true);
                    per -= 10;// 每次都减少10
                }
            }

            // 回收图片，清理内存
            if (bmp != null && !bmp.isRecycled()) {
                bmp.recycle();
                bmp = null;
                System.gc();
            }
            return f.getPath();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("resource")
    public static long getFileSizes(File f) throws Exception {// 取得文件大小
        long s = 0;
        if (f.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(f);
            s = fis.available();
        } else {
            f.createNewFile();
            System.out.println("文件不存在");
        }
        return s;
    }
}
