package com.xhh.ticketver2.ui.adapter.homebuy;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.NumEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.ShiShiCaiRuleEntry;
import com.xhh.ticketver2.ui.adapter.base.BaseMultiAdapter;
import com.xhh.ticketver2.ui.adapter.base.SuperViewHolder;
import com.cc.util.yc.DensityUtil;

import org.json.JSONObject;


/**
 * Author:    hup
 * Date:      2017/3/28.
 * Description:
 */

public class BallBuyAdapter extends BaseMultiAdapter<NumEntry> implements View.OnClickListener {

    private  int mScreenWidth;
    private DensityUtil densityUtil;
    onItemClickListenter mOnItemClick;
    private int mPrarentPos = 0;
    JSONObject joNumberProp;
    ShiShiCaiRuleEntry ruleEntry;
    public BallBuyAdapter(Context context, int screenWidth , onItemClickListenter click, int pos) {
        super(context);
        addItemType(Const.TYPE_01, R.layout.item_ball_buy);
        mScreenWidth = screenWidth;
        densityUtil = new DensityUtil(mContext);
        mOnItemClick = click;
        mPrarentPos = pos;
    }
    public void setPropMis(JSONObject joNumberProp){
        this.joNumberProp = joNumberProp;
    }
    public void setRules(ShiShiCaiRuleEntry ruleEntry){
        this.ruleEntry = ruleEntry;
    }
    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        NumEntry item = getDataList().get(position);
        switch (item.getItemType()){
            case Const.TYPE_01:
                TextView item_ball_buy_num = holder.getView(R.id.item_ball_buy_num);
                TextView ball_buy_miss = holder.getView(R.id.ball_buy_miss);
                FrameLayout item_ball_buy_fl = holder.getView(R.id.item_ball_buy_fl);
                item_ball_buy_fl.setTag(position);
                item_ball_buy_fl.setOnClickListener(this);
                if (item.isSelect){
                    item_ball_buy_fl.setSelected(true);
                }else{
                    item_ball_buy_fl.setSelected(false);
                }
                item_ball_buy_num.setText(item.num);
                String omitCode = ruleEntry.omitCode;
                String  yilou = getNumberProp(omitCode,item.num);
                if(!TextUtils.isEmpty(yilou) && !TextUtils.isEmpty(omitCode)){
                    ball_buy_miss.setText(yilou);
                } else {
                    ball_buy_miss.setText("--");
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        int pos = (int) v.getTag();
        if (mDataList.get(pos).isSelect){
            mDataList.get(pos).isSelect = false;
            notifyDataSetChanged();
        }else{
            mOnItemClick.onItemClick(mPrarentPos,pos);
        }
    }

    public interface onItemClickListenter {
        void onItemClick(int parent, int pos);
    }
    public String getNumberProp(String omitCode,String num){
        String currentOmit = "";
        try {
            if (joNumberProp != null){
                JSONObject obj = joNumberProp.getJSONObject("obj");
                if (obj.has(omitCode)){
                    JSONObject jo = obj.getJSONObject(omitCode);
                    JSONObject numJo = jo.getJSONObject(num);
                    currentOmit = numJo.getString("currentOmit");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  currentOmit;
    }
}
