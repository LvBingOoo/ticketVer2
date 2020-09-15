package com.cc.util.yc;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class ImgEffUtil {

	public Drawable bimpToDrawable(Bitmap bmp) {
		return new BitmapDrawable(bmp);
	}
	// 获取res目录下的drawable资源
	public Bitmap getDrawableBitmap(Context context, int drawableId) {
		// 获取应用资源集管理实例
		Resources mResources = context.getResources();
		// 获取drawable资源frame，转换为 BitmapDrawable类型
		BitmapDrawable mBitmapDrawable = (BitmapDrawable) mResources.getDrawable(drawableId);
		// 获取bitmap
		Bitmap mBitmap = mBitmapDrawable.getBitmap();

		return mBitmap;
	}

	// 获取res目录下的drawable资源
	public Bitmap getResourceBitmap(Context context, int drawableId) {
		Resources mResources = context.getResources();
		Bitmap mBitmap = BitmapFactory.decodeResource(mResources, drawableId);
		return mBitmap;
	}

	// 获取assets目录下的图片资源
	public Bitmap getAssetsBitmap(Context context, String fullName) {
		// 定义Bitmap
		Bitmap mBitmap = null;

		// 获取assets资源管理实例
		AssetManager mAssetManager = context.getAssets();

		try {
			// 打开frame.png文件流
			InputStream mInputStream = mAssetManager.open(fullName);
			// 通过decodeStream方法解析文件流
			mBitmap = BitmapFactory.decodeStream(mInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return mBitmap;
	}

	// 创建Bitmap资源
	public Bitmap drawGraphics(Context context, int drawableId) {
		// 创建大小为320 x 480的ARGB_8888类型位图
		Bitmap mBitmap = Bitmap.createBitmap(320, 480, Config.ARGB_8888);
		// 把新建的位图作为画板
		Canvas mCanvas = new Canvas(mBitmap);

		// 先画一个黑屏
		mCanvas.drawColor(Color.BLACK);

		// 创建画笔,并进行设置
		Paint mPaint = new Paint();
		mPaint.setColor(Color.BLUE);
		mPaint.setStyle(Style.FILL);

		Rect mRect = new Rect(10, 10, 300, 80);
		RectF mRectF = new RectF(mRect);
		// 设置圆角半径
		float roundPx = 15;

		mPaint.setAntiAlias(true);
		mCanvas.drawRoundRect(mRectF, roundPx, roundPx, mPaint);
		mPaint.setColor(Color.GREEN);
		mCanvas.drawCircle(80, 180, 80, mPaint);

		DashPathEffect mDashPathEffect = new DashPathEffect(new float[] { 20, 20, 10, 10, 5, 5, }, 0);
		mPaint.setPathEffect(mDashPathEffect);
		Path mPath = new Path();
		mRectF.offsetTo(10, 300);
		mPath.addRect(mRectF, Direction.CW);

		mPaint.setColor(Color.RED);
		mPaint.setStrokeWidth(5);
		mPaint.setStrokeJoin(Join.ROUND);
		mPaint.setStyle(Style.STROKE);
		mCanvas.drawPath(mPath, mPaint);

		mCanvas.drawBitmap(getDrawableBitmap(context, drawableId), 160, 90, mPaint);

		return mBitmap;
	}

	// 图片圆角处理
	public Bitmap getRoundedBitmap(Bitmap mBitmap) {
		// 创建新的位图
		Bitmap bgBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Config.ARGB_8888);
		// 把创建的位图作为画板
		Canvas mCanvas = new Canvas(bgBitmap);

		Paint mPaint = new Paint();
		Rect mRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
		RectF mRectF = new RectF(mRect);
		// 设置圆角半径为20
		float roundPx = 15;
		mPaint.setAntiAlias(true);
		// 先绘制圆角矩形
		mCanvas.drawRoundRect(mRectF, roundPx, roundPx, mPaint);

		// 设置图像的叠加模式
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		// 绘制图像
		mCanvas.drawBitmap(mBitmap, mRect, mRect, mPaint);

		return bgBitmap;
	}

	// 图片灰化处理
	public Bitmap getGrayBitmap(Bitmap mBitmap) {
		Bitmap mGrayBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Config.ARGB_8888);
		Canvas mCanvas = new Canvas(mGrayBitmap);
		Paint mPaint = new Paint();

		// 创建颜色变换矩阵
		ColorMatrix mColorMatrix = new ColorMatrix();
		// 设置灰度影响范围
		mColorMatrix.setSaturation(0);
		// 创建颜色过滤矩阵
		ColorMatrixColorFilter mColorFilter = new ColorMatrixColorFilter(mColorMatrix);
		// 设置画笔的颜色过滤矩阵
		mPaint.setColorFilter(mColorFilter);
		// 使用处理后的画笔绘制图像
		mCanvas.drawBitmap(mBitmap, 0, 0, mPaint);

		return mGrayBitmap;
	}

	// 提取图像Alpha位图
	public Bitmap getAlphaBitmap(Bitmap mBitmap, int mColor) {
		// BitmapDrawable mBitmapDrawable = (BitmapDrawable)
		// mContext.getResources().getDrawable(R.drawable.enemy_infantry_ninja);
		// Bitmap mBitmap = mBitmapDrawable.getBitmap();

		// BitmapDrawable的getIntrinsicWidth（）方法，Bitmap的getWidth（）方法
		// 注意这两个方法的区别
		// Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmapDrawable.getIntrinsicWidth(),
		// mBitmapDrawable.getIntrinsicHeight(), Config.ARGB_8888);
		Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Config.ARGB_8888);

		Canvas mCanvas = new Canvas(mAlphaBitmap);
		Paint mPaint = new Paint();

		mPaint.setColor(mColor);
		// 从原位图中提取只包含alpha的位图
		Bitmap alphaBitmap = mBitmap.extractAlpha();
		// 在画布上（mAlphaBitmap）绘制alpha位图
		mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);

		return mAlphaBitmap;
	}

	// 图像缩放
	public Bitmap getScaleBitmap(Bitmap mBitmap) {
		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(0.75f, 0.75f);
		Bitmap mScaleBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, true);

		return mScaleBitmap;
	}

	// 旋转
	public Bitmap getRotatedBitmap(Bitmap mBitmap) {
		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preRotate(45);
		Bitmap mRotateBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, true);

		return mRotateBitmap;
	}

	// 倾斜 使用Matrix类preSkew或者postSkew可以对图像进行倾斜操作，它的两个参数分别为x和y坐标倾斜度，下面使用preSkew对图像进行倾斜变换，Java代码如下
	public Bitmap getScrewBitmap(Bitmap mBitmap) {
		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();

		Matrix matrix = new Matrix();
		matrix.preSkew(1.0f, 0.15f);
		Bitmap mScrewBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, true);

		return mScrewBitmap;
	}


	/**
	 * 倒影 主要是Matrix的preScale方法的使用，给它设置负数缩放比例，图像就会进行反转。然后通过设置Shader添加渐变效果
	 */
	public Bitmap getReflectedBitmap(Bitmap mBitmap) {
		int width = mBitmap.getWidth();
		int height = mBitmap.getHeight();

		Matrix matrix = new Matrix();
		// 图片缩放，x轴变为原来的1倍，y轴为-1倍,实现图片的反转
		matrix.preScale(1, -1);

		// 创建反转后的图片Bitmap对象，图片高是原图的一半。
		// Bitmap mInverseBitmap = Bitmap.createBitmap(mBitmap, 0, height/2, width, height/2, matrix, false);
		// 创建标准的Bitmap对象，宽和原图一致，高是原图的1.5倍。
		// 注意两种createBitmap的不同
		// Bitmap mReflectedBitmap = Bitmap.createBitmap(width, height*3/2, Config.ARGB_8888);

		Bitmap mInverseBitmap = Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
		Bitmap mReflectedBitmap = Bitmap.createBitmap(width, height * 2, Config.ARGB_8888);

		// 把新建的位图作为画板
		Canvas mCanvas = new Canvas(mReflectedBitmap);
		// 绘制图片
		mCanvas.drawBitmap(mBitmap, 0, 0, null);
		mCanvas.drawBitmap(mInverseBitmap, 0, height, null);

		// 添加倒影的渐变效果
		Paint mPaint = new Paint();
		Shader mShader = new LinearGradient(0, height, 0, mReflectedBitmap.getHeight(), 0x70ffffff, 0x00ffffff,
				TileMode.MIRROR);
		mPaint.setShader(mShader);
		// 设置叠加模式
		mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
		// 绘制遮罩效果
		mCanvas.drawRect(0, height, width, mReflectedBitmap.getHeight(), mPaint);

		return mReflectedBitmap;
	}

}
