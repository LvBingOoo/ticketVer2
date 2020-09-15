package com.xhh.ticketver2.ui.homebuy;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.hhhc.obsessive.library.utils.AndroidBug5497Workaround;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.CommonHeader;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.AwardEntry;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.GameInfoEntry;
import com.xhh.ticketver2.beans.GameListEntry;
import com.xhh.ticketver2.beans.NumEntry;
import com.xhh.ticketver2.beans.ShiShiCaiEntry;
import com.xhh.ticketver2.presenter.BuyPresenter;
import com.xhh.ticketver2.ui.adapter.homeaward.BallAdapter;
import com.xhh.ticketver2.ui.adapter.homebuy.BuyDetailAdapter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.dialog.GameIssueDialog;
import com.xhh.ticketver2.ui.dialog.GameTypeDialog;
import com.xhh.ticketver2.ui.dialog.HeMaiDialog;
import com.xhh.ticketver2.ui.homeaward.AwardRecordActivity;
import com.xhh.ticketver2.ui.user.PayActivity;
import com.xhh.ticketver2.ui.view.BuyView;
import com.xhh.ticketver2.ui.view.CalculateEntry;
import com.xhh.ticketver2.utils.MLog;
import com.xhh.ticketver2.utils.MRule;
import com.cc.DataUitl;
import com.cc.uview.MyDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

public class BuyDetailActivity extends BaseActivity implements View.OnClickListener ,BuyView, BuyDetailAdapter.OnCallBack {


    @InjectView(R.id.buy_detail_last_award_recyclerview)
    RecyclerView buy_detail_last_award_recyclerview;
    @InjectView(R.id.home_recyclerview)
    LRecyclerView mLRecyclerView;
    GridLayoutManager gridLayoutManager;
    BuyDetailAdapter homeThreeAdapter;

    BuyPresenter buyPresenter ;


    @InjectView(R.id.buy_detail_issue_tv)
    TextView buy_detail_issue_tv;
    @InjectView(R.id.buy_detail_issue_tv2)
    TextView buy_detail_issue_tv2;
    @InjectView(R.id.time_hour_tv)
    TextView time_hour_tv;
    @InjectView(R.id.time_minute_tv)
    TextView time_minute_tv;
    @InjectView(R.id.time_second_tv)
    TextView time_second_tv;
    @InjectView(R.id.buydetail_kj_issue)
    TextView buydetail_kj_issue;
    @InjectView(R.id.buydetail_root)
    View buydetail_root;

    TextView buy_detail_header_desc;
    TextView buy_detail_header_fullname;

    long secondCount = 10800; // 秒数，倒计时3小时
    String mLotterType;
    GameListEntry gaEn;
    JSONObject joNumberProp ;

    int mIndexPlayTypePos = 0;
    List<GameListEntry> mHasBetList = new ArrayList<>();
    List<GameInfoEntry> mCrrentIssueList;
    GameInfoEntry mCurrentIssueEntry;
//    GameInfoEntry mSecendIssueEntry;

    boolean mIsAllBuy = false;
    boolean mIsStopIssue = false;
    @Override
    protected void initViewsAndEvents() {
        initTopBar();
        mTopbarRightTv.setText("近期开奖");
        Drawable nav_up=getResources().getDrawable(R.mipmap.history);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        mTopbarRightTv.setCompoundDrawables(null, null, nav_up, null);
        buydetail_root.setBackgroundResource(R.mipmap.a_bg);
        AndroidBug5497Workaround.assistActivity(this);
        buyPresenter = new BuyPresenter(this,mContext);
        initRecyclerView();
        buyPresenter.postGetPeriods(mLotterType,1,false);
        buyPresenter.postGetLotterynumberProp(mLotterType);
        buyPresenter.postGetNewNumber(mLotterType);
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
//        GridItemDecoration divider = new GridItemDecoration.Builder(mContext)
//                .setHorizontal(R.dimen.margin_size_10_5dp)
//                .setVertical(R.dimen.margin_size_10_5dp)
//                .setColorResource(R.color.bg_gray)
//                .build();
//        mLRecyclerView.addItemDecoration(divider);
        CommonHeader commonHeader = new CommonHeader(mContext, R.layout.layout_buydetail_header);
        commonHeader.findViewById(R.id.layout_buydetail_header_gametype_ll).setOnClickListener(this);
        buy_detail_header_desc = commonHeader.findViewById(R.id.buy_detail_header_desc);
        buy_detail_header_fullname = commonHeader.findViewById(R.id.buy_detail_header_fullname);
        homeThreeAdapter = new BuyDetailAdapter(mContext, mScreenWidth, this);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(homeThreeAdapter);
        lRecyclerViewAdapter.addHeaderView(commonHeader);
        mLRecyclerView.setAdapter(lRecyclerViewAdapter);
        mLRecyclerView.setPullRefreshEnabled(false);
        mLRecyclerView.setLoadMoreEnabled(false);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        mLotterType = extras.getString("lotteryTypeId");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_buydetail;
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
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @OnClick({R.id.topbar_right_text})
    public void onclick(View v){
        switch (v.getId()){
            case R.id.topbar_right_text:
                Bundle bundle = new Bundle();
                bundle.putString("lotteryTypeName",mTopbarTitleTv.getText().toString());
                bundle.putString("lotteryTypeId",mLotterType);
                readyGo(AwardRecordActivity.class,bundle);
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_buydetail_header_gametype_ll:
                if (gaEn != null){
                    GameTypeDialog gameTypeDialog = new GameTypeDialog();
                    gameTypeDialog.showDialog(mContext,gaEn,mIndexPlayTypePos, new GameTypeDialog.TZFSDialogCallBack() {
                        @Override
                        public void wayCallBack(int pos) {
                            mIndexPlayTypePos = pos;
                            initPlayBall();
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void showGetPeriodsSuccess(GameInfoEntry entry) {
        if (entry.commEn.status == Const.STATUS_SUCCESS && entry.mList != null && !entry.mList.isEmpty()){
            mCurrentIssueEntry = entry.mList.get(0);
            initPeriods();

        }else{
            showToast(entry.commEn.msg);
        }
    }
    private void initPeriods(){
        buy_detail_issue_tv.setText("第" + mCurrentIssueEntry.issue + "期");
        if (!TextUtils.isEmpty(mCurrentIssueEntry.end_time)) {
            long gapTime = DataUitl.strToLongJava(mCurrentIssueEntry.end_time) - System.currentTimeMillis();
            if (gapTime > 0){
                if (isShowTimeTipFisrt){
                    showToast(buy_detail_issue_tv.getText().toString() + "投注截至时间已到，已为您切换到最新一期");
                }
                buy_detail_issue_tv2.setText("投注截至时间");
            }
            initTimer(gapTime);
        }
    }

    boolean isNeedMore = false;
    GameIssueDialog gameIssueDialog;
    @Override
    public void showGetPeriodsMoreSuccess(GameInfoEntry entry) {
        if (entry.commEn.status == Const.STATUS_SUCCESS && entry.mList != null && !entry.mList.isEmpty()){
            mCrrentIssueList = entry.mList;
            final int arrMoneyAndNum[] = MRule.calculateBetMoneyAndNum(mHasBetList);
            if (arrMoneyAndNum[0] <= 0){
                showToast("请先添加到投注列表");
            }else{
                int multiple = homeThreeAdapter.getCurrentMultiple();
                if (isNeedMore){
                    gameIssueDialog.upDate(mCrrentIssueList);
                }else{
                    gameIssueDialog = new GameIssueDialog();
                    gameIssueDialog.showDialog(mContext,mCrrentIssueList,arrMoneyAndNum, multiple,new GameIssueDialog.TZFSDialogCallBack() {
                        @Override
                        public void wayCallBack(boolean isStopIssue) {
                            mIsStopIssue = isStopIssue;
                            int totalIsssue =0;
                            int totalIssueMoney = 0;
                            for ( int i=0;i<mCrrentIssueList.size();i++){
                                if (mCrrentIssueList.get(i).multipleInput !=0){
                                    totalIsssue ++;
                                    totalIssueMoney = totalIssueMoney +  mCrrentIssueList.get(i).multipleInput * arrMoneyAndNum[0];
                                    MLog.e(mCrrentIssueList.get(i).issue + "========issue=multiple = " + mCrrentIssueList.get(i).multipleInput);
                                }
                            }
                            if (totalIsssue >0){
                                homeThreeAdapter.setTottalIssue(totalIsssue, totalIssueMoney);
                            }
                        }

                        @Override
                        public void wayCallBackGetData(int size) {
                            isNeedMore = true;
                            buyPresenter.postGetPeriods(mLotterType,size,true);
                        }
                    });
                }
            }

        }else{
            showToast(entry.commEn.msg);
        }
    }

    /**
     * 倒计时
     */
    private void initTimer(long gapTime) {
        secondCount = gapTime / 1000;// 秒
        mTopbarTitleTv.post(runnable);
    }
    boolean isShowTimeTipFisrt = false;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (time_hour_tv != null){
                if (secondCount > 0) {
                    secondCount--;
                    String[] showTime = DataUitl.surplusLongToStrngArr(secondCount);
                    time_hour_tv.setText(showTime[1]);
                    time_minute_tv.setText(showTime[2]);
                    time_second_tv.setText(showTime[3]);
                    mTopbarTitleTv.postDelayed(runnable, 1000);
                } else {
                    buy_detail_issue_tv2.setText("投注已截至");
                    time_hour_tv.setText("00");
                    time_minute_tv.setText("00");
                    time_second_tv.setText("00");
                    homeThreeAdapter.reSetButtomData();
                    // 重新获取期数 和结束时间
//                    if (mSecendIssueEntry != null){
//                        mCurrentIssueEntry = mSecendIssueEntry;
//                        mSecendIssueEntry = null;
//                        initPeriods();
//                    }
                    isShowTimeTipFisrt = true;
                    buyPresenter.postGetPeriods(mLotterType , 1,false);
                }
            }

        }
    };
    @Override
    public void showGetNumberPropSuccess(CommEntry entry) {
        try {
            if (entry.status == Const.STATUS_SUCCESS){
                joNumberProp = new JSONObject(entry.json);
                buyPresenter.postGetPlay(mLotterType);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showGetPlaySuccess(GameListEntry entry) {
        if (entry.commEn.status == Const.STATUS_SUCCESS){
            gaEn = entry;
            if (gaEn.mlist != null && !gaEn.mlist.isEmpty()) {
                mIndexPlayTypePos = 0;
                initPlayBall();
            }
        }else{
            showToast(entry.commEn.msg);
        }
    }
    private void initPlayBall(){
        GameListEntry gameListEntry = gaEn.mlist.get(mIndexPlayTypePos);

        mTopbarTitleTv.setText(gameListEntry.lotteryTypeName);
        buy_detail_header_desc.setText(gameListEntry.simpleDescribe);
        buy_detail_header_fullname.setText(gameListEntry.fullName);

        List<ShiShiCaiEntry> list = gameListEntry.ruleEntry;
        ShiShiCaiEntry lastEntry = new ShiShiCaiEntry();
        lastEntry.type = Const.TYPE_02;
        homeThreeAdapter.setPropMis(joNumberProp);
        homeThreeAdapter.setDataList(list);
        homeThreeAdapter.getDataList().add(lastEntry);
        homeThreeAdapter.notifyDataSetChanged();
    }
    @Override
    public void showGetNewNumberSuccess(AwardEntry entry) {
        try {
            if (entry.commEntry.status == Const.STATUS_SUCCESS){
                buydetail_kj_issue.setText("第" + entry.period + "期 开奖号码");
                LinearLayoutManager gridLayoutManager = new LinearLayoutManager(mContext);
                gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                buy_detail_last_award_recyclerview.setLayoutManager(gridLayoutManager);
                BallAdapter gridAdapter = new BallAdapter(mContext, mScreenWidth, null,0,false);
                List<NumEntry> temp = new ArrayList<>();
                String num[] = entry.dwawNumber.split(",");
                for (int i=0;i<num.length;i++){
                    NumEntry numEntry = new NumEntry();
                    numEntry.num = num[i];
                    temp.add(numEntry);
                }
                gridAdapter.setDataList(temp);
                buy_detail_last_award_recyclerview.setAdapter(gridAdapter);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void showCalculateBetNumSuccess(CalculateEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS){
            GameListEntry gameListEntry = gaEn.mlist.get(mIndexPlayTypePos);
            gameListEntry.showBetMoney = entry.betMoney;
            gameListEntry.showBetNum = entry.betNum;
            mHasBetList.add(gameListEntry.myclone());
            homeThreeAdapter.setBetList(mHasBetList);
        }else{
            showToast(entry.commEntry.msg);
        }
    }

    @Override
    public void showBetSuccess(CommEntry entry) {
        if (entry.status == Const.STATUS_SUCCESS){
            showToast("投注成功");
            finish();
        }else{
            if ("余额不足".equals(entry.msg)){
                readyGo(PayActivity.class);
            }
            showToast(entry.msg);
        }
    }

    @Override
    public void onCallBack(int flag,int pararentPos ,int numPos) {
        if (flag == Const.FLAG_BUY_DETAL_ADD_TO_LIST){
            GameListEntry gameListEntry = gaEn.mlist.get(mIndexPlayTypePos);
            String[] check = MRule.isRightMinChoice(gameListEntry.ruleEntry);
            if ("0".equals(check[0])) {
                String betContent = MRule.getRowsChoiceJSonNewCount(gameListEntry.ruleEntry);
                gameListEntry.showTip = "[" + gameListEntry.fullName + "]" + betContent;
                buyPresenter.postCalculatebetnum(mLotterType,gameListEntry.playedType,betContent);
            } else {
                Toast.makeText(mContext,check[1],Toast.LENGTH_SHORT).show();
            }
        }else if (flag == Const.FLAG_BUY_DETAL_CLEAR_LIST){
            mHasBetList.clear();
            homeThreeAdapter.setBetList(mHasBetList);
        }else if (flag == Const.FLAG_BUY_DETAL_NUM_SELECTE){
            GameListEntry gameListEntry = gaEn.mlist.get(mIndexPlayTypePos);
            ShiShiCaiEntry shiShiCaiEntry = gameListEntry.ruleEntry.get(pararentPos);
            if (MRule.isRightRowsChoiceMax(shiShiCaiEntry)) {
                gameListEntry.ruleEntry.get(pararentPos).nums.get(numPos).isSelect = true;
                MRule.cancleLastCheck(gameListEntry.ruleEntry, pararentPos, numPos);
                homeThreeAdapter.notifyDataSetChanged();
            } else {
                showToast(shiShiCaiEntry.name + "最多只能选择" + shiShiCaiEntry.rule.max_nums + "个");
            }
        }else if (flag == Const.FLAG_BUY_DETAL_ISSUE){
            isNeedMore = false;
            buyPresenter.postGetPeriods(mLotterType,5,true);
        }else if (flag == Const.FLAG_BUY_HE){
            MyDialog.ShowDialog(mContext, "", new String[]{"单买", "合买"}, new MyDialog.DialogItemClickListener() {
                @Override
                public void confirm(String result) {
                    if ("单买".endsWith(result)){
                        homeThreeAdapter.setHeOrDan("1",null,null);
                    }else{
                        int[] moneyAndNum = homeThreeAdapter.getBetMoneyAndrNum();
                        if (mCurrentIssueEntry == null){
                            showToast("期号获取失败，请稍后再试");
                        }else if (moneyAndNum != null && moneyAndNum[0]>0){
                            int money = homeThreeAdapter.getTotalMoney();
                            int totalIssue = homeThreeAdapter.getTottalIssue();
                            GameListEntry gameListEntry = gaEn.mlist.get(mIndexPlayTypePos);
                            HeMaiDialog heMaiDialog = new HeMaiDialog();
                            heMaiDialog.showDialog(mContext,gameListEntry,money,totalIssue, mCurrentIssueEntry,new HeMaiDialog.TZFSDialogCallBack() {

                                @Override
                                public void wayCallBack(String buyNum, String baodiNum,boolean isAllBuy) {
                                    mIsAllBuy = isAllBuy;
                                    homeThreeAdapter.setHeOrDan("2",buyNum,baodiNum);
                                }
                            });
                        }else{
                            showToast("请先添加购彩方案到投注列表");
                        }


                    }
                }
            });
        }else if (flag == Const.FLAG_BUY_OK){
            if (mHasBetList.isEmpty()){
                showToast("请先添加购彩方案到投注列表");
            }else{
                boolean isIssue = homeThreeAdapter.getIsIssue();
                String isBuyOrHeBuy = homeThreeAdapter.getIsBuyOrHeBuy();
                int multiple = homeThreeAdapter.getCurrentMultiple();
                int  showMoney = homeThreeAdapter.getTotalShowMoney();
                String[] buyNumAndBuyHeNum = homeThreeAdapter.getHeBuyNumAndBaodiNum();
                buyPresenter.postLotteryBet(mLotterType,isIssue,isBuyOrHeBuy, mCurrentIssueEntry.issue,multiple + "",
                        showMoney + "",buyNumAndBuyHeNum
                        ,mHasBetList,mCrrentIssueList,mIsAllBuy,mIsStopIssue);
            }
        }
    }

    @Override
    public void onCallBackTag(int flag, int pararentPos, String tag) {
        if (flag == Const.FLAG_BUY_SELECT_TOOLS){
            GameListEntry gameListEntry = gaEn.mlist.get(mIndexPlayTypePos);
            ShiShiCaiEntry shiShiCaiEntry = gameListEntry.ruleEntry.get(pararentPos);//当前行
            List<NumEntry> numEntries  = shiShiCaiEntry.nums;
            if ("全".equals(tag)){
                for (int i=0;i<numEntries.size();i++){
                    numEntries.get(i).isSelect = true;
                    MRule.cancleLastCheck(gameListEntry.ruleEntry, pararentPos, i);
                }
            }else if ("大".equals(tag)){
                for (int i = 0; i < numEntries.size(); i++){
                    if (i < numEntries.size() / 2){
                        numEntries.get(i).isSelect = false;
                    }else{
                        numEntries.get(i).isSelect = true;
                        MRule.cancleLastCheck(gameListEntry.ruleEntry, pararentPos, i);
                    }
                }
            }else if ("小".equals(tag)){
                for (int i = 0; i < numEntries.size(); i++){
                    if (i < numEntries.size() / 2){
                        numEntries.get(i).isSelect = true;
                        MRule.cancleLastCheck(gameListEntry.ruleEntry, pararentPos, i);
                    }else{
                        numEntries.get(i).isSelect = false;
                    }
                }
            }else if ("单".equals(tag)){
                for (int i = 0; i < numEntries.size(); i++){
                    if (i % 2 != 0){
                        numEntries.get(i).isSelect = true;
                        MRule.cancleLastCheck(gameListEntry.ruleEntry, pararentPos, i);
                    }else{
                        numEntries.get(i).isSelect = false;
                    }
                }
            }else if ("双".equals(tag)){
                for (int i = 0; i < numEntries.size(); i++){
                    if (i % 2 == 0){
                        numEntries.get(i).isSelect = true;
                        MRule.cancleLastCheck(gameListEntry.ruleEntry, pararentPos, i);
                    }else{
                        numEntries.get(i).isSelect = false;
                    }
                }
            }else if ("清".equals(tag)){
                for (int i=0;i<numEntries.size();i++){
                    numEntries.get(i).isSelect = false;
                }
            }
            homeThreeAdapter.notifyDataSetChanged();
        }
    }
}
