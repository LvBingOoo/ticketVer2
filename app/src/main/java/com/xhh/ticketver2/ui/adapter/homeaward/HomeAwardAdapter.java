package com.xhh.ticketver2.ui.adapter.homeaward;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.AwardEntry;
import com.xhh.ticketver2.beans.NumEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.ui.adapter.base.BaseMultiAdapter;
import com.xhh.ticketver2.ui.adapter.base.SuperViewHolder;
import com.cc.util.yc.DensityUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Author:    hup
 * Date:      2017/3/28.
 * Description:
 */

public class HomeAwardAdapter extends BaseMultiAdapter<AwardEntry> implements View.OnClickListener, BallAdapter.onItemClickListenter {

    private  int mScreenWidth;
    private DensityUtil densityUtil;
    onItemClickListenter mOnItemClick;
    public HomeAwardAdapter(Context context, int screenWidth , onItemClickListenter click) {
        super(context);
        addItemType(Const.TYPE_01, R.layout.item_homeaward);
        mScreenWidth = screenWidth;
        densityUtil = new DensityUtil(mContext);
        mOnItemClick = click;
    }
    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        AwardEntry item = getDataList().get(position);
        switch (item.getItemType()){
            case Const.TYPE_01:
                FrameLayout item_homearward_fl = holder.getView(R.id.item_homearward_fl);
                item_homearward_fl.setTag(position);
                item_homearward_fl.setOnClickListener(this);
                if (position == 0){
                    item_homearward_fl.setBackgroundResource(R.drawable.bg_rect_corners_home_award_1_top_icon);
                }else if (position == 1){
                    item_homearward_fl.setBackgroundResource(R.drawable.bg_rect_corners_home_award_2_top_icon);
                }else{
                    item_homearward_fl.setBackgroundColor(mContext.getResources().getColor(R.color.white));
                }

                TextView item_homeaward_name = holder.getView(R.id.item_homeaward_name);
                TextView item_homeaward_issue = holder.getView(R.id.item_homeaward_issue);
                item_homeaward_name.setText(item.lotteryTypeName);
                item_homeaward_issue.setText("第" + item.period + "期 开奖号码");

                RecyclerView mLRecyclerView = holder.getView(R.id.item_award_recyclerview);
                item_homearward_fl.setTag(position);
                mLRecyclerView.setOnClickListener(this);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 5);
                mLRecyclerView.setLayoutManager(gridLayoutManager);
                BallAdapter gridAdapter = new BallAdapter(mContext, mScreenWidth, this,position,false);
                List<NumEntry> temp = new ArrayList<>();
                String [] dwawNumber = item.dwawNumber.split(",");
                for (int i=0;i<dwawNumber.length;i++){
                    NumEntry ballEntry = new NumEntry();
                    ballEntry.num = dwawNumber[i];
                    temp.add(ballEntry);
                }
                gridAdapter.setDataList(temp);
                mLRecyclerView.setAdapter(gridAdapter);
                mLRecyclerView.setFocusableInTouchMode(false);
                mLRecyclerView.requestFocus();
                View item_homeaward_right_fl = holder.getView(R.id.item_homeaward_right_fl);
                item_homeaward_right_fl.setVisibility(View.GONE);
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
