package com.xhh.ticketver2.ui.user;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.github.jdsjlzx.ItemDecoration.GridItemDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.TouZhuEntry;
import com.xhh.ticketver2.presenter.TouZhuPresenter;
import com.xhh.ticketver2.ui.adapter.user.MyTouZhuAdapter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.homehe.BuyHeDetailActivity;
import com.xhh.ticketver2.ui.view.TouZhuView;

import butterknife.InjectView;

public class MyTouZhuActivity extends BaseActivity implements MyTouZhuAdapter.onItemClickListenter,TouZhuView {
    @InjectView(R.id.home_award_recyclerview)
    LRecyclerView mLRecyclerView;
    @InjectView(R.id.tz_root)
    View tz_root;
    @InjectView(R.id.layout_nodata)
    View layout_nodata;
    GridLayoutManager gridLayoutManaer;
    MyTouZhuAdapter adapter;
    TouZhuPresenter touZhuPresenter;
    int mPage;
    @Override
    protected void initViewsAndEvents() {
        initTopBar();
        mTopbarTitleTv.setText("我的投注");
        tz_root.setBackgroundResource(R.mipmap.a_bg);
        initRecyclerView();
        touZhuPresenter = new TouZhuPresenter(this,mContext);
        touZhuPresenter.postGetBetorders(mPage);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    private void initRecyclerView() {
        mLRecyclerView.setHasFixedSize(true);
        gridLayoutManaer = new GridLayoutManager(mContext, 1);
        mLRecyclerView.setLayoutManager(gridLayoutManaer);
        GridItemDecoration divider = new GridItemDecoration.Builder(mContext)
                .setHorizontal(R.dimen.margin_size_20_10dp)
                .setVertical(R.dimen.margin_size_20_10dp)
                .setColorResource(R.color.bg_gray)
                .build();
        mLRecyclerView.addItemDecoration(divider);

        adapter = new MyTouZhuAdapter(mContext, mScreenWidth, this);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        mLRecyclerView.setAdapter(lRecyclerViewAdapter);
        mLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
                touZhuPresenter.postGetBetorders(mPage);
            }
        });
        mLRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                touZhuPresenter.postGetBetorders(mPage);
            }
        });
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mytouzhu;
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
        TouZhuEntry touZhuEntry = adapter.getDataList().get(pos);
        Bundle bundle = new Bundle();
        bundle.putString("schemeId",touZhuEntry.schemeId);
        readyGo(BuyHeDetailActivity.class,bundle);
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void showGetBetOrdersSuccess(TouZhuEntry entry) {
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
    public void refreshOver() {
        mLRecyclerView.refreshComplete(Const.PAGE_SIZE);
    }
}
