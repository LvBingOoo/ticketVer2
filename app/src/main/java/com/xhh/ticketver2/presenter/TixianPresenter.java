package com.xhh.ticketver2.presenter;

import android.content.Context;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.view.TiXianView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class TixianPresenter implements BaseLoadedListener<Object> {

    private TiXianView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String postWithdrawalaply = "postWithdrawalaply";
    public TixianPresenter(TiXianView view, Context context){
        mContext = context;
        mView = view;
        mInteractor = new CommInteractor(this);
    }
    public void postWithdrawalaply(String bankId,String money,String pwd){
        Map<String,String> params = new HashMap<>();
        params.put("bankId",bankId);
        params.put("money",money);
        params.put("pwd",pwd);
        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postWithdrawalaply, ApiH.URL_FINANCE_WITHDRAWALAPPLY,params);
    }

    @Override
    public void onSuccess(String event_tag, Object data) {
        mView.hideLoadingDialog();
        if (postWithdrawalaply.equals(event_tag)){
            mView.showTiXianSuccess(JSonParamUtil.paramComm(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
        mView.hideLoadingDialog();
        mView.showToast(ApiH.NETWORK_ERROR);
    }
}
