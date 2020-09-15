package com.xhh.ticketver2.ui.user;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.LoginEntry;
import com.xhh.ticketver2.beans.RongEntry;
import com.xhh.ticketver2.presenter.LoginPresenter;
import com.xhh.ticketver2.presenter.RongPresenter;
import com.xhh.ticketver2.ui.MainActivity;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.view.LoginView;
import com.xhh.ticketver2.ui.view.RongView;
import com.xhh.ticketver2.utils.FileUtil;

import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginView ,RongView {

    @InjectView(R.id.login_user)
    EditText login_user;
    @InjectView(R.id.login_pwd)
    EditText login_pwd;
    @InjectView(R.id.login_root)
    View login_root;

    LoginPresenter loginPresenter;
    RongPresenter rongPresenter;
    @Override
    protected boolean isApplyKitKatTranslucency() {
        return true;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    protected void onPubEvent(EventCenter eventCenter) {

    }

    @Override
    protected void initViewsAndEvents() {
        login_root.setBackgroundResource(R.mipmap.a_bg);
        loginPresenter = new LoginPresenter(this,this);
        rongPresenter = new RongPresenter(this,mContext);
        String userName = FileUtil.getString(mContext,FileUtil.USERNAME);
        if (!TextUtils.isEmpty(userName)){
            login_user.setText(userName);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @OnClick({R.id.login_ok,R.id.login_register_bt})
    public void onclick(View v){
        switch (v.getId()){
            case R.id.login_ok:
                loginPresenter.post(login_user.getText().toString(),login_pwd.getText().toString());
                break;
            case R.id.login_register_bt:
                readyGo(RegisterActivity.class);
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
    public void showLoginSuccess(LoginEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS){
            FileUtil.saveString(mContext,"userId",entry.userId);
            FileUtil.saveString(mContext,"parentId",entry.parentId);
            FileUtil.saveString(mContext,"userType",entry.userType);
//            FileUtil.saveString(mContext,FileUtil.PRE_FILE_RONG_GOURPID,entry.groupId);
//            if (!FileUtil.getBoolean(mContext,FileUtil.PRE_FILE_RONG_GOURPID_SUCCTESS)){
//                rongPresenter.postGetRongAddGroup();
//            }else{
                readyGo(MainActivity.class);
//            }
        }else{
            showToast(entry.commEntry.msg);
        }
    }

    @Override
    public void showGetRongTokenSuccess(RongEntry entry) {

    }

    @Override
    public void showAddRongSuccess(CommEntry entry) {
        if (entry.status == Const.STATUS_SUCCESS){
        }else{
            showToast(entry.msg);
        }
        readyGo(MainActivity.class);
    }
}
