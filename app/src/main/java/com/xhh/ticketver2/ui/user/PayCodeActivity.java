package com.xhh.ticketver2.ui.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.SysEntry;
import com.xhh.ticketver2.presenter.SysPresenter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.view.SysView;
import com.xhh.ticketver2.utils.CommUtil;
import com.xhh.ticketver2.utils.FileUtil;

import butterknife.InjectView;

public class PayCodeActivity extends BaseActivity implements SysView{
	String alipay_qrcode;
	String weixin_qrcode;
	@InjectView(R.id.paydetail2_tv)
	TextView paydetail2_tv;
	@InjectView(R.id.paydetail2_iv)
	ImageView paydetail2_iv;
    SysPresenter sysPresenter;
	@Override
	protected boolean isApplyKitKatTranslucency() {
		return false;
	}


	@Override
	protected void getBundleExtras(Bundle extras) {

	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_paycode;
	}

	@Override
	protected void onPubEvent(EventCenter eventCenter) {

	}

	@Override
	protected void initViewsAndEvents() {
		initTopBar();
        paydetail2_tv.setVisibility(View.GONE);
        sysPresenter = new SysPresenter(this,mContext);
		alipay_qrcode = getIntent().getStringExtra("alipay_qrcode");
		weixin_qrcode = getIntent().getStringExtra("weixin_qrcode");
		String kfqq= CommUtil.getKfQq(this);
        if (TextUtils.isEmpty(kfqq)){
            sysPresenter.postSys(Const.TAG_QQ_ID);
        }else{
            initQqShow(kfqq);
        }
        if (TextUtils.isEmpty(weixin_qrcode)){
            mTopbarTitleTv.setText("支付宝二维码");
            Glide.with(mContext).load(alipay_qrcode).dontAnimate().into(paydetail2_iv);
        }else{
            mTopbarTitleTv.setText("微信二维码");
            Glide.with(mContext).load(weixin_qrcode).dontAnimate().into(paydetail2_iv);
		}

	}
    private void initQqShow(String kfqq){
        paydetail2_tv.setVisibility(View.VISIBLE);
        paydetail2_tv.setText(CommUtil.getZfbNote(this,kfqq));
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

    @Override
    public void showSysSuccess(SysEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS){
            if (Const.TAG_QQ_ID.equals(entry.setId)){
                FileUtil.saveString(mContext,FileUtil.PRE_FILE_KFQQ,entry.setContent);
                initQqShow(entry.setContent);
            }
        }
    }
}
