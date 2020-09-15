package com.xhh.ticketver2.ui.homeaward;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.github.jdsjlzx.ItemDecoration.GridItemDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.AwardEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.presenter.AwardPresenter;
import com.xhh.ticketver2.ui.adapter.homeaward.AwardRecordAdapter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.view.AwardView;

import butterknife.InjectView;

public class AwardRecordActivity extends BaseActivity implements  AwardRecordAdapter.onItemClickListenter ,AwardView {

    @InjectView(R.id.are_root)
    View are_root;
    @InjectView(R.id.home_he_recyclerview)
    LRecyclerView mLRecyclerView;
    GridLayoutManager gridLayoutManager;
    AwardRecordAdapter adapter;

    AwardPresenter awardPresenter;
    int mPage = 0;
    String lotteryTypeName;
    String lotteryTypeId;
    @Override
    protected void initViewsAndEvents() {
        initTopBar();
        mTopbarTitleTv.setText(lotteryTypeName);
        awardPresenter = new AwardPresenter(this,mContext);
        mTopbarLeftIv.setVisibility(View.VISIBLE);
        are_root.setBackgroundResource(R.mipmap.a_bg);
        initRecyclerView();
        awardPresenter.postGetLotteryNumbers(lotteryTypeId,mPage);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    private void initRecyclerView() {
        mLRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(mContext, 1);
        mLRecyclerView.setLayoutManager(gridLayoutManager);
        GridItemDecoration divider = new GridItemDecoration.Builder(mContext)
                .setHorizontal(R.dimen.divider_size)
                .setVertical(R.dimen.divider_size)
                .setColorResource(R.color.div_gray2)
                .build();
        mLRecyclerView.addItemDecoration(divider);

        adapter = new AwardRecordAdapter(mContext, mScreenWidth, this);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        mLRecyclerView.setAdapter(lRecyclerViewAdapter);
        mLRecyclerView.setPullRefreshEnabled(false);
        mLRecyclerView.setLoadMoreEnabled(true);
        mLRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                awardPresenter.postGetLotteryNumbers(lotteryTypeId,mPage);
            }
        });
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        lotteryTypeName = extras.getString("lotteryTypeName");
        lotteryTypeId = extras.getString("lotteryTypeId");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_awardrecord;
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
    public void showGetNewNumberSuccess(AwardEntry entry) {

    }

    @Override
    public void showGetLotteryNumbersSuccess(AwardEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS && entry.mList != null){
            if (mPage == 0){
                adapter.clear();
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
    public void pullRefreshOver() {
        mLRecyclerView.refreshComplete(Const.PAGE_SIZE);
    }
}
