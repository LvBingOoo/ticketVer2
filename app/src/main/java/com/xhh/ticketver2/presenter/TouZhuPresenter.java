package com.xhh.ticketver2.presenter;

import android.content.Context;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.view.TouZhuView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class TouZhuPresenter implements BaseLoadedListener<Object> {

    private TouZhuView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String postGetBetorders = "postGetBetorders";
    public TouZhuPresenter(TouZhuView view, Context context){
        mContext = context;
        mView = view;
        mInteractor = new CommInteractor(this);
    }
    public void postGetBetorders(int pageSize){
        Map<String,String> params = new HashMap<>();
        params.put("pageSize","20");
        params.put("pageIndex",pageSize + "");
//      mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postGetBetorders, ApiH.URL_FINANCE_GETBETORDERS,params);
    }

    @Override
    public void onSuccess(String event_tag, Object data) {
        mView.hideLoadingDialog();
        if (postGetBetorders.equals(event_tag)){
            mView.refreshOver();
            mView.showGetBetOrdersSuccess(JSonParamUtil.paramGetBetOrders(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
        mView.refreshOver();
        mView.hideLoadingDialog();
        mView.showToast(ApiH.NETWORK_ERROR);
    }
}
