package com.xhh.ticketver2.ui.adapter.homeaward;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.AwardEntry;
import com.xhh.ticketver2.beans.NumEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.ui.adapter.base.BaseMultiAdapter;
import com.xhh.ticketver2.ui.adapter.base.SuperViewHolder;
import com.cc.DataUitl;
import com.cc.util.yc.DensityUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Author:    hup
 * Date:      2017/3/28.
 * Description:
 */

public class AwardRecordAdapter extends BaseMultiAdapter<AwardEntry> implements View.OnClickListener {

    private  int mScreenWidth;
    private DensityUtil densityUtil;
    onItemClickListenter mOnItemClick;
    public AwardRecordAdapter(Context context, int screenWidth , onItemClickListenter click) {
        super(context);
        addItemType(Const.TYPE_01, R.layout.item_awardrecord);
        mScreenWidth = screenWidth;
        densityUtil = new DensityUtil(mContext);
        mOnItemClick = click;
    }
    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        AwardEntry item = getDataList().get(position);
        switch (item.getItemType()){
            case Const.TYPE_01:
                TextView item_awardrecord_ing_tv = holder.getView(R.id.item_awardrecord_ing_tv);
                TextView item_awardrecord_issue = holder.getView(R.id.item_awardrecord_issue);
                TextView item_awardrecord_opentime = holder.getView(R.id.item_awardrecord_opentime);
                RecyclerView mLRecyclerView = holder.getView(R.id.item_awardrecord_recyclerview);

                if (!TextUtils.isEmpty(item.period)){
                    item_awardrecord_issue.setText(item.period);
                }
                if (!TextUtils.isEmpty(item.openTimeStr)){
                    item_awardrecord_opentime.setText(DataUitl.strTostr(item.openTimeStr));
                }
                if (!TextUtils.isEmpty(item.dwawNumber)){
                    String[] drawNumber = item.dwawNumber.split(",");
                    item_awardrecord_ing_tv.setVisibility(View.GONE);
                    mLRecyclerView.setVisibility(View.VISIBLE);
                    LinearLayoutManager gridLayoutManager = new LinearLayoutManager(mContext);
                    gridLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    mLRecyclerView.setLayoutManager(gridLayoutManager);
                    BallAdapter gridAdapter = new BallAdapter(mContext, mScreenWidth, null,position,true);
                    List<NumEntry> temp = new ArrayList<>();
                    for (int i=0;i<drawNumber.length;i++){
                        NumEntry ballEntry = new NumEntry();
                        ballEntry.num = drawNumber[i];
                        temp.add(ballEntry);
                    }
                    gridAdapter.setDataList(temp);
                    mLRecyclerView.setAdapter(gridAdapter);
                }else{
                    item_awardrecord_ing_tv.setVisibility(View.VISIBLE);
                    mLRecyclerView.setVisibility(View.GONE);
                }

                break;
        }
    }
    @Override
    public void onClick(View v) {
    }

    public interface onItemClickListenter {
        public void onItemClick(int pos);
    }
}
