package com.xhh.ticketver2.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.view.LoginView;
import com.xhh.ticketver2.utils.FileUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class LoginPresenter implements BaseLoadedListener<Object> {

    private LoginView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String tag = "tag";
    public LoginPresenter(LoginView loginView, Context context){
        mContext = context;
        mView = loginView;
        mInteractor = new CommInteractor(this);
    }
    public void post(String username,String password){
        if (TextUtils.isEmpty(username)){
            mView.showToast("请输入用户名");
        }else if (TextUtils.isEmpty(password)){
            mView.showToast("请输入密码");
        }else{
            FileUtil.saveString(mContext,FileUtil.USERNAME,username);
            Map<String,String> params = new HashMap<>();
            params.put("username",username);
            params.put("password",password);
            mView.showLoadingDialog(ApiH.LOADING);
            mInteractor.post(tag, ApiH.URL_USER_LOGIN,params);
        }
    }

    @Override
    public void onSuccess(String event_tag, Object data) {
        mView.hideLoadingDialog();
        if (tag.equals(event_tag)){
            mView.showLoginSuccess(JSonParamUtil.paramLogin(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
        mView.hideLoadingDialog();
        mView.showToast(ApiH.NETWORK_ERROR);
    }
}
