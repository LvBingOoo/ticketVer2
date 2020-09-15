package com.xhh.ticketver2.presenter;

import android.content.Context;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.view.UserView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class UserPresenter implements BaseLoadedListener<Object> {

    private UserView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String postGetInfo = "postGetInfo";
    public String postGetInfoFromId = "postGetInfoFromId";
    public UserPresenter(UserView view, Context context){
        mContext = context;
        mView = view;
        mInteractor = new CommInteractor(this);
    }
    public void postGetInfo(){
        Map<String,String> params = new HashMap<>();
//        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postGetInfo, ApiH.URL_USER_GETINFO,params);
    }
    public void postGetInfoFromId(String userId){
        Map<String,String> params = new HashMap<>();
//        mView.showLoadingDialog(ApiH.LOADING);
        params.put("userId",userId);
        mInteractor.post(postGetInfoFromId, ApiH.URL_USER_GETUSERINFO,params);
    }

    @Override
    public void onSuccess(String event_tag, Object data) {
//        mView.hideLoadingDialog();
        if (postGetInfo.equals(event_tag)){
            mView.showGetUserInfoSuccess(JSonParamUtil.paramGetInfo(data.toString()));
        }else if (postGetInfoFromId.equals(event_tag)){
            mView.showGetUserInfoSuccess(JSonParamUtil.paramGetInfo(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
        mView.hideLoadingDialog();
        mView.showToast(ApiH.NETWORK_ERROR);
    }
}
