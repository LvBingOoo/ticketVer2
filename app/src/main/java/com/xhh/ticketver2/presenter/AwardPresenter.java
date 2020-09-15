package com.xhh.ticketver2.presenter;

import android.content.Context;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.view.AwardView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class AwardPresenter implements BaseLoadedListener<Object> {

    private AwardView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String postGetNewNumbers = "postGetNewNumbers";
    public String postGetLotteryNumbers = "postGetLotteryNumbers";
    public AwardPresenter(AwardView view, Context context){
        mContext = context;
        mView = view;
        mInteractor = new CommInteractor(this);
    }
    public void postGetNewNumbers(){
        Map<String,String> params = new HashMap<>();
//        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postGetNewNumbers, ApiH.URL_NUMBER_GETNEWNUMBERS,params);
    }
    public void postGetLotteryNumbers(String lotteryType,int pageIndex){
        try {
            Map<String,String> params = new HashMap<>();
            JSONObject jo = new JSONObject();
            jo.put("lotteryTypeId",lotteryType);
            params.put("number",jo.toString());
            params.put("pageSize","20");
            params.put("pageIndex",pageIndex + "");
//        mView.showLoadingDialog(ApiH.LOADING);
            mInteractor.post(postGetLotteryNumbers, ApiH.URL_NUMBER_GETLOTTERYNUMBERS,params);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onSuccess(String event_tag, Object data) {
        mView.hideLoadingDialog();
        mView.pullRefreshOver();
        if (postGetNewNumbers.equals(event_tag)){
            mView.showGetNewNumberSuccess(JSonParamUtil.paramGetNewNumbers(data.toString()));
        }else if (postGetLotteryNumbers.equals(event_tag)){
            mView.showGetLotteryNumbersSuccess(JSonParamUtil.paramLotteryNumbers(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
        mView.hideLoadingDialog();
        mView.showToast(ApiH.NETWORK_ERROR);
        mView.pullRefreshOver();
    }
}
