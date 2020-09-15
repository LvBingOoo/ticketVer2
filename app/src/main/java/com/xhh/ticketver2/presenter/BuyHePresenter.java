package com.xhh.ticketver2.presenter;

import android.content.Context;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.view.BuyHeView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class BuyHePresenter implements BaseLoadedListener<Object> {

    private BuyHeView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String postGetPtorders = "postGetPtorders";
    public String postGetSchemeDetail = "postGetSchemeDetail";
    public String postBetPartnership = "postBetPartnership";
    public BuyHePresenter(BuyHeView view, Context context){
        mContext = context;
        mView = view;
        mInteractor = new CommInteractor(this);
    }
    public void postGetPtorders(int pageIndex){
        Map<String,String> params = new HashMap<>();
//        mView.showLoadingDialog(ApiH.LOADING);
        params.put("pageSize","20");
        params.put("pageIndex",pageIndex + "");
        mInteractor.post(postGetPtorders, ApiH.URL_LOTTERY_GETPTORDERS,params);
    }
    public void postGetSchemeDetail(String schemeId){
        Map<String,String> params = new HashMap<>();
        mView.showLoadingDialog(ApiH.LOADING);
        params.put("schemeId",schemeId);
        mInteractor.post(postGetSchemeDetail, ApiH.URL_LOTTERY_GETSCHEMEDETAIL,params);
    }

    public void postBetPartnership(String schemeId,String num){
        Map<String,String> params = new HashMap<>();
        mView.showLoadingDialog(ApiH.LOADING);
        params.put("platform","3");
        params.put("schemeId",schemeId);
        params.put("num",num);
        mInteractor.post(postBetPartnership, ApiH.URL_LOTTERY_BETPARTNERSHIP,params);
    }

    @Override
    public void onSuccess(String event_tag, Object data) {
        mView.hideLoadingDialog();
        if (postGetPtorders.equals(event_tag)){
            mView.pullRefreshOver();
            mView.showGetHeListSuccess(JSonParamUtil.paramBuyHeList(data.toString()));
        }else if (postGetSchemeDetail.equals(event_tag)){
            mView.showGetSchemeDetailSuccess(JSonParamUtil.paraHeBuyDetail(data.toString()));
        }else if (postBetPartnership.equals(event_tag)){
            mView.showBetPartnershipSuccess(JSonParamUtil.paramComm(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
        mView.pullRefreshOver();
        mView.hideLoadingDialog();
        mView.showToast(ApiH.NETWORK_ERROR);
    }
}
