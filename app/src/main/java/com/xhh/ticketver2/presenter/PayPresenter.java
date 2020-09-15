package com.xhh.ticketver2.presenter;

import android.content.Context;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.view.PayView;

import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class PayPresenter implements BaseLoadedListener<Object> {

    private PayView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String tag = "tag";
    public String postAlipay = "postAlipay";
    public PayPresenter(PayView loginView, Context context){
        mContext = context;
        mView = loginView;
        mInteractor = new CommInteractor(this);
    }
    public void post( Map<String,String> params){
            mView.showLoadingDialog(ApiH.LOADING);
            mInteractor.post(tag, ApiH.URL_PAY,params);
    }

    public void postAlipay( Map<String,String> params){
        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postAlipay, ApiH.URL_PAY_ALIPAY,params);
    }
    @Override
    public void onSuccess(String event_tag, Object data) {
        mView.hideLoadingDialog();
        if (tag.equals(event_tag)){
            mView.showPaySuccess(JSonParamUtil.paramCommForOri(data.toString()));
        }else if (postAlipay.equals(event_tag)){
            mView.showAliPaySuccess(JSonParamUtil.paramCommForValue(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
        mView.hideLoadingDialog();
        mView.showToast(ApiH.NETWORK_ERROR);
    }
}
