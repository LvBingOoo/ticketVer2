package com.xhh.ticketver2.ui.adapter.user;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.BillEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.ui.adapter.base.BaseMultiAdapter;
import com.xhh.ticketver2.ui.adapter.base.SuperViewHolder;
import com.xhh.ticketver2.utils.CommUtil;
import com.cc.util.yc.DensityUtil;


/**
 * Author:    hup
 * Date:      2017/3/28.
 * Description:
 */

public class BillAdapter extends BaseMultiAdapter<BillEntry> implements View.OnClickListener {

    private  int mScreenWidth;
    private DensityUtil densityUtil;
    onItemClickListenter mOnItemClick;
    public BillAdapter(Context context, int screenWidth , onItemClickListenter click) {
        super(context);
        addItemType(Const.TYPE_01, R.layout.item_bill);
        mScreenWidth = screenWidth;
        densityUtil = new DensityUtil(mContext);
        mOnItemClick = click;
    }
    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        BillEntry item = getDataList().get(position);
        switch (item.getItemType()){
            case Const.TYPE_01:
                View item_chongzhijilv_root = holder.getView(R.id.item_chongzhijilv_root);
                View item_chongzhijilv_top = holder.getView(R.id.item_chongzhijilv_top);
                item_chongzhijilv_root.setTag(position);
                item_chongzhijilv_root.setOnClickListener(this);
                if (position ==0){
                    item_chongzhijilv_top.setBackgroundResource(R.drawable.bg_rect_corners_home_top_icon);
                }else{
                    item_chongzhijilv_top.setBackgroundColor(Color.WHITE);
                }

                TextView item_ct_type = holder.getView(R.id.item_ct_type);
                TextView item_ct_money = holder.getView(R.id.item_ct_money);
                TextView item_ct_time = holder.getView(R.id.item_ct_time);
                item_ct_type.setText(item.acTypeStr);
                item_ct_time.setText(item.createTime);
                double change = CommUtil.stringToDouble(item.afterAvailableFund) - CommUtil.stringToDouble(item.beforeAvailableFund);
                String ss = "";
                if (!item.changeAmount.contains("-")  && !item.changeAmount.contains("+") && change >0){
                    ss = "+";
                }else if (!item.changeAmount.contains("-")  && !item.changeAmount.contains("+") && change < 0){
                    ss = "-";
                }
                item_ct_money.setText(ss + item.changeAmount);

                break;
        }
    }
    @Override
    public void onClick(View v) {
//        mContext.startActivity(new Intent(mContext, ChongZhiJiLvDetailActivity.class));
    }

    public interface onItemClickListenter {
        public void onItemClick(int pos);
    }
}
