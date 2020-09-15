package com.xhh.ticketver2.ui.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.beans.MyTeamListBean;
import com.xhh.ticketver2.presenter.MyTeamPresenter;
import com.xhh.ticketver2.ui.adapter.user.MyTeamListAdapter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.view.MyTeamView;
import com.xhh.ticketver2.utils.volley.VolleyManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTeamActivity1 extends AppCompatActivity {
    private RecyclerView rvMyTeam;
    private MyTeamListAdapter adapter;
    private MyTeamPresenter presenter;
    private LinearLayout layoutEmpty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_team);
        initViews();
        initData();
    }

    private void initViews() {
        TextView topbar_title = (TextView) findViewById(R.id.topbar_title);
        topbar_title.setText("我的团队");

        layoutEmpty = (LinearLayout) findViewById(R.id.layoutEmpty);

        rvMyTeam = (RecyclerView) findViewById(R.id.rvMyTeam);
        rvMyTeam.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyTeamListAdapter(this);
        rvMyTeam.setAdapter(adapter);
    }

    private void initData() {
        Map<String, String> params = new HashMap<>();
        params.put("pageSize", "10000");
        params.put("pageIndex", "0");
        VolleyManager.newInstance().MGsonPostRequest("myTeam", params, ApiH.URL_MY_TEAM, String.class,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String data) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (!TextUtils.isEmpty(data)) {
                                    MyTeamListBean response = new Gson().fromJson((String) data, MyTeamListBean.class);
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
                            }
                        });
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                layoutEmpty.setVisibility(View.VISIBLE);
                                rvMyTeam.setVisibility(View.GONE);
                            }
                        });

                    }
                });
    }

}
