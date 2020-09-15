package com.xhh.ticketver2.ui.adapter.homebuyhe;

import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.BuyHeEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.ui.adapter.base.BaseMultiAdapter;
import com.xhh.ticketver2.ui.adapter.base.SuperViewHolder;
import com.xhh.ticketver2.utils.CommUtil;
import com.cc.util.code.StringUtils;
import com.cc.util.yc.DensityUtil;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Author:    hup
 * Date:      2017/3/28.
 * Description:
 */

public class HomeBuyHeAdapter extends BaseMultiAdapter<BuyHeEntry> implements View.OnClickListener {

    private  int mScreenWidth;
    private DensityUtil densityUtil;
    onItemClickListenter mOnItemClick;
    public HomeBuyHeAdapter(Context context, int screenWidth , onItemClickListenter click) {
        super(context);
        addItemType(Const.TYPE_01, R.layout.item_homebuyhe);
        mScreenWidth = screenWidth;
        densityUtil = new DensityUtil(mContext);
        mOnItemClick = click;
    }
    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        BuyHeEntry item = getDataList().get(position);
        switch (item.getItemType()){
            case Const.TYPE_01:
                View item_buyhe_root = holder.getView(R.id.item_buyhe_root);
                item_buyhe_root.setTag(position);
                item_buyhe_root.setOnClickListener(this);

                TextView lottery_name = holder.getView(R.id.lottery_name);
                TextView lottery_issue = holder.getView(R.id.lottery_issue);
                TextView lottery_status = holder.getView(R.id.lottery_status);
                CircleImageView header_civ = holder.getView(R.id.header_civ);
                TextView buyhe_leve = holder.getView(R.id.buyhe_leve);
                TextView buyhe_username = holder.getView(R.id.buyhe_username);
                ContentLoadingProgressBar buyhe_progress = holder.getView(R.id.buyhe_progress);
                TextView buy_bottom_totalmoney = holder.getView(R.id.buy_bottom_totalmoney);
                TextView buy_bottom_unitprice = holder.getView(R.id.buy_bottom_unitprice);
                TextView buy_bottom_leftnum = holder.getView(R.id.buy_bottom_leftnum);
                TextView buy_bottom_degree = holder.getView(R.id.buy_bottom_degree);
                TextView buy_bottom_baodi = holder.getView(R.id.buy_bottom_baodi);
                FrameLayout layout_item_buy_bottom_buy_ll = holder.getView(R.id.layout_item_buy_bottom_buy_ll);
                layout_item_buy_bottom_buy_ll.setTag(position);
                layout_item_buy_bottom_buy_ll.setOnClickListener(this);
                lottery_name.setText(item.lotteryTypeName);

                String status = item.statusStr;
                if (TextUtils.isEmpty(status)){
                    status = "";
                }else{
                    status = "-[" + status + "]";
                }
                lottery_status.setText(status);
                if ("4".equals(item.status)){
                    lottery_status.setTextColor(mContext.getResources().getColor(R.color.color_text_red));
                }else if ("0".equals(item.status) || "1".equals(item.status)){
                    lottery_status.setTextColor(mContext.getResources().getColor(R.color.color_text_green));
                }else{
                    lottery_status.setTextColor(mContext.getResources().getColor(R.color.color_text_main_black));
                }
                if (TextUtils.isEmpty(item.chaseNum)){
                    lottery_issue.setText("第" + item.betPeriod + "期");
                }else{
                    lottery_issue.setText("第" + item.betPeriod + "期[追" + item.chaseNum + "期]");
                }
                if (TextUtils.isEmpty(item.headPortrait)){
                    Glide.with(mContext).load(R.mipmap.test_logo2).dontAnimate().fitCenter().into(header_civ);
                }else{
                    Glide.with(mContext).load(item.headPortrait).dontAnimate().fitCenter().into(header_civ);
                }
                buyhe_username.setText(CommUtil.getCutUserName(item.userName));
                buy_bottom_totalmoney.setText(mContext.getString(R.string.rmb) + item.bettingTotailAmount);
                buy_bottom_unitprice.setText(mContext.getString(R.string.rmb) + item.unitPrice);
                int actualBuyNum = item.purchasedNum + item.actualMinNum;
                int left = item.totalNum - actualBuyNum;
                buy_bottom_leftnum.setText(left + "");
                String degree = StringUtils.floatTo2( 100f * actualBuyNum / item.totalNum) + "%";
                buy_bottom_degree.setText(degree);
                buyhe_progress.setMax(item.totalNum);
                buyhe_progress.setProgress(actualBuyNum);

                buyhe_leve.setText("Lv" + item.userGrade);
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_buyhe_root:
                mOnItemClick.onItemClick((Integer) v.getTag());
                break;
            case R.id.layout_item_buy_bottom_buy_ll:
                mOnItemClick.onBuy((Integer) v.getTag());
                break;
        }
    }

    public interface onItemClickListenter {
        public void onItemClick(int pos);
        public void onBuy(int pos);
    }
}
