package com.xhh.ticketver2.presenter;

import android.content.Context;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.view.RongView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class RongPresenter implements BaseLoadedListener<Object> {

    private RongView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String postGetRongToken = "postGetRongToken";
    public String postGetRongAddGroup = "postGetRongAddGroup";
    public RongPresenter(RongView view, Context context){
        mContext = context;
        mView = view;
        mInteractor = new CommInteractor(this);
    }
    public void postGetRongToken(){
        Map<String,String> params = new HashMap<>();
        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postGetRongToken, ApiH.URL_CHAT_GETTOKEN,params);
    }
    public void postGetRongAddGroup(){
        Map<String,String> params = new HashMap<>();
//        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postGetRongAddGroup, ApiH.URL_CHAT_JOIN,params);
    }
    @Override
    public void onSuccess(String event_tag, Object data) {
//        mView.hideLoadingDialog();
        if (postGetRongToken.equals(event_tag)){
            mView.showGetRongTokenSuccess(JSonParamUtil.paramRongToken(data.toString()));
        }else if (postGetRongAddGroup.equals(event_tag)){
            mView.showAddRongSuccess(JSonParamUtil.paramComm(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
        mView.hideLoadingDialog();
        mView.showToast(ApiH.NETWORK_ERROR);
    }
}
