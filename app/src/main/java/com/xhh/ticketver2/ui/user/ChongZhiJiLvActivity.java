package com.xhh.ticketver2.ui.user;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.BillEntry;
import com.xhh.ticketver2.beans.ChongTiEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.presenter.ChongTiPresenter;
import com.xhh.ticketver2.ui.adapter.user.ChongZhiJiLvAdapter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.view.ChongTiView;

import butterknife.InjectView;

public class ChongZhiJiLvActivity extends BaseActivity implements ChongZhiJiLvAdapter.onItemClickListenter,ChongTiView {

    @InjectView(R.id.layout_nodata)
    View layout_nodata;
    @InjectView(R.id.topbar_title)
    TextView topbar_title;
    @InjectView(R.id.czti_root)
    View czti_root;
    @InjectView(R.id.home_award_recyclerview)
    LRecyclerView mLRecyclerView;
    GridLayoutManager gridLayoutManager;
    ChongZhiJiLvAdapter adapter;
    String flag;
    ChongTiPresenter chongTiPresenter;
    int mPage = 0;
    @Override
    protected void initViewsAndEvents() {
        initTopBar();
        czti_root.setBackgroundResource(R.mipmap.a_bg);
        mTopbarLeftIv.setVisibility(View.VISIBLE);
        initRecyclerView();
        chongTiPresenter = new ChongTiPresenter(this,mContext);
        getData();
    }
    private void getData(){
        if ("1".equals(flag)){
            mTopbarTitleTv.setText("充值记录");
            chongTiPresenter.postGetRecharges(mPage);
        }else{
            mTopbarTitleTv.setText("提现记录");
            chongTiPresenter.postGetWithdrawals(mPage);
        }
    }
    private void initRecyclerView() {
        mLRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(mContext, 1);
        mLRecyclerView.setLayoutManager(gridLayoutManager);
//        GridItemDecoration divider = new GridItemDecoration.Builder(mContext)
//                .setHorizontal(R.dimen.divider_size)
//                .setVertical(R.dimen.divider_size)
//                .setColorResource(R.color.div_gray)
//                .build();
//        mLRecyclerView.addItemDecoration(divider);

        adapter = new ChongZhiJiLvAdapter(mContext, mScreenWidth, this,flag);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        mLRecyclerView.setAdapter(lRecyclerViewAdapter);
        mLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
                getData();
            }
        });
        mLRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                getData();
            }
        });
    }
    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        flag = extras.getString("flag");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_chongzhijilv;
    }
    @Override
    protected void onPubEvent(EventCenter eventCenter) {

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
    public void onItemClick(int pos) {
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void showGetRechargesSuccess(ChongTiEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS && entry.mList != null){
            if (mPage == 0){
                adapter.clear();
                if (entry.mList.isEmpty()){
                    layout_nodata.setVisibility(View.VISIBLE);
                }else{
                    layout_nodata.setVisibility(View.GONE);
                }
            }
            adapter.addAll(entry.mList);
            adapter.notifyDataSetChanged();
            if (entry.mList.size() < Const.PAGE_SIZE){
                mLRecyclerView.setNoMore(true);
            }else{
                mLRecyclerView.setNoMore(false);
            }
            mPage ++;
        }
    }

    @Override
    public void showGetWithdrawalsSuccess(ChongTiEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS && entry.mList != null){
            if (mPage == 0){
                adapter.clear();
                if (entry.mList.isEmpty()){
                    layout_nodata.setVisibility(View.VISIBLE);
                }else{
                    layout_nodata.setVisibility(View.GONE);
                }
            }
            adapter.addAll(entry.mList);
            adapter.notifyDataSetChanged();
            if (entry.mList.size() < Const.PAGE_SIZE){
                mLRecyclerView.setNoMore(true);
            }else{
                mLRecyclerView.setNoMore(false);
            }
            mPage ++;
        }
    }

    @Override
    public void showGetBillSuccess(BillEntry entry) {

    }

    @Override
    public void refreshOver() {
        mLRecyclerView.refreshComplete(Const.PAGE_SIZE);
    }
}
