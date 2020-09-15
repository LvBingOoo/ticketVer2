package com.xhh.ticketver2.presenter;

import android.content.Context;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.view.HomeBuyView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class HomeBuyPresenter implements BaseLoadedListener<Object> {

    private HomeBuyView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String postGetLotteryType = "postGetLotteryType";
    public String postGetLotteryTypeGroups = "postGetLotteryTypeGroups";
    public String postGetBlan = "postGetBlan";
    public String postGetNewWinPurchase = "postGetNewWinPurchase";
    public HomeBuyPresenter(HomeBuyView view, Context context){
        mContext = context;
        mView = view;
        mInteractor = new CommInteractor(this);
    }
    public void postGetLotteryTypeGroups(){
        Map<String,String> params = new HashMap<>();
//        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postGetLotteryTypeGroups, ApiH.URL_LOTTERY_GETLOTTERYTYPEGROUPS,params);
    }
    public void postGetLotteryType(){
        Map<String,String> params = new HashMap<>();
        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postGetLotteryType, ApiH.URL_LOTTERY_GETLOTTERYTYPE,params);
    }
    public void postGetBlan(){
        Map<String,String> params = new HashMap<>();
//        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postGetBlan, ApiH.URL_SYS_GETBLAN,params);
    }
    public void postGetNewWinPurchase(){
        Map<String,String> params = new HashMap<>();
//        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postGetNewWinPurchase, ApiH.URL_FINANCE_GETNEWWINPURCHASE,params);
    }

    @Override
    public void onSuccess(String event_tag, Object data) {
        if (postGetLotteryTypeGroups.equals(event_tag)){
            mView.hideLoadingDialog();
            mView.showGetLotteryTypeGroupsSuccess(JSonParamUtil.paramGetLotteryTypeGroup(data.toString()));
        }else if (postGetLotteryType.equals(event_tag)){
            mView.showGetLotteryTypeSuccess(JSonParamUtil.paramGetLotteryType(data.toString()));
        }else if (postGetBlan.equals(event_tag)){
            mView.showGetBlanSuccess(JSonParamUtil.paramGetBannaer(data.toString()));
        }else if (postGetNewWinPurchase.equals(event_tag)){
            mView.showGetWinPurchaseSuccess(JSonParamUtil.paramGetNewWin(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
        mView.hideLoadingDialog();
        mView.showToast(ApiH.NETWORK_ERROR);
    }
}
