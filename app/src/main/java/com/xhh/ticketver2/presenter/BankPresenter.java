package com.xhh.ticketver2.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.view.BankView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class BankPresenter implements BaseLoadedListener<Object> {

    private BankView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String postGetBank = "postGetBank";
    public String postAddBank = "postAddBank";
    public String postUpdateBank = "postUpdateBank";
    public BankPresenter(BankView view, Context context){
        mContext = context;
        mView = view;
        mInteractor = new CommInteractor(this);
    }
    public void postGetBank(){
        Map<String,String> params = new HashMap<>();
        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postGetBank, ApiH.URL_USER_GETBANKS,params);
    }
    public void postAddBank(String accountName,String bankName,String bankNumber,String bankArea){
        Map<String,String> params = new HashMap<>();
        try {
            if (TextUtils.isEmpty(accountName)){
                mView.showToast("请输入开户姓名");
            }else if (TextUtils.isEmpty(bankName)){
                mView.showToast("请输入银行名称");
            }else if (TextUtils.isEmpty(bankNumber)){
                mView.showToast("请输入银行卡号");
            }else if (TextUtils.isEmpty(bankArea)){
                mView.showToast("请输入开户支行");
            }else{
                JSONObject jo = new JSONObject();
                jo.put("accountName",accountName);
                jo.put("bankName",bankName);
                jo.put("bankNumber",bankNumber);
                jo.put("bankArea",bankArea);
                params.put("userBank",jo.toString());
                mView.showLoadingDialog(ApiH.LOADING);
                mInteractor.post(postAddBank, ApiH.URL_USER_ADDBANK,params);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void postUpdateBank(String bankcarId,String accountName,String bankName,String bankNumber,String bankArea){
        Map<String,String> params = new HashMap<>();
        try {
            if (TextUtils.isEmpty(accountName)){
                mView.showToast("请输入开户姓名");
            }else if (TextUtils.isEmpty(bankName)){
                mView.showToast("请输入银行名称");
            }else if (TextUtils.isEmpty(bankNumber)){
                mView.showToast("请输入银行卡号");
            }else if (TextUtils.isEmpty(bankArea)){
                mView.showToast("请输入开户支行");
            }else{
                JSONObject jo = new JSONObject();
                jo.put("accountName",accountName);
                jo.put("bankName",bankName);
                jo.put("bankNumber",bankNumber);
                jo.put("bankArea",bankArea);
                jo.put("bankcarId",bankcarId);
                params.put("userBank",jo.toString());
                mView.showLoadingDialog(ApiH.LOADING);
                mInteractor.post(postUpdateBank, ApiH.URL_USER_UPDATEBANK,params);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onSuccess(String event_tag, Object data) {
        mView.hideLoadingDialog();
        if (postGetBank.equals(event_tag)){
            mView.showGetBanksSuccess(JSonParamUtil.paramGetBank(data.toString()));
        }else if (postAddBank.equals(event_tag)){
            mView.showAddBanksSuccess(JSonParamUtil.paramComm(data.toString()));
        }else if (postUpdateBank.equals(event_tag)){
            mView.showUpdateBanksSuccess(JSonParamUtil.paramComm(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
        mView.hideLoadingDialog();
        mView.showToast(ApiH.NETWORK_ERROR);
    }
}
