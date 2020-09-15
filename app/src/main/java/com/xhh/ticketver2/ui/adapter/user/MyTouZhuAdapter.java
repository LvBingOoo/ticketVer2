package com.xhh.ticketver2.ui.adapter.user;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.TouZhuEntry;
import com.xhh.ticketver2.ui.adapter.base.BaseMultiAdapter;
import com.xhh.ticketver2.ui.adapter.base.SuperViewHolder;
import com.xhh.ticketver2.ui.adapter.homeaward.BallAdapter;
import com.cc.util.yc.DensityUtil;


/**
 * Author:    hup
 * Date:      2017/3/28.
 * Description:
 */

public class MyTouZhuAdapter extends BaseMultiAdapter<TouZhuEntry> implements View.OnClickListener, BallAdapter.onItemClickListenter {

    private  int mScreenWidth;
    private DensityUtil densityUtil;
    onItemClickListenter mOnItemClick;
    public MyTouZhuAdapter(Context context, int screenWidth , onItemClickListenter click) {
        super(context);
        addItemType(Const.TYPE_01, R.layout.item_mytouzhu);
        mScreenWidth = screenWidth;
        densityUtil = new DensityUtil(mContext);
        mOnItemClick = click;
    }
    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        TouZhuEntry item = getDataList().get(position);
        switch (item.getItemType()){
            case Const.TYPE_01:
                LinearLayout item_homearward_fl = holder.getView(R.id.item_homearward_fl);
                item_homearward_fl.setTag(position);
                item_homearward_fl.setOnClickListener(this);
                if (position == 0){
                    item_homearward_fl.setBackgroundResource(R.drawable.bg_rect_corners_home_top_icon);
                }else{
                    item_homearward_fl.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }

                TextView item_tz_name = holder.getView(R.id.item_tz_name);
                TextView item_tz_betamount = holder.getView(R.id.item_tz_betamount);
                TextView item_tz_status = holder.getView(R.id.item_tz_status);
                TextView item_tz_issue = holder.getView(R.id.item_tz_issue);
                TextView item_tz_winamount = holder.getView(R.id.item_tz_winamount);
                TextView item_tz_bettime = holder.getView(R.id.item_tz_bettime);
                item_tz_name.setText(item.lotteryTypeName);
                item_tz_betamount.setText(item.betMoney + "元");
                item_tz_status.setText(item.statusStr);
                if ("5".equals(item.status)){
                    item_tz_status.setBackgroundResource(R.drawable.bg_rect_corners_gray3);
                }else if ("4".equals(item.status)){
                    item_tz_status.setBackgroundResource(R.drawable.bg_rect_corners_orange);
                }else{
                    item_tz_status.setBackgroundResource(R.drawable.bg_rect_corners_blue);
                }
                String ss = "";
                if ("1".equals(item.isChase)){
                    ss = "[追" + item.chaseNum + "期]";
                }
                if ("2".equals(item.schemeType)){
                    if (!TextUtils.isEmpty(ss)){
                        ss = ss + ",";
                    }
                    ss = ss + "[合]";
                }
                item_tz_issue.setText("第" + item.betPeriod + "期" + ss);
                if ("0".equals(item.winningAmount)){
                    item_tz_winamount.setText("");
                }else{
                    item_tz_winamount.setText("赚" + mContext.getString(R.string.rmb) + item.winningAmount);
                }
                item_tz_bettime.setText(item.betTime);
                break;
        }
    }
    @Override
    public void onClick(View v) {
        mOnItemClick.onItemClick((Integer) v.getTag());
    }

    @Override
    public void onItemClick(int parent, int pos) {
        mOnItemClick.onItemClick(parent);
    }

    public interface onItemClickListenter {
        public void onItemClick(int pos);
    }
}
