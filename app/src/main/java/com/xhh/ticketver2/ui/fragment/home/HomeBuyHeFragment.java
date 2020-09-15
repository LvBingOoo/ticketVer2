package com.xhh.ticketver2.ui.fragment.home;


import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.github.jdsjlzx.ItemDecoration.GridItemDecoration;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.BuyHeEntry;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.SchemeEntry;
import com.xhh.ticketver2.presenter.BuyHePresenter;
import com.xhh.ticketver2.ui.adapter.homebuyhe.HomeBuyHeAdapter;
import com.xhh.ticketver2.ui.base.BaseFragment;
import com.xhh.ticketver2.ui.homehe.BuyHeDetailActivity;
import com.xhh.ticketver2.ui.view.BuyHeView;

import butterknife.InjectView;

public class HomeBuyHeFragment extends BaseFragment implements HomeBuyHeAdapter.onItemClickListenter,BuyHeView {

    @InjectView(R.id.topbar_title)
    TextView topbar_title;
    @InjectView(R.id.homebuyhe_root)
    View homebuyhe_root;
    @InjectView(R.id.home_he_recyclerview)
    LRecyclerView mLRecyclerView;
    GridLayoutManager gridLayoutManager;
    HomeBuyHeAdapter adapter;

    BuyHePresenter buyHePresenter;
    int mPage = 0;
    @Override
    protected void onFirstUserVisible() {
    }

    @Override
    protected void onUserVisible() {
        mPage = 0;
        buyHePresenter.postGetPtorders(mPage);
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViewsAndEvents() {
        topbar_title.setText("合买大厅");
        homebuyhe_root.setBackgroundResource(R.mipmap.a_bg);
        buyHePresenter = new BuyHePresenter(this,mContext);
        initRecyclerView();
        buyHePresenter.postGetPtorders(mPage);
    }
    private void initRecyclerView() {
        mLRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(mContext, 1);
        mLRecyclerView.setLayoutManager(gridLayoutManager);
        GridItemDecoration divider = new GridItemDecoration.Builder(mContext)
                .setHorizontal(R.dimen.margin_size_20_10dp)
                .setVertical(R.dimen.margin_size_20_10dp)
                .setColorResource(R.color.transparent)
                .build();
        mLRecyclerView.addItemDecoration(divider);

        adapter = new HomeBuyHeAdapter(mContext, mScreenWidth, this);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(adapter);
        mLRecyclerView.setAdapter(lRecyclerViewAdapter);
        mLRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                buyHePresenter.postGetPtorders(mPage);
            }
        });
        mLRecyclerView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPage = 0;
                buyHePresenter.postGetPtorders(mPage);
            }
        });
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_homebuyhe;
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
        initGoDetail(pos);
    }

    @Override
    public void onBuy(int pos) {
        initGoDetail(pos);
    }
    private void initGoDetail(int pos){
        BuyHeEntry buyHeEntry = adapter.getDataList().get(pos);
        Bundle bundle = new Bundle();
        bundle.putString("schemeId",buyHeEntry.schemeId);
        readyGo(BuyHeDetailActivity.class,bundle);
    }
    @Override
    public void showGetHeListSuccess(BuyHeEntry entry) {
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
    public void showGetSchemeDetailSuccess(SchemeEntry entry) {

    }

    @Override
    public void showBetPartnershipSuccess(CommEntry entry) {
        if (entry.status == Const.STATUS_SUCCESS){
            showToast("购买成功");
            mPage = 0;
            buyHePresenter.postGetPtorders(mPage);
        }else{
            showToast(entry.msg);
        }
    }

    @Override
    public void pullRefreshOver() {
        mLRecyclerView.refreshComplete(Const.PAGE_SIZE);
    }
}
