package com.xhh.ticketver2.ui.user;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.BankEntry;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.presenter.BankPresenter;
import com.xhh.ticketver2.ui.adapter.user.MyTouZhuAdapter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.view.BankView;

import butterknife.InjectView;
import butterknife.OnClick;

public class BankBindActivity extends BaseActivity implements MyTouZhuAdapter.onItemClickListenter ,BankView {

    @InjectView(R.id.bankbind_accountname_et)
    EditText bankbind_accountname_et;
    @InjectView(R.id.bankbind_banknumber_et)
    EditText bankbind_banknumber_et;
    @InjectView(R.id.bankbind_bankname_et)
    EditText bankbind_bankname_et;
    @InjectView(R.id.bankbind_bankarea_et)
    EditText bankbind_bankarea_et;
    @InjectView(R.id.bt_ok)
    Button bt_ok;
    String flag;
    BankEntry mBankEntry;

    BankPresenter bankPresenter;

    @Override
    protected void initViewsAndEvents() {
        initTopBar();
        bankPresenter = new BankPresenter(this,mContext);
        if ("update".equals(flag)){
            mTopbarTitleTv.setText("银行资料");
            bt_ok.setText("保存");
        }else{
            mTopbarTitleTv.setText("绑定银行卡");
            bt_ok.setText("确定");
        }
        bankPresenter.postGetBank();
    }
    private void initBank(){
        if (mBankEntry != null){
            bankbind_accountname_et.setText(mBankEntry.accountName);
            bankbind_banknumber_et.setText(mBankEntry.bankNumber);
            bankbind_bankname_et.setText(mBankEntry.bankName);
            bankbind_bankarea_et.setText(mBankEntry.bankArea);
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
    @OnClick({R.id.bt_ok})
    public void onclick(View v){
        switch (v.getId()){
            case R.id.bt_ok:
                String accountName = bankbind_accountname_et.getText().toString();
                String bankNum = bankbind_banknumber_et.getText().toString();
                String bankName = bankbind_bankname_et.getText().toString();
                String bankArea = bankbind_bankarea_et.getText().toString();
                if (mBankEntry != null){
                    bankPresenter.postUpdateBank(mBankEntry.bankcarId,accountName,bankName,bankNum,bankArea);
                }else{
                    bankPresenter.postAddBank(accountName,bankName,bankNum,bankArea);
                }
                break;
        }
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_bankbind;
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
    public void showGetBanksSuccess(BankEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS && entry.mList != null && !entry.mList.isEmpty()){
            mBankEntry = entry.mList.get(0);
            initBank();
        }
    }

    @Override
    public void showAddBanksSuccess(CommEntry entry) {
        if (entry.status == Const.STATUS_SUCCESS){
            showToast("保存成功");
            finish();
        }else{
            showToast(entry.msg);
        }
    }

    @Override
    public void showUpdateBanksSuccess(CommEntry entry) {
        if (entry.status == Const.STATUS_SUCCESS){
            showToast("保存成功");
            finish();
        }else{
            showToast(entry.msg);
        }
    }
}
