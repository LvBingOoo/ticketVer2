package com.xhh.ticketver2.presenter;

import android.content.Context;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.view.DomainView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class DomainPresenter implements BaseLoadedListener<Object> {

    private DomainView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String postGetDomain = "postGetDomain";
    public DomainPresenter(DomainView view, Context context){
        mContext = context;
        mView = view;
        mInteractor = new CommInteractor(this);
    }
    public void postGetDomain(String id){
        Map<String,String> params = new HashMap<>();
//        mView.showLoadingDialog(ApiH.LOADING);
        params.put("id",id);
//        mInteractor.post(postGetDomain, ApiH.URL_GET_DOMAIN,params);
    }
    @Override
    public void onSuccess(String event_tag, Object data) {
//        mView.hideLoadingDialog();
        if (postGetDomain.equals(event_tag)){
            mView.showDomainSuccess(JSonParamUtil.paramCommForDomain(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
//        mView.hideLoadingDialog();
//        mView.showToast(ApiH.NETWORK_ERROR);
        mView.showDomainFath();
    }
}
