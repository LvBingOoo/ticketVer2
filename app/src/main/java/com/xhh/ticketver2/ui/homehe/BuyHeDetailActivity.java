package com.xhh.ticketver2.ui.homehe;


import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.hhhc.obsessive.library.utils.AndroidBug5497Workaround;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.BuyHeEntry;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.HeBuyDoEntry;
import com.xhh.ticketver2.beans.HeBuyUserEntry;
import com.xhh.ticketver2.beans.SchemeEntry;
import com.xhh.ticketver2.beans.SchemeInfoEntry;
import com.xhh.ticketver2.presenter.BuyHePresenter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.dialog.MenuDialog3;
import com.xhh.ticketver2.ui.view.BuyHeView;
import com.xhh.ticketver2.utils.CommUtil;
import com.cc.util.code.StringUtils;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class BuyHeDetailActivity extends BaseActivity implements BuyHeView {


    @InjectView(R.id.layout_item_buy_bottom_bd_ll)
    View layout_item_buy_bottom_bd_ll;
    @InjectView(R.id.layout_item_buy_bottom_buy_ll)
    View layout_item_buy_bottom_buy_ll;
    @InjectView(R.id.buyhedetail_root)
    View buyhedetail_root;

    @InjectView(R.id.lottery_name)
    TextView lottery_name ;
    @InjectView(R.id.lottery_issue)
    TextView lottery_issue ;
    @InjectView(R.id.header_civ)
    CircleImageView header_civ;
    @InjectView(R.id.buyhe_leve)
    TextView buyhe_leve ;
    @InjectView(R.id.buyhe_username)
    TextView buyhe_username ;
    @InjectView(R.id.buyhe_progress)
    ContentLoadingProgressBar buyhe_progress ;
    @InjectView(R.id.buy_bottom_totalmoney)
    TextView buy_bottom_totalmoney ;
    @InjectView(R.id.buy_bottom_unitprice)
    TextView buy_bottom_unitprice ;
    @InjectView(R.id.buy_bottom_leftnum)
    TextView buy_bottom_leftnum;
    @InjectView(R.id.buy_bottom_degree)
    TextView buy_bottom_degree ;
    @InjectView(R.id.buy_bottom_baodi)
    TextView buy_bottom_baodi ;


    @InjectView(R.id.count_reduce_iv)
    ImageView count_reduce_iv;
    @InjectView(R.id.count_add_iv)
    ImageView count_add_iv;
    @InjectView(R.id.count_input_tv)
    EditText count_input_tv;

    @InjectView(R.id.buyhe_bottom_rl)
    View buyhe_bottom_rl;
    String schemeId;
    BuyHePresenter buyHePresenter;
    @Override
    protected void initViewsAndEvents() {
        initTopBar();
        mTopbarTitleTv.setText("方案详情");
        mTopbarLeftIv.setVisibility(View.VISIBLE);
        layout_item_buy_bottom_bd_ll.setVisibility(View.VISIBLE);
        layout_item_buy_bottom_buy_ll.setVisibility(View.GONE);
        buyhedetail_root.setBackgroundResource(R.mipmap.a_bg);
        buyHePresenter = new BuyHePresenter(this,mContext);
        buyHePresenter.postGetSchemeDetail(schemeId);
        AndroidBug5497Workaround.assistActivity(this);
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {
        schemeId = extras.getString("schemeId");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_buyhedetail;
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

    @Override
    public void showGetHeListSuccess(BuyHeEntry entry) {

    }

    @Override
    public void showGetSchemeDetailSuccess(SchemeEntry entry) {
        if (entry.commEn.status == Const.STATUS_SUCCESS){
            if ("0".equals(entry.status)){
                buyhe_bottom_rl.setVisibility(View.VISIBLE);
            }else{
                buyhe_bottom_rl.setVisibility(View.GONE);
            }
            lottery_name.setText(entry.lotteryTypeName);

            String ss = "";
            if ("1".equals(entry.isChase)){
                ss = "[追" + entry.chaseNum + "期]";
            }
            if ("2".equals(entry.schemeType)){
                if (!TextUtils.isEmpty(ss)){
                    ss = ss + ",";
                }
                ss = ss + "[合]";
            }
            lottery_issue.setText("第" + entry.betPeriod + "期" + ss);
            if (TextUtils.isEmpty(entry.headPortrait)){
                Glide.with(mContext).load(R.mipmap.test_logo2).dontAnimate().fitCenter().into(header_civ);
            }else{
                Glide.with(mContext).load(entry.headPortrait).dontAnimate().fitCenter().error(R.mipmap.test_logo2).into(header_civ);
            }
            buyhe_leve.setText("Lv" + entry.userGrade);
            buyhe_username.setText(CommUtil.getCutUserName(entry.userName));
            buy_bottom_totalmoney.setText(mContext.getString(R.string.rmb) + entry.bettingTotailAmount);
            buy_bottom_unitprice.setText(mContext.getString(R.string.rmb) + entry.unitPrice);

            int actualBuyNum = entry.purchasedNum + entry.actualMinNum;
            int left = entry.totalNum - actualBuyNum;
            buy_bottom_leftnum.setText(left + "");
            String degree = StringUtils.floatTo2( 100f * actualBuyNum / entry.totalNum) + "%";
            buy_bottom_degree.setText(degree);
            buyhe_progress.setMax(entry.totalNum);
            buyhe_progress.setProgress(actualBuyNum);

            buy_bottom_baodi.setText(entry.minNum);
            initUser(entry.mUserList);
            initIssue(entry.mIssueList);
            initFz(entry.mListSchemeInfo);

        }
    }

    @Override
    public void showBetPartnershipSuccess(CommEntry entry) {
        if (entry.status == Const.STATUS_SUCCESS){
            showToast("购买成功");
            buyHePresenter.postGetSchemeDetail(schemeId);
        }else{
            showToast(entry.msg);
        }
    }

    @InjectView(R.id.hed_user_ll)
    LinearLayout hed_user_ll;
    @InjectView(R.id.bd_issue_ll)
    LinearLayout bd_issue_ll;
    @InjectView(R.id.bh_fa)
    LinearLayout bh_fa;
    private void initUser(List<HeBuyUserEntry> mUserList){
        hed_user_ll.removeAllViews();
        if (mUserList == null || mUserList.isEmpty()){
            return;
        }
        for ( int i=0;i<mUserList.size();i++){
            View root = View.inflate(mContext,R.layout.layout_buyhe_de_rgyh,null);
            TextView rgyh_name = root.findViewById(R.id.rgyh_name);
            TextView rgyh_winmoney = root.findViewById(R.id.rgyh_winmoney);
            TextView rgyh_betmoney = root.findViewById(R.id.rgyh_betmoney);
            TextView rgyh_degree = root.findViewById(R.id.rgyh_degree);
            HeBuyUserEntry userEntry = mUserList.get(i);
            rgyh_name.setText(CommUtil.getCutUserName(userEntry.userName));
            rgyh_winmoney.setText(getString(R.string.rmb) + userEntry.winningAmount + "");
            rgyh_betmoney.setText(getString(R.string.rmb) + userEntry.betMoney);
            rgyh_degree.setText(StringUtils.floatTo2(1f * userEntry.betNum / userEntry.totalNum * 100) + "%");
            hed_user_ll.addView(root);
        }
    }

    private void initIssue(List<HeBuyDoEntry> mUserList){
        bd_issue_ll.removeAllViews();
        if (mUserList == null || mUserList.isEmpty()){
            return;
        }
        for ( int i=0;i<mUserList.size();i++){
            View root = View.inflate(mContext,R.layout.layout_buyhe_de_zxqk,null);
            TextView axqk_issue = root.findViewById(R.id.axqk_issue);
            TextView axqk_betmutt = root.findViewById(R.id.axqk_betmutt);
            TextView axqk_status = root.findViewById(R.id.axqk_status);
            HeBuyDoEntry userEntry = mUserList.get(i);
            axqk_issue.setText("第" + userEntry.periods + "期");
            axqk_betmutt.setText(userEntry.betMultiple);
            axqk_status.setText(CommUtil.getStatusText(userEntry.status));
            if ("4".equals(userEntry.status)){
                axqk_status.setTextColor(mContext.getResources().getColor(R.color.color_text_red));
            }else if ("0".equals(userEntry.status) || "1".equals(userEntry.status)){
                axqk_status.setTextColor(mContext.getResources().getColor(R.color.color_text_green));
            }else{
                axqk_status.setTextColor(mContext.getResources().getColor(R.color.color_text_main_black));
            }
            bd_issue_ll.addView(root);
            root.setTag(userEntry);
            root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    HeBuyDoEntry  en = (HeBuyDoEntry) view.getTag();
                    MenuDialog3 menu = new MenuDialog3();
                    menu.ShowDialog(BuyHeDetailActivity.this,en,null);
                }
            });
        }
    }

    private void initFz(List<SchemeInfoEntry> mUserList){
        bh_fa.removeAllViews();
        if (mUserList == null || mUserList.isEmpty()){
            return;
        }
        for ( int i=0;i<mUserList.size();i++){
            View root = View.inflate(mContext,R.layout.buyhe_fa,null);
            TextView buyhed_num = root.findViewById(R.id.buyhed_num);
            SchemeInfoEntry userEntry = mUserList.get(i);
            buyhed_num.setText("选号：[" + userEntry.playedName + "]" + userEntry.betContent);
            bh_fa.addView(root);
        }
    }
    @Override
    public void pullRefreshOver() {

    }

    @OnClick({R.id.count_reduce_iv,R.id.count_add_iv,R.id.buyhe_ok})
    public void onclick(View v){
        String input = count_input_tv.getText().toString();
        switch (v.getId()){
            case R.id.count_reduce_iv:
                int count = CommUtil.stringToInt(input);
                if (count > 1){
                    count_input_tv.setText((count - 1) + "");
                }
                break;
            case R.id.count_add_iv:
                count_input_tv.setText((CommUtil.stringToInt(input) + 1) + "");
                break;
            case R.id.buyhe_ok:
                if (TextUtils.isEmpty(input)){
                    showToast("请输入购买数量");
                }else{
                    buyHePresenter.postBetPartnership(schemeId,input);
                }

                break;
        }
    }
}
