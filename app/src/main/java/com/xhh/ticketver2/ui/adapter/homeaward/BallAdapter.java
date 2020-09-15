package com.xhh.ticketver2.ui.adapter.homeaward;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.NumEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.ui.adapter.base.BaseMultiAdapter;
import com.xhh.ticketver2.ui.adapter.base.SuperViewHolder;
import com.cc.util.yc.DensityUtil;


/**
 * Author:    hup
 * Date:      2017/3/28.
 * Description:
 */

public class BallAdapter extends BaseMultiAdapter<NumEntry> implements View.OnClickListener {

    private  int mScreenWidth;
    private DensityUtil densityUtil;
    onItemClickListenter mOnItemClick;
    private int mPrarentPos = 0;
    public BallAdapter(Context context, int screenWidth , onItemClickListenter click, int pos,boolean isSmall) {
        super(context);
        if (isSmall){
            addItemType(Const.TYPE_01, R.layout.item_ball_small);
        }else{
            addItemType(Const.TYPE_01, R.layout.item_ball);
        }
        mScreenWidth = screenWidth;
        densityUtil = new DensityUtil(mContext);
        mOnItemClick = click;
        mPrarentPos = pos;
    }
    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        NumEntry item = getDataList().get(position);
        switch (item.getItemType()){
            case Const.TYPE_01:
                View rootView = holder.getView(R.id.ball_root);
                rootView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemClick != null){
                            mOnItemClick.onItemClick(mPrarentPos,position);
                        }
                    }
                });
                TextView item_ball_num = holder.getView(R.id.item_ball_num);
                if (!TextUtils.isEmpty(item.num)){
                    item_ball_num.setText(item.num);
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
    }

    public interface onItemClickListenter {
        public void onItemClick(int parent ,int pos);
    }
}
