package com.xhh.ticketver2.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.view.RegView;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class RegPresenter implements BaseLoadedListener<Object> {

    private RegView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String postGetRegVerCode = "postGetRegVerCode";
    public String postReg = "postReg";
    public RegPresenter(RegView view, Context context){
        mContext = context;
        mView = view;
        mInteractor = new CommInteractor(this);
    }
    public void postGetRegVerCode(String uuid){
        Map<String,String> params = new HashMap<>();
        params.put("uuid",uuid);
        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.get(postGetRegVerCode, ApiH.URL_SYS_GETREGVERCODE,params);
    }
    public void postReg(String userName,String pwd,String valCode,String uuid,String qq){
        if (TextUtils.isEmpty(userName)){
            mView.showToast("请先输入帐号");
        }else if (TextUtils.isEmpty(valCode)){
            mView.showToast("请先输入验证码");
        }else if (TextUtils.isEmpty(pwd)){
            mView.showToast("请先输入密码");
        }else{
            Map<String,String> params = new HashMap<>();
            params.put("platform","3");
            params.put("regIdent",uuid);
            params.put("valCode",valCode);
            params.put("userName",userName);
            params.put("pwd",pwd);
            params.put("qq",qq);
            mView.showLoadingDialog(ApiH.LOADING);
            mInteractor.post(postReg, ApiH.URL_USER_REG,params);
        }
    }

    @Override
    public void onSuccess(String event_tag, Object data) {
        mView.hideLoadingDialog();
        if (postGetRegVerCode.equals(event_tag)){
            mView.showRegCodeSuccess(JSonParamUtil.paramComm(data.toString()));
        }else if (postReg.equals(event_tag)){
            mView.showRegSuccess(JSonParamUtil.paramComm(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
        mView.hideLoadingDialog();
        mView.showToast(ApiH.NETWORK_ERROR);
    }
}
