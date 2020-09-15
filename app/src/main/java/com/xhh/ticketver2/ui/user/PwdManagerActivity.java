package com.xhh.ticketver2.ui.user;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.ui.adapter.user.MyTouZhuAdapter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.utils.FileUtil;

import butterknife.InjectView;
import butterknife.OnClick;

public class PwdManagerActivity extends BaseActivity implements MyTouZhuAdapter.onItemClickListenter {

    @InjectView(R.id.pwdm_pay_tv)
    TextView pwdm_pay_tv;
    @Override
    protected void initViewsAndEvents() {
        initTopBar();
        mTopbarTitleTv.setText("密码设置");
        if (TextUtils.isEmpty(FileUtil.getString(mContext,FileUtil.PRE_FILE_SAFETYPASSRORD))){
            pwdm_pay_tv.setText("设置支付密码");
        }else{
            pwdm_pay_tv.setText("重置支付密码");
        }
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
        return R.layout.activity_pwdmanager;
    }

    @OnClick({R.id.pwdm_pay_ll,R.id.pwdm_login_ll})
    public void onclick(View v){
        Bundle bundle = new Bundle();
        switch (v.getId()){
            case R.id.pwdm_pay_ll:
                if (TextUtils.isEmpty(FileUtil.getString(mContext,FileUtil.PRE_FILE_SAFETYPASSRORD))){
                    bundle.putString("flag","payset");
                }else{
                    bundle.putString("flag","pay");
                }
                readyGo(PwdResetActivity.class,bundle);
                finish();
                break;
            case R.id.pwdm_login_ll:
                bundle.putString("flag","login");
                readyGo(PwdResetActivity.class,bundle);
                finish();
                break;
        }
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
