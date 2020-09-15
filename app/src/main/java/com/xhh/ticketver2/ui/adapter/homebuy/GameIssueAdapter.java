package com.xhh.ticketver2.ui.adapter.homebuy;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.GameInfoEntry;
import com.xhh.ticketver2.ui.adapter.base.BaseMultiAdapter;
import com.xhh.ticketver2.ui.adapter.base.SuperViewHolder;
import com.xhh.ticketver2.utils.CommUtil;
import com.cc.util.yc.DensityUtil;

import java.util.List;


/**
 * Author:    hup
 * Date:      2017/3/28.
 * Description:
 */

public class GameIssueAdapter extends BaseMultiAdapter<GameInfoEntry> {

    private  int mScreenWidth;
    private DensityUtil densityUtil;
    onItemClickListenter mOnItemClick;
    private int mPrarentPos = 0;
    int[] arrMoneyAndNum;
    public GameIssueAdapter(Context context, int screenWidth , onItemClickListenter click, int pos,int[] arrMoneyAndNum) {
        super(context);
        addItemType(Const.TYPE_01, R.layout.item_gameissue);
        mScreenWidth = screenWidth;
        densityUtil = new DensityUtil(mContext);
        mOnItemClick = click;
        mPrarentPos = pos;
        this.arrMoneyAndNum = arrMoneyAndNum;
    }
    public void setNum(int num){
        List<GameInfoEntry>  list = getDataList();
        for (int i=0;i<list.size();i++){
            list.get(i).multipleInput = num;
        }
        notifyDataSetChanged();
    }
    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        final GameInfoEntry item = getDataList().get(position);
        switch (item.getItemType()){
            case Const.TYPE_01:
                TextView item_issue_tv = holder.getView(R.id.item_issue_tv);
                final EditText item_issue_mutt_et = holder.getView(R.id.item_issue_mutt_et);
                final TextView item_money_tv = holder.getView(R.id.item_money_tv);
                TextView item_time_tv = holder.getView(R.id.item_time_tv);
                item_issue_tv.setText(item.issue);
                item_time_tv.setText(item.end_time);
                item_money_tv.setText(arrMoneyAndNum[0] * item.multipleInput + "元");
                item_issue_mutt_et.setText(item.multipleInput + "");

                item_issue_mutt_et.setTag(position);
                item_issue_mutt_et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        int num = (int) item_issue_mutt_et.getTag();
                        getDataList().get(num).multipleInput = CommUtil.stringToInt(s.toString());
                        item_money_tv.setText(arrMoneyAndNum[0] * CommUtil.stringToInt(s.toString())+ "元");
                    }
                });
                break;
        }
    }


    public interface onItemClickListenter {
        public void onItemClick(int parent, int pos);
    }
}
