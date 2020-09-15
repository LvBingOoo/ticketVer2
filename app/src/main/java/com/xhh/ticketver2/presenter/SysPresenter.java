package com.xhh.ticketver2.presenter;

import android.content.Context;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.view.SysView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class SysPresenter implements BaseLoadedListener<Object> {

    private SysView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String postSys = "postSys";
    public SysPresenter(SysView view, Context context){
        mContext = context;
        mView = view;
        mInteractor = new CommInteractor(this);
    }
    public void postSys(String id){
        Map<String,String> params = new HashMap<>();
        params.put("id",id);
        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postSys, ApiH.URL_SYS_GETSYSSET,params);
    }

    @Override
    public void onSuccess(String event_tag, Object data) {
        mView.hideLoadingDialog();
        if (postSys.equals(event_tag)){
            mView.showSysSuccess(JSonParamUtil.paramSys(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
        mView.hideLoadingDialog();
        mView.showToast(ApiH.NETWORK_ERROR);
    }
}
