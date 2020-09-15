package com.xhh.ticketver2.presenter;

import android.content.Context;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.view.ChongTiView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class ChongTiPresenter implements BaseLoadedListener<Object> {

    private ChongTiView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String postGetRecharges = "postGetRecharges";
    public String postGetWithdrawals = "postGetWithdrawals";
    public String postGetBill = "postGetBill";
    public ChongTiPresenter(ChongTiView view, Context context){
        mContext = context;
        mView = view;
        mInteractor = new CommInteractor(this);
    }
    public void postGetRecharges(int pageIndex){
        Map<String,String> params = new HashMap<>();
//        mView.showLoadingDialog(ApiH.LOADING);
        params.put("pageSize","20");
        params.put("pageIndex",pageIndex + "");
        mInteractor.post(postGetRecharges, ApiH.URL_FINANCE_GETRECHARGES,params);
    }
    public void postGetWithdrawals(int pageIndex){
        Map<String,String> params = new HashMap<>();
//        mView.showLoadingDialog(ApiH.LOADING);
        params.put("pageSize","20");
        params.put("pageIndex",pageIndex + "");
        mInteractor.post(postGetWithdrawals, ApiH.URL_FINANCE_GETWITHDRAWALS,params);
    }
    public void postGetBill(int pageIndex){
        Map<String,String> params = new HashMap<>();
//        mView.showLoadingDialog(ApiH.LOADING);
        params.put("pageSize","20");
        params.put("pageIndex",pageIndex + "");
        mInteractor.post(postGetBill, ApiH.URL_FINANCE_GETACRECORDS,params);
    }

    @Override
    public void onSuccess(String event_tag, Object data) {
        mView.hideLoadingDialog();
        mView.refreshOver();
        if (postGetRecharges.equals(event_tag)){
            mView.showGetRechargesSuccess(JSonParamUtil.paramGetRecharges(data.toString()));
        }else if (postGetWithdrawals.equals(event_tag)){
            mView.showGetWithdrawalsSuccess(JSonParamUtil.paramGetTiXian(data.toString()));
        }else if (postGetBill.equals(event_tag)){
            mView.showGetBillSuccess(JSonParamUtil.paramGetBill(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
        mView.refreshOver();
        mView.hideLoadingDialog();
        mView.showToast(ApiH.NETWORK_ERROR);
    }
}
