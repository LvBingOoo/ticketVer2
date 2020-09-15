package com.xhh.ticketver2.ui.adapter.user;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.ChongTiEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.ui.adapter.base.BaseMultiAdapter;
import com.xhh.ticketver2.ui.adapter.base.SuperViewHolder;
import com.cc.util.yc.DensityUtil;


/**
 * Author:    hup
 * Date:      2017/3/28.
 * Description:
 */

public class ChongZhiJiLvAdapter extends BaseMultiAdapter<ChongTiEntry> implements View.OnClickListener {

    private  int mScreenWidth;
    private DensityUtil densityUtil;
    onItemClickListenter mOnItemClick;
    String flag;
    public ChongZhiJiLvAdapter(Context context, int screenWidth , onItemClickListenter click,String flag) {
        super(context);
        addItemType(Const.TYPE_01, R.layout.item_chongzhijilv);
        mScreenWidth = screenWidth;
        densityUtil = new DensityUtil(mContext);
        mOnItemClick = click;
        this.flag = flag;
    }
    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        ChongTiEntry item = getDataList().get(position);
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
                TextView item_ct_status = holder.getView(R.id.item_ct_status);

                item_ct_type.setText(item.rechargeChannelStr);
                if ("1".equals(flag)){
                    item_ct_money.setText("+" + item.rechargeAmount);
                }else{
                    item_ct_money.setText("-" + item.rechargeAmount);
                }
                item_ct_time.setText(item.createTimeStr);
                item_ct_status.setText(item.statusStr);
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
