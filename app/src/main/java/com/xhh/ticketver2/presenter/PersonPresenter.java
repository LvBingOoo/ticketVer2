package com.xhh.ticketver2.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.base.BaseAppCompatActivity2;
import com.xhh.ticketver2.ui.view.PersonView;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class PersonPresenter implements BaseLoadedListener<Object> {

    private PersonView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String postUserUpdate = "postUserUpdate";
    public String postUploadHeader = "postUploadHeader";

    public PersonPresenter(PersonView view, Context context) {
        mContext = context;
        mView = view;
        mInteractor = new CommInteractor(this);
    }

    public void postUserUpdate(String nickName, String qq) {
        try {
            if (TextUtils.isEmpty(nickName) && TextUtils.isEmpty(qq)) {
                return;
            }
            Map<String, String> params = new HashMap<>();
            JSONObject jo = new JSONObject();
            jo.put("qq", qq);
            jo.put("nickName", nickName);
            params.put("user", jo.toString());
            mView.showLoadingDialog(ApiH.LOADING);
            mInteractor.post(postUserUpdate, ApiH.URL_USER_UPDATE, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postUploadHeader(File file) {
        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.upLoadFile(postUploadHeader, ApiH.URL_USER_UPLOADUSERHEADIMG, file);
    }

    @Override
    public void onSuccess(String event_tag, Object data) {
        mView.hideLoadingDialog();
        if (postUserUpdate.equals(event_tag)) {
            mView.showUpdateUserSuccess(JSonParamUtil.paramComm(data.toString()));
        } else if (postUploadHeader.equals(event_tag)) {
            mView.showUpdateHeaderSuccess(JSonParamUtil.paramComm(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
        ((BaseAppCompatActivity2) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mView.hideLoadingDialog();
                mView.showToast(ApiH.NETWORK_ERROR);
            }
        });

    }
}
