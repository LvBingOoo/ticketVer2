package com.xhh.ticketver2.ui.adapter.homebuy;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.GameListEntry;
import com.xhh.ticketver2.ui.adapter.base.BaseMultiAdapter;
import com.xhh.ticketver2.ui.adapter.base.SuperViewHolder;
import com.cc.util.yc.DensityUtil;


/**
 * Author:    hup
 * Date:      2017/3/28.
 * Description:
 */

public class GameTypeAdapter extends BaseMultiAdapter<GameListEntry> implements View.OnClickListener {

    private  int mScreenWidth;
    private DensityUtil densityUtil;
    onItemClickListenter mOnItemClick;
    private int mPos = 0;
    public GameTypeAdapter(Context context, int screenWidth , onItemClickListenter click, int pos) {
        super(context);
        addItemType(Const.TYPE_01, R.layout.item_gametype);
        mScreenWidth = screenWidth;
        densityUtil = new DensityUtil(mContext);
        mOnItemClick = click;
        mPos = pos;
    }
    public int getIndexPos(){
        return mPos;
    }
    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        GameListEntry item = getDataList().get(position);
        switch (item.getItemType()){
            case Const.TYPE_01:
                TextView item_ball_buy_name = holder.getView(R.id.item_ball_buy_name);
                FrameLayout item_ball_buy_fl = holder.getView(R.id.item_ball_buy_fl);
                item_ball_buy_name.setText(item.fullName);
                item_ball_buy_fl.setTag(position);
                item_ball_buy_fl.setOnClickListener(this);
                if (mPos == position){
                    item_ball_buy_fl.setSelected(true);
                }else{
                    item_ball_buy_fl.setSelected(false);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag();
        mPos = pos;
        notifyDataSetChanged();
    }

    public interface onItemClickListenter {
        public void onItemClick(int parent, int pos);
    }
}
