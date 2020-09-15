package com.xhh.ticketver2.ui.user;


import android.os.Bundle;
import android.view.View;

import com.hhhc.obsessive.library.base.BaseAppManager;
import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.ui.TicketApplication;
import com.xhh.ticketver2.ui.adapter.user.MyTouZhuAdapter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.utils.FileUtil;

import butterknife.OnClick;

public class SysSetActivity extends BaseActivity implements MyTouZhuAdapter.onItemClickListenter {

    @Override
    protected void initViewsAndEvents() {
        initTopBar();
        mTopbarTitleTv.setText("系统设置");
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
    @OnClick({R.id.sysset_about_ll,R.id.sysset_problem_ll,R.id.bt_ok})
    public void onclick(View v){
        switch (v.getId()){
            case R.id.sysset_problem_ll:
                readyGo(ProblemActivity.class);
                break;
            case R.id.sysset_about_ll:
                readyGo(AboutVerActivity.class);
                break;
            case R.id.bt_ok:
                TicketApplication.TOKEN = null;
                FileUtil.saveString(mContext,FileUtil.TOKEN,"");
                readyGo(LoginActivity.class);
                BaseAppManager.getInstance().clear();
                break;
        }
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_sysset;
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
