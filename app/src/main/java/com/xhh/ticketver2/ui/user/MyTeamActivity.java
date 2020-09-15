package com.xhh.ticketver2.ui.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.MyTeamListBean;
import com.xhh.ticketver2.presenter.MyTeamPresenter;
import com.xhh.ticketver2.ui.adapter.user.MyTeamListAdapter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.view.MyTeamView;

import java.util.List;
import java.util.Map;

public class MyTeamActivity extends BaseActivity implements MyTeamView {
    private RecyclerView rvMyTeam;
    private MyTeamListAdapter adapter;
    private MyTeamPresenter presenter;
    private LinearLayout layoutEmpty;

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_my_team;
    }

    @Override
    protected void onPubEvent(EventCenter eventCenter) {

    }

    @Override
    protected void initViewsAndEvents() {
        TextView topbar_title = (TextView) findViewById(R.id.topbar_title);
        topbar_title.setText("我的团队");

        layoutEmpty = (LinearLayout) findViewById(R.id.layoutEmpty);

        rvMyTeam = (RecyclerView) findViewById(R.id.rvMyTeam);
        rvMyTeam.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyTeamListAdapter(this);
        rvMyTeam.setAdapter(adapter);

        presenter = new MyTeamPresenter(this, this);
        presenter.getMyTeamList();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void getListSuccess(MyTeamListBean response) {
        if (response != null && response.getObj() != null) {
            List<MyTeamListBean.ObjBean.RowsBean> list = response.getObj().getRows();
            if (list != null && list.size() > 0) {
                layoutEmpty.setVisibility(View.GONE);
                rvMyTeam.setVisibility(View.VISIBLE);
                adapter.setDataList(list);
                adapter.notifyDataSetChanged();
            } else {
                layoutEmpty.setVisibility(View.VISIBLE);
                rvMyTeam.setVisibility(View.GONE);
            }
        } else {
            layoutEmpty.setVisibility(View.VISIBLE);
            rvMyTeam.setVisibility(View.GONE);
        }
    }

    @Override
    public void getListError() {
        layoutEmpty.setVisibility(View.VISIBLE);
        rvMyTeam.setVisibility(View.GONE);
    }
}
