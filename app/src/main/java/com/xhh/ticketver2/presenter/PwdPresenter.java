package com.xhh.ticketver2.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.view.PwdView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class PwdPresenter implements BaseLoadedListener<Object> {

    private PwdView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String postUpdatePwd = "postUpdatePwd";
    public String postUpdatePayPwd = "postUpdatePayPwd";
    public PwdPresenter(PwdView view, Context context){
        mContext = context;
        mView = view;
        mInteractor = new CommInteractor(this);
    }
    public void postUpdatePwd(String oldPwd,String newPwd,String newPwd2){
        if (TextUtils.isEmpty(oldPwd)){
            mView.showToast("请输入旧密码");
        }else if (TextUtils.isEmpty(newPwd)){
            mView.showToast("请输入新密码");
        }else if (!newPwd.equals(newPwd2)){
            mView.showToast("两次输入密码不一致");
        }else{
            Map<String,String> params = new HashMap<>();
            params.put("oldPwd",oldPwd);
            params.put("newPwd",newPwd);
            mView.showLoadingDialog(ApiH.LOADING);
            mInteractor.post(postUpdatePwd, ApiH.URL_USER_UPDATEPWD,params);
        }
    }
    public void postUpdatePayPwd(String oldPwd,String newPwd,String newPwd2){
        if (TextUtils.isEmpty(newPwd)){
            mView.showToast("请输入新密码");
        }else if (!newPwd.equals(newPwd2)){
            mView.showToast("两次输入密码不一致");
        }else{
            Map<String,String> params = new HashMap<>();
            params.put("oldPwd",oldPwd);
            params.put("newPwd",newPwd);
            mView.showLoadingDialog(ApiH.LOADING);
            mInteractor.post(postUpdatePayPwd, ApiH.URL_USER_UPDATEPAYPWD,params);
        }
    }
    @Override
    public void onSuccess(String event_tag, Object data) {
        mView.hideLoadingDialog();
        if (postUpdatePwd.equals(event_tag)){
            mView.showUpdatePwdSuccess(JSonParamUtil.paramComm(data.toString()));
        }else if (postUpdatePayPwd.equals(event_tag)){
            mView.showUpdatePayPwdSuccess(JSonParamUtil.paramComm(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
        mView.hideLoadingDialog();
        mView.showToast(ApiH.NETWORK_ERROR);
    }
}
