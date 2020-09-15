package com.xhh.ticketver2.ui.user;


import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.presenter.RegPresenter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.view.RegView;

import butterknife.InjectView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements RegView{
    @InjectView(R.id.reg_see)
    ImageView reg_see;
    @InjectView(R.id.reg_code_iv)
    ImageView reg_code_iv;
    @InjectView(R.id.reg_user)
    EditText reg_user;
    @InjectView(R.id.reg_pwd)
    EditText reg_pwd;
    @InjectView(R.id.reg_code)
    EditText reg_code;
    @InjectView(R.id.reg_qq)
    EditText reg_qq;

    RegPresenter regPresenter;
    String mCodeUUid ;
    int uuidCount = 0;
    @Override
    protected boolean isApplyKitKatTranslucency() {
        return true;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_register;
    }

    @Override
    protected void onPubEvent(EventCenter eventCenter) {

    }

    @Override
    protected void initViewsAndEvents() {
        initTopBar();
        mTopbarTitleTv.setText("账号注册");
        regPresenter = new RegPresenter(this,mContext);
        initCode();
    }
    private void initCode(){
        mCodeUUid = System.currentTimeMillis() + "" + uuidCount;
        Glide.with(mContext).load(ApiH.URL_SYS_GETREGVERCODE+ "?uuid=" + mCodeUUid).fitCenter().dontAnimate().into(reg_code_iv);
        regPresenter.postGetRegVerCode(mCodeUUid);
    }
    @OnClick({R.id.reg_see,R.id.login_ok,R.id.reg_code_iv})
    public void onclick(View v){
        switch (v.getId()){
            case R.id.reg_see:
                if (reg_see.isSelected()){
                    reg_see.setSelected(false);
                    reg_pwd.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }else{
                    reg_see.setSelected(true);
                    reg_pwd.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                break;
            case R.id.login_ok:
                regPresenter.postReg(reg_user.getText().toString(),reg_pwd.getText().toString(),reg_code.getText().toString(),mCodeUUid,reg_qq.getText().toString());
                break;
            case R.id.reg_code_iv:
                initCode();
                break;
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

    @Override
    public void showRegCodeSuccess(CommEntry entry) {

    }

    @Override
    public void showRegSuccess(CommEntry entry) {
        if (entry.status == Const.STATUS_SUCCESS){
            showToast("注册成功,请登录");
            finish();
        }else{
            showToast(entry.msg);
        }
    }
}
