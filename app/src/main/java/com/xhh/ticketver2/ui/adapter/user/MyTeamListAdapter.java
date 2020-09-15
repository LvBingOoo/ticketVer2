package com.xhh.ticketver2.ui.adapter.user;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.MyTeamListBean;
import com.xhh.ticketver2.ui.adapter.base.BaseMultiAdapter;
import com.xhh.ticketver2.ui.adapter.base.SuperViewHolder;

import java.util.List;

public class MyTeamListAdapter extends RecyclerView.Adapter<MyTeamListAdapter.ViewHolder> {
    private Context context;
    private List<MyTeamListBean.ObjBean.RowsBean> dataList;

    public MyTeamListAdapter(Context context) {
        this.context = context;
    }

    public void setDataList(List<MyTeamListBean.ObjBean.RowsBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_myteam, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MyTeamListBean.ObjBean.RowsBean rowsBean = dataList.get(position);
        holder.tvName.setText(rowsBean.getUserName());
        holder.tvQQ.setText(rowsBean.getQq());
        holder.tvRecharge.setText(rowsBean.getAvailableFund());
        holder.tvWithOut.setText(rowsBean.getBetTask());
        String stateStr = "";
        int stateColor;
        switch (rowsBean.getStatus()) {
            case 0:
                stateStr = "停用";
                stateColor = Color.BLACK;
                break;
            case 1:
                stateStr = "正常";
                stateColor = Color.GREEN;
                break;
            default:
                stateStr = "未知";
                stateColor = Color.GRAY;
                break;
        }
        holder.tvState.setText(stateStr);
        holder.tvState.setTextColor(stateColor);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvState;
        TextView tvQQ;
        TextView tvRecharge;
        TextView tvWithOut;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvState = itemView.findViewById(R.id.tvState);
            tvQQ = itemView.findViewById(R.id.tvQQ);
            tvRecharge = itemView.findViewById(R.id.tvRecharge);
            tvWithOut = itemView.findViewById(R.id.tvWithOut);
        }
    }
}
