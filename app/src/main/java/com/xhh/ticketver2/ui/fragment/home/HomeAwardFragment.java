package com.xhh.ticketver2.ui.fragment.home;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.widget.TextView;

import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.github.jdsjlzx.ItemDecoration.GridItemDecoration;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.AwardEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.presenter.AwardPresenter;
import com.xhh.ticketver2.ui.adapter.homeaward.HomeAwardAdapter;
import com.xhh.ticketver2.ui.base.BaseFragment;
import com.xhh.ticketver2.ui.homeaward.AwardRecordActivity;
import com.xhh.ticketver2.ui.view.AwardView;

import butterknife.InjectView;

public class HomeAwardFragment extends BaseFragment implements HomeAwardAdapter.onItemClickListenter, AwardView {
    @InjectView(R.id.topbar_title)
    TextView topbar_title;
    @InjectView(R.id.home_award_recyclerview)
    LRecyclerView mLRecyclerView;
    GridLayoutManager gridLayoutManager;
    HomeAwardAdapter adapter;

    AwardPresenter awardPresenter;
    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViewsAndEvents() {
        topbar_title.setText("开奖公告");
        awardPresenter = new AwardPresenter(this,mContext);
        initRecyclerView();
        awardPresenter.postGetNewNumbers();
    }
    private void initRecyclerView() {
        mLRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(mContext, 2);
        mLRecyclerView.setLayoutManager(gridLayoutManager);
        GridItemDecoration divider = new GridItemDecoration.Builder(mContext)
                .setHorizontal(R.dimen.divider_size)
                .setVertical(R.dimen.divider_size)
                .setColorResource(R.color.div_gray)
                .build();
        mLRecyclerView.addItemDecoration(divider);

        adapter = new HomeAwardAdapter(mContext, mScreenWidth, this);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        mLRecyclerView.setAdapter(lRecyclerViewAdapter);
        mLRecyclerView.setPullRefreshEnabled(true);
        mLRecyclerView.setLoadMoreEnabled(false);
        mLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                awardPresenter.postGetNewNumbers();
            }
        });

    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_award;
    }

    @Override
    protected void onPubEvent(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void onItemClick(int pos) {
        AwardEntry awardEntry = adapter.getItem(pos);
        Bundle bundle = new Bundle();
        bundle.putSerializable("entry",awardEntry);
        bundle.putString("lotteryTypeName",awardEntry.lotteryTypeName);
        bundle.putString("lotteryTypeId",awardEntry.lotteryTypeId);
        readyGo(AwardRecordActivity.class,bundle);
    }

    @Override
    public void showGetNewNumberSuccess(AwardEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS && entry.mList != null){
            adapter.setDataList(entry.mList);
        }
    }

    @Override
    public void showGetLotteryNumbersSuccess(AwardEntry entry) {

    }

    @Override
    public void pullRefreshOver() {
        mLRecyclerView.refreshComplete(12);
    }
}
