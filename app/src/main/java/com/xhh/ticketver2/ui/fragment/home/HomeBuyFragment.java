package com.xhh.ticketver2.ui.fragment.home;


import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.BannaerEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.HomeBuyEntry;
import com.xhh.ticketver2.beans.UserInfoEntry;
import com.xhh.ticketver2.presenter.HomeBuyPresenter;
import com.xhh.ticketver2.presenter.UserPresenter;
import com.xhh.ticketver2.ui.adapter.homebuy.HomeBuyAdapter;
import com.xhh.ticketver2.ui.base.BaseFragment;
import com.xhh.ticketver2.ui.user.PayActivity;
import com.xhh.ticketver2.ui.user.PersonActivity;
import com.xhh.ticketver2.ui.user.TiXianActivity;
import com.xhh.ticketver2.ui.view.HomeBuyView;
import com.xhh.ticketver2.ui.view.UserView;
import com.xhh.ticketver2.ui.view.WinEntry;
import com.xhh.ticketver2.utils.CommUtil;
import com.xhh.ticketver2.utils.MLog;
import com.cc.util.ValidaUtil;
import com.cc.util.code.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeBuyFragment extends BaseFragment implements HomeBuyAdapter.onItemClickListenter,UserView,HomeBuyView {


    @InjectView(R.id.home_buy_header_civ)
    CircleImageView home_buy_header_civ;
    @InjectView(R.id.home_buy_leve_tv)
    TextView home_buy_leve_tv;
    @InjectView(R.id.home_buy_nickname_tv)
    TextView home_buy_nickname_tv;
    @InjectView(R.id.home_buy_money_tv)
    TextView home_buy_money_tv;
    @InjectView(R.id.home_buy_task_tv)
    TextView home_buy_task_tv;
    @InjectView(R.id.home_buy_task_progress)
    ContentLoadingProgressBar home_buy_task_progress;
    @InjectView(R.id.home_recyclerview)
    LRecyclerView mLRecyclerView;
    GridLayoutManager gridLayoutManager;
    HomeBuyAdapter homeThreeAdapter;
    UserPresenter userPresenter;

    HomeBuyPresenter homeBuyPresenter;
    List<HomeBuyEntry> mTempList = new ArrayList<>();
    BannaerEntry mBannerEntry;
    WinEntry mWinEntry;
    @Override
    protected void onFirstUserVisible() {
        userPresenter.postGetInfo();
    }

    @Override
    protected void onUserVisible() {
        userPresenter.postGetInfo();
        if (mTempList.isEmpty()){
            homeBuyPresenter.postGetLotteryType();
        }else{
            if (mBannerEntry == null){
                homeBuyPresenter.postGetBlan();
            }
            if (mWinEntry == null){
                homeBuyPresenter.postGetNewWinPurchase();
            }
        }

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViewsAndEvents() {
        userPresenter = new UserPresenter(this,mContext);
        homeBuyPresenter = new HomeBuyPresenter(this,mContext);
        initRecyclerView();
        homeBuyPresenter.postGetLotteryType();
    }
    private void initRecyclerView() {
        mLRecyclerView.setHasFixedSize(true);
        gridLayoutManager = new GridLayoutManager(mContext, 1);
        mLRecyclerView.setLayoutManager(gridLayoutManager);
//        GridItemDecoration divider = new GridItemDecoration.Builder(mContext)
//                .setHorizontal(R.dimen.margin_size_10_5dp)
//                .setVertical(R.dimen.margin_size_10_5dp)
//                .setColorResource(R.color.bg_gray)
//                .build();
//        mLRecyclerView.addItemDecoration(divider);

        homeThreeAdapter = new HomeBuyAdapter(mContext, mScreenWidth, this);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(homeThreeAdapter);
        mLRecyclerView.setAdapter(lRecyclerViewAdapter);
        mLRecyclerView.setPullRefreshEnabled(false);
        mLRecyclerView.setLoadMoreEnabled(false);

        mLRecyclerView.setFocusableInTouchMode(true);
        mLRecyclerView.requestFocus();
    }
    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_homebuy;
    }
    @OnClick({R.id.home_buy_tixian_tv,R.id.home_buy_pay_tv,R.id.home_buy_header_civ})
    public void onclick(View v){
        switch (v.getId()){
            case R.id.home_buy_tixian_tv:
                readyGo(TiXianActivity.class);
                break;
            case R.id.home_buy_pay_tv:
                readyGo(PayActivity.class);
                break;
            case R.id.home_buy_header_civ:
                readyGo(PersonActivity.class);
                break;
        }
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

    }

    @Override
    public void showGetUserInfoSuccess(UserInfoEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS){
            if (TextUtils.isEmpty(entry.headPortrait)){
                Glide.with(this).load(R.mipmap.test_logo2).dontAnimate().fitCenter().into(home_buy_header_civ);
            }else{
                Glide.with(this).load(entry.headPortrait).dontAnimate().fitCenter().error(R.mipmap.test_logo2).into(home_buy_header_civ);
            }
            home_buy_nickname_tv.setText(entry.nickName);
            home_buy_leve_tv.setText("Lv" + entry.userGrade);
            home_buy_money_tv.setText(getString(R.string.rmb) + " " + entry.availableFund);

            float max = CommUtil.stringToFloat(entry.totalBetTask);
            float task = max - CommUtil.stringToFloat(entry.betTask);
            MLog.e("=============task= max=" + max + " task="+task);
            if (task == 0){
                home_buy_task_tv.setText(0 + "/" + entry.totalBetTask);
            }else{
                home_buy_task_tv.setText(StringUtils.floatTo2(task) + "/" + entry.totalBetTask);
            }
            home_buy_task_progress.setMax((int) max);
            home_buy_task_progress.setProgress((int) task);
        }
    }

    @Override
    public void showGetLotteryTypeGroupsSuccess(HomeBuyEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS && entry.mList != null){
            mTempList.addAll(entry.mList);
            homeThreeAdapter.setDataList(mTempList);
            homeBuyPresenter.postGetBlan();
            homeBuyPresenter.postGetNewWinPurchase();
            if (!ValidaUtil.checkSignature(mContext)){
                //todo
//                getActivity().finish();
            }
        }
    }

    @Override
    public void showGetLotteryTypeSuccess(HomeBuyEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS && entry.mList != null){
            mTempList.add(entry);
            homeBuyPresenter.postGetLotteryTypeGroups();
        }
    }

    @Override
    public void showGetBlanSuccess(BannaerEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS && entry.mList != null && !entry.mList.isEmpty()){
            mBannerEntry = entry;
            homeThreeAdapter.setBannaer(mBannerEntry);
        }
    }

    @Override
    public void showGetWinPurchaseSuccess(WinEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS && entry.mList != null && !entry.mList.isEmpty()){
            mWinEntry = entry;
            homeThreeAdapter.setWinPurchase(mWinEntry);
        }
    }
}
