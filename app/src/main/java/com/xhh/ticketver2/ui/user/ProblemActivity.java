package com.xhh.ticketver2.ui.user;


import android.os.Bundle;

import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.ui.adapter.user.MyTouZhuAdapter;
import com.xhh.ticketver2.ui.base.BaseActivity;

import butterknife.OnClick;

public class ProblemActivity extends BaseActivity implements MyTouZhuAdapter.onItemClickListenter {

    @Override
    protected void initViewsAndEvents() {
        initTopBar();
        mTopbarTitleTv.setText("常见问题");
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
        return R.layout.activity_problem;
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
    public void onItemClick(int pos) {
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
