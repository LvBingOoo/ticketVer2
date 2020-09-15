package com.xhh.ticketver2.presenter;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.beans.MyTeamListBean;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.base.BaseAppCompatActivity2;
import com.xhh.ticketver2.ui.view.MyTeamView;

import java.util.HashMap;
import java.util.Map;

public class MyTeamPresenter implements BaseLoadedListener<Object> {
    private Context context;
    private MyTeamView myTeamView;
    private CommInteractor mInteractor;
    private Gson gson;

    public MyTeamPresenter(Context context, MyTeamView myTeamView) {
        this.myTeamView = myTeamView;
        this.context = context;
        mInteractor = new CommInteractor(this);
        gson = new Gson();
    }

    public void getMyTeamList() {
        myTeamView.showLoadingDialog(ApiH.LOADING);
        Map<String, String> params = new HashMap<>();
        params.put("pageSize", "10000");
        params.put("pageIndex", "0");
        mInteractor.post("myTeam", ApiH.URL_MY_TEAM, params);
    }

    @Override
    public void onSuccess(String event_tag, final Object data) {
        if (data instanceof String) {
            ((BaseActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myTeamView.hideLoadingDialog();
                    MyTeamListBean myTeamListBean = gson.fromJson((String) data, MyTeamListBean.class);
                    myTeamView.getListSuccess(myTeamListBean);
                }
            });

        }
    }

    @Override
    public void onException(String msg) {
        ((BaseAppCompatActivity2) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                myTeamView.hideLoadingDialog();
                myTeamView.showToast(ApiH.NETWORK_ERROR);
            }
        });
    }
}
