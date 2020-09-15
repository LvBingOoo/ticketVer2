package com.xhh.ticketver2.ui.user;


import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;

import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.presenter.PwdPresenter;
import com.xhh.ticketver2.ui.adapter.user.MyTouZhuAdapter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.view.PwdView;

import butterknife.InjectView;
import butterknife.OnClick;

public class PwdResetActivity extends BaseActivity implements MyTouZhuAdapter.onItemClickListenter ,PwdView{

    @InjectView(R.id.pwdset_old_pwd)
    EditText pwdset_old_pwd;
    @InjectView(R.id.pwdset_new_pwd)
    EditText pwdset_new_pwd;
    @InjectView(R.id.pwdset_new2_pwd)
    EditText pwdset_new2_pwd;
    @InjectView(R.id.pwdset_old_ll)
    View pwdset_old_ll;
    String flag;

    PwdPresenter pwdPresenter;
    @Override
    protected void initViewsAndEvents() {
        initTopBar();
        pwdPresenter = new PwdPresenter(this,mContext);
        if ("login".equals(flag)){
            mTopbarTitleTv.setText("重置登录密码");
            pwdset_old_ll.setVisibility(View.VISIBLE);
        }else if ("pay".equals(flag)){
            mTopbarTitleTv.setText("重置支付密码");
            pwdset_old_ll.setVisibility(View.VISIBLE);
            pwdset_old_pwd.setHint("6位数字");
            pwdset_new_pwd.setHint("6位数字");
            pwdset_new2_pwd.setHint("6位数字");
            pwdset_old_pwd.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
            pwdset_new_pwd.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
            pwdset_new2_pwd.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
        }else if ("payset".equals(flag)){
            mTopbarTitleTv.setText("设置支付密码");
            pwdset_old_ll.setVisibility(View.GONE);
            pwdset_old_pwd.setHint("6位数字");
            pwdset_new_pwd.setHint("6位数字");
            pwdset_new2_pwd.setHint("6位数字");
            pwdset_old_pwd.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
            pwdset_new_pwd.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
            pwdset_new2_pwd.setFilters(new InputFilter[] { new InputFilter.LengthFilter(6) });
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
        flag = extras.getString("flag");
    }

    @OnClick(R.id.bt_ok)
    public void onclick(View v){
        String oldPwd = pwdset_old_pwd.getText().toString();
        String newPwd = pwdset_new_pwd.getText().toString();
        String new2Pwd = pwdset_new2_pwd.getText().toString();
        if ("login".equals(flag)){
            pwdPresenter.postUpdatePwd(oldPwd,newPwd,new2Pwd);
        }else if ("pay".equals(flag) || "payset".equals(flag)){
            pwdPresenter.postUpdatePayPwd(oldPwd,newPwd,new2Pwd);
        }
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_pwdset;
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

    @Override
    public void showUpdatePwdSuccess(CommEntry entry) {
        if (entry.status == Const.STATUS_SUCCESS){
            showToast("修改成功");
            finish();
        }else{
            showToast(entry.msg);
        }
    }

    @Override
    public void showUpdatePayPwdSuccess(CommEntry entry) {
        if (entry.status == Const.STATUS_SUCCESS){
            showToast("修改成功");
            finish();
        }else{
            showToast(entry.msg);
        }
    }
}
