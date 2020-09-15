package com.xhh.ticketver2.ui;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.ImageView;

import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.presenter.DomainPresenter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.user.LoginActivity;
import com.xhh.ticketver2.ui.view.DomainView;
import com.xhh.ticketver2.utils.FileUtil;

import butterknife.InjectView;

public class StartActivity extends BaseActivity implements DomainView {

    DomainPresenter domainPresenter;

    @Override
    protected void initViewsAndEvents() {
        domainPresenter = new DomainPresenter(this, mContext);
//        domainPresenter.postGetDomain(BuildConfig.ENDPOINT_ID);
        goAc();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_start;
    }

    @Override
    protected void onPubEvent(EventCenter eventCenter) {

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
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showDomainSuccess(CommEntry entry) {
        if (entry.status == Const.STATUS_SUCCESS && !TextUtils.isEmpty(entry.json)) {
            FileUtil.saveString(mContext, FileUtil.PRE_FILE_DOMAIN, entry.json);
        }
        goAc();
    }

    @Override
    public void showDomainFath() {
        goAc();
    }

    private void goAc() {
        String domain = FileUtil.getString(mContext, FileUtil.PRE_FILE_DOMAIN);
//        if (!TextUtils.isEmpty(domain)) {
//            if ("--".equals(BuildConfig.ENDPOINT_ID)) {
//                ApiH.ENDPOINT = BuildConfig.ENDPOINT;
//            } else {
////                ApiH.ENDPOINT = domain + "/";
//                ApiH.ENDPOINT = "http://test.iddc.site:8888/";
//            }
            ApiH.initEndpoint();
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    String token = FileUtil.getString(StartActivity.this, FileUtil.TOKEN);
                    if (TextUtils.isEmpty(token)) {
                        startActivity(new Intent(StartActivity.this, LoginActivity.class));
                    } else {
                        startActivity(new Intent(StartActivity.this, MainActivity.class));
                    }
                    finish();
                }
            }, 1500);
//        } else {
////            finish();
//            showToast("初始化失败，请退出应用重新进入");
//        }
    }
}
