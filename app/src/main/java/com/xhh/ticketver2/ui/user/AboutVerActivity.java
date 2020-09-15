package com.xhh.ticketver2.ui.user;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.utils.BmpUtil;
import com.xhh.ticketver2.utils.FileUtil;
import com.cc.util.yc.PhoneInfoUtils;

import butterknife.InjectView;

public class AboutVerActivity extends BaseActivity{

	@InjectView(R.id.abhout_iv)
	ImageView abhout_iv;
	@InjectView(R.id.ver_tv)
	TextView ver_tv;
	@InjectView(R.id.about_qq)
	TextView ver_qq;
	@Override
	protected boolean isApplyKitKatTranslucency() {
		return false;
	}

	@Override
	protected void getBundleExtras(Bundle extras) {

	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_about;
	}


	@Override
	protected void onPubEvent(EventCenter eventCenter) {

	}

	@Override
	protected void initViewsAndEvents() {
		initTopBar();
		mTopbarTitleTv.setText("关于我们");
		Drawable drawable = getResources().getDrawable(R.mipmap.logo);
		BitmapDrawable bd = (BitmapDrawable) drawable;
		Bitmap bmp = BmpUtil.GetRoundedCornerBitmap(bd.getBitmap());
		abhout_iv.setImageBitmap(bmp);
        String verName = PhoneInfoUtils.getVersionName(this);
        ver_tv.setText("版本号：" + verName);
        String qq = FileUtil.getString(mContext,FileUtil.PRE_FILE_KFQQ);
        if (!TextUtils.isEmpty(qq)){
            ver_qq.setText(qq);
        }
	}

	@Override
	protected void onNetworkConnected(NetUtils.NetType type) {

	}

	@Override
	protected void onNetworkDisConnected() {

	}

	@Override
	protected boolean isBindEventBusHere() {
		return false;
	}

	@Override
	protected boolean toggleOverridePendingTransition() {
		return false;
	}

	@Override
	protected TransitionMode getOverridePendingTransitionMode() {
		return null;
	}
}
