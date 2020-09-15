package com.xhh.ticketver2.ui.adapter.homebuy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.HomeBuyEntry;
import com.xhh.ticketver2.ui.adapter.base.BaseMultiAdapter;
import com.xhh.ticketver2.ui.adapter.base.SuperViewHolder;
import com.xhh.ticketver2.ui.homebuy.BuyDetailActivity;
import com.cc.util.yc.DensityUtil;


/**
 * Author:    hup
 * Date:      2017/3/28.
 * Description:
 */

public class HomeBuyGridAdapter extends BaseMultiAdapter<HomeBuyEntry> implements View.OnClickListener {

    private  int mScreenWidth;
    private DensityUtil densityUtil;
    onItemClickListenter mOnItemClick;
    private int mPrarentPos = 0;
    public HomeBuyGridAdapter(Context context, int screenWidth , onItemClickListenter click, int pos) {
        super(context);
        addItemType(Const.TYPE_01, R.layout.item_bugygride);
        mScreenWidth = screenWidth;
        densityUtil = new DensityUtil(mContext);
        mOnItemClick = click;
        mPrarentPos = pos;
    }
    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        HomeBuyEntry item = getDataList().get(position);
        switch (item.getItemType()){
            case Const.TYPE_01:
                View rootView = holder.getView(R.id.gride_root);
                rootView.setTag(position);
                rootView.setOnClickListener(this);

                TextView grid_name = holder.getView(R.id.grid_name);
                TextView grid_area = holder.getView(R.id.grid_area);
                ImageView item_buygride_iv = holder.getView(R.id.item_buygride_iv);
                grid_name.setText(item.lotteryTypeName);
                grid_area.setText(item.describes);
                Glide.with(mContext).load(item.icon).fitCenter().dontAnimate().into(item_buygride_iv);

                break;
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(mContext, BuyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("lotteryTypeId",getDataList().get((Integer) v.getTag()).lotteryTypeId);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
    }

    public interface onItemClickListenter {
        public void onItemClick(int pos);
    }
}
