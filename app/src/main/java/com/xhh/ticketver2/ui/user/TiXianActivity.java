package com.xhh.ticketver2.ui.user;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.BankEntry;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.UserInfoEntry;
import com.xhh.ticketver2.presenter.BankPresenter;
import com.xhh.ticketver2.presenter.TixianPresenter;
import com.xhh.ticketver2.presenter.UserPresenter;
import com.xhh.ticketver2.ui.adapter.user.MyTouZhuAdapter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.view.BankView;
import com.xhh.ticketver2.ui.view.TiXianView;
import com.xhh.ticketver2.ui.view.UserView;
import com.xhh.ticketver2.utils.CommUtil;
import com.cc.util.code.StringUtils;
import com.cc.util.pwdui.DialogWidget;
import com.cc.util.pwdui.PayPasswordView;

import butterknife.InjectView;
import butterknife.OnClick;

public class TiXianActivity extends BaseActivity implements MyTouZhuAdapter.onItemClickListenter ,BankView,UserView,TiXianView {

    @InjectView(R.id.tixian_input_et)
    EditText tixian_input_et;
    @InjectView(R.id.tixian_money_tv)
    TextView tixian_money_tv;
    @InjectView(R.id.bankname)
    TextView bankname;
    @InjectView(R.id.bankdetail)
    TextView bankdetail;
    DialogWidget mDialogWidget;
    BankPresenter bankPresenter;
    UserPresenter userPresenter;
    TixianPresenter tixianPresenter;
    BankEntry mBankEntry;
    String mMoney;
    String mSafetyPassword;
    @Override
    protected void initViewsAndEvents() {
        initTopBar();
        mTopbarTitleTv.setText("提现");
        bankPresenter = new BankPresenter(this,mContext);
        userPresenter = new UserPresenter(this,mContext);
        tixianPresenter = new TixianPresenter(this,mContext);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bankPresenter.postGetBank();
        userPresenter.postGetInfo();
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
        return R.layout.activity_tixian;
    }

    @OnClick({R.id.tixian_bank_ll,R.id.tixian_ok,R.id.tixian_all_tv})
    public void onclick(View v){
        switch (v.getId()){
            case R.id.tixian_bank_ll:
                readyGo(BankBindActivity.class);
                break;
            case R.id.tixian_ok:
                String input = tixian_input_et.getText().toString();
                if (TextUtils.isEmpty(mMoney)){
                    showToast("余额不足，暂时无法提现");
                }else if (TextUtils.isEmpty(input)){
                    showToast("请输入提现金额");
                }else if (mBankEntry == null || TextUtils.isEmpty(mBankEntry.bankcarId)){
                    showToast("请先添加银行卡");
                }else if(TextUtils.isEmpty(mSafetyPassword)){
                    new MaterialDialog.Builder(mContext).content("您还未设置过支付密码，确定设置？").positiveText("确定").negativeText("取消")
                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (which == DialogAction.POSITIVE) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("flag","payset");
                                        readyGo(PwdResetActivity.class,bundle);
                                        dialog.dismiss();
                                    } else if (which == DialogAction.NEGATIVE) {
                                        dialog.dismiss();
                                    }
                                }
                            })
                            .positiveColorRes(R.color.color_text_red)
                            .negativeColorRes(R.color.color_text_red)
                            .build().show();
                }else{
                    showPayPwd(input);
                }
                break;
            case R.id.tixian_all_tv:
                tixian_input_et.setText(mMoney);
                break;
        }
    }
    private void showPayPwd(final String money) {
        mDialogWidget = new DialogWidget(this, PayPasswordView.getInstance(PayPasswordView.FLAG_FORM_PAY, StringUtils.floatTo2(Math.abs(CommUtil.stringToFloat(money))), this,
                new PayPasswordView.OnPayListener() {
                    @Override
                    public void onCancelPay() {
                        mDialogWidget.dismiss();
                    }

                    @Override
                    public void onSurePay(String password) {
                        mDialogWidget.dismiss();
                        tixianPresenter.postWithdrawalaply(mBankEntry.bankcarId,money,password);
                    }
                }).getView());
        mDialogWidget.show();
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
            bankname.setText(mBankEntry.bankName);
            bankdetail.setText(mBankEntry.bankNumber);
        }
    }

    @Override
    public void showAddBanksSuccess(CommEntry entry) {

    }

    @Override
    public void showUpdateBanksSuccess(CommEntry entry) {

    }

    @Override
    public void showGetUserInfoSuccess(UserInfoEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS){
            mMoney = entry.availableFund;
            mSafetyPassword = entry.safetyPassword;
            tixian_money_tv.setText("可用金额" + mMoney + "元");
        }
    }

    @Override
    public void showTiXianSuccess(CommEntry entry) {
        if (entry.status == Const.STATUS_SUCCESS){
            showToast("提现申请已提交，请耐心等待");
            finish();
        }else{
            showToast(entry.msg);
        }
    }
}
