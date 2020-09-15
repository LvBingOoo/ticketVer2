package com.xhh.ticketver2.ui.adapter.homebuy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.GameListEntry;
import com.xhh.ticketver2.beans.ShiShiCaiEntry;
import com.xhh.ticketver2.ui.adapter.base.BaseMultiAdapter;
import com.xhh.ticketver2.ui.adapter.base.SuperViewHolder;
import com.xhh.ticketver2.utils.CommUtil;
import com.xhh.ticketver2.utils.MRule;
import com.cc.util.yc.DensityUtil;

import org.json.JSONObject;

import java.util.List;


/**
 * Author:    hup
 * Date:      2017/3/28.
 * Description:
 */

public class BuyDetailAdapter extends BaseMultiAdapter<ShiShiCaiEntry> implements View.OnClickListener{

    int mScreenWidth;
    DensityUtil densityUtil;
    OnCallBack mCallBack;
    boolean issueIsSelect ;
    String tzfsFlag = "1"; //1 单买  1合买
    JSONObject joNumberProp;
    List<GameListEntry> mHasBetList;
    int mCurrentMultiple = 1;
    int totalIssue;
    int totalIssueMoney;
    int[] monyAndNum ;
    String mHeBuyNum = "";
    String mHeBaoDiNum = "";
    public BuyDetailAdapter(Context context, int screenWidth , OnCallBack click) {
        super(context);
        addItemType(Const.TYPE_01, R.layout.item_buy_detail_type1);
        addItemType(Const.TYPE_02, R.layout.item_buy_detail_type2);
        mScreenWidth = screenWidth;
        densityUtil = new DensityUtil(mContext);
        mCallBack = click;
    }
    public void setPropMis(JSONObject joNumberProp){
        this.joNumberProp = joNumberProp;
    }
    public void setBetList(List<GameListEntry> mHasBetList){
        this.mHasBetList = mHasBetList;
        monyAndNum = MRule.calculateBetMoneyAndNum(mHasBetList);
        reSetButtomData();
        notifyDataSetChanged();
    }
    public int[] getBetMoneyAndrNum(){
        return monyAndNum = MRule.calculateBetMoneyAndNum(mHasBetList);
    }
    public void setTottalIssue(int totalIssue,int totalIssueMoney){
        this.totalIssue = totalIssue;
        this.totalIssueMoney = totalIssueMoney;
        issueIsSelect = true;
        tzfsFlag = "1";
        notifyDataSetChanged();
    }
    public int getTottalIssue(){
        if (issueIsSelect){
            return totalIssue;
        }else{
            return 0;
        }
    }
    public void setHeOrDan(String tzfsFlag,String mHeBuyNum,String mHeBaoDiNum){
        this.tzfsFlag = tzfsFlag;
        this.mHeBuyNum = mHeBuyNum;
        this.mHeBaoDiNum = mHeBaoDiNum;
        notifyDataSetChanged();
    }
    public String[] getHeBuyNumAndBaodiNum(){
        String[] arr = {mHeBuyNum,mHeBaoDiNum};
        return arr;
    }
    public int getCurrentMultiple(){
        return mCurrentMultiple;
    }
    public int getTotalMoney(){
        if (!issueIsSelect){
            return monyAndNum == null ? 0 : monyAndNum[0] * mCurrentMultiple;
        }else{
            return totalIssueMoney;
        }
    }
    public int getTotalShowMoney(){
        if ("1".endsWith(tzfsFlag)){
            return getTotalMoney();
        }else{
            return CommUtil.stringToInt(mHeBuyNum) +CommUtil.stringToInt(mHeBaoDiNum);
        }
    }
    public void reSetButtomData(){
        tzfsFlag = "1";
        issueIsSelect = false;
        notifyDataSetChanged();
    }
    public boolean getIsIssue(){
        return issueIsSelect;
    }
    public String getIsBuyOrHeBuy(){
        return tzfsFlag;
    }
    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        ShiShiCaiEntry item = getDataList().get(position);
        switch (item.getItemType()){
            case Const.TYPE_01:
                LinearLayout item_buy_type1_tools_ll = holder.getView(R.id.item_buy_type1_tools_ll);
                initToolsBar(item_buy_type1_tools_ll,item.selectTools,position);
                RecyclerView mLRecyclerViewType1 = holder.getView(R.id.item_recyclerview_type01);
                TextView item_buydetail_type1_name = holder.getView(R.id.item_buydetail_type1_name);
                item_buydetail_type1_name.setText(item.name);

                LinearLayoutManager manager = new LinearLayoutManager(mContext);
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                mLRecyclerViewType1.setLayoutManager(manager);
                BallBuyAdapter gridAdapterType1 = new BallBuyAdapter(mContext, mScreenWidth, new BallBuyAdapter.onItemClickListenter() {
                    @Override
                    public void onItemClick(int parent, int pos) {
                        mCallBack.onCallBack(Const.FLAG_BUY_DETAL_NUM_SELECTE,parent,pos);
                    }
                }, position);
                gridAdapterType1.setPropMis(joNumberProp);
                gridAdapterType1.setRules(item.rule);
                gridAdapterType1.setDataList(item.nums);
                mLRecyclerViewType1.setAdapter(gridAdapterType1);

                break;
                case Const.TYPE_02:
                    View item_buy_type2_clear_list = holder.getView(R.id.item_buy_type2_clear_list);
                    View item_buy_type2_add_list = holder.getView(R.id.item_buy_type2_add_list);

                    View buy_ok = holder.getView(R.id.buy_ok);
                    buy_ok.setOnClickListener(this);

                    TextView item_buy_detail_type2_issue_tv = holder.getView(R.id.item_buy_detail_type2_issue_tv);
                    ImageView item_buy_detail_type2_issue_iv = holder.getView(R.id.item_buy_detail_type2_issue_iv);
                    View item_buy_detail_type2_tzfs = holder.getView(R.id.item_buy_detail_type2_tzfs);
                    TextView item_buy_detail_type2_tzfs_tv = holder.getView(R.id.item_buy_detail_type2_tzfs_tv);

                    TextView bottom_total_money = holder.getView(R.id.bottom_total_money);
                    TextView buy_detail_type2_yx_num = holder.getView(R.id.buy_detail_type2_yx_num);
                    TextView buy_detail_type2_yx_money = holder.getView(R.id.buy_detail_type2_yx_money);
                    if (monyAndNum != null && monyAndNum.length>1){
                        buy_detail_type2_yx_num.setText(monyAndNum[1] * mCurrentMultiple + "注，");
                        buy_detail_type2_yx_money.setText(monyAndNum[0]  * mCurrentMultiple + "元");
                    }

                    LinearLayout item_buy_type2_bet_list = holder.getView(R.id.item_buy_type2_bet_list);
                    initBetList(item_buy_type2_bet_list);

                    ImageView count_reduce_iv = holder.getView(R.id.count_reduce_iv);
                    ImageView count_add_iv = holder.getView(R.id.count_add_iv);
                    final EditText count_input_tv = holder.getView(R.id.count_input_tv);
                    count_reduce_iv.setOnClickListener(this);
                    count_add_iv.setOnClickListener(this);
                    count_input_tv.setText(mCurrentMultiple + "");
                    count_input_tv.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            String num = s.toString();
                            if (TextUtils.isEmpty(num)){
                                count_input_tv.setText("1");
                            }
                            mCurrentMultiple = CommUtil.stringToInt(count_input_tv.getText().toString());
                        }
                    });

                    item_buy_type2_clear_list.setOnClickListener(this);
                    item_buy_type2_add_list.setOnClickListener(this);

                    item_buy_detail_type2_issue_iv.setSelected(issueIsSelect);
                    if (issueIsSelect){
                        item_buy_detail_type2_issue_tv.setVisibility(View.VISIBLE);
                        item_buy_detail_type2_issue_tv.setText("追" + totalIssue + "期，共" + totalIssueMoney + "元");
                    }else{
                        item_buy_detail_type2_issue_tv.setVisibility(View.GONE);
                    }
                    item_buy_detail_type2_issue_iv.setOnClickListener(this);
                    item_buy_detail_type2_tzfs.setOnClickListener(this);
                    if ("1".endsWith(tzfsFlag)){
                        item_buy_detail_type2_tzfs_tv.setText("单买");
                        if (monyAndNum != null && monyAndNum[0] > 0){
                            bottom_total_money.setText("总计" + getTotalShowMoney() + "元");
                        }else{
                            bottom_total_money.setText("");
                        }
                    }else{
                        if (TextUtils.isEmpty(mHeBaoDiNum)){
                            item_buy_detail_type2_tzfs_tv.setText("合买" + mHeBuyNum + "份");
                        }else{
                            item_buy_detail_type2_tzfs_tv.setText("合买" + mHeBuyNum + "份，保底" + mHeBaoDiNum + "份");
                        }
                        bottom_total_money.setText("总计" + getTotalShowMoney() + "元");
                    }


                    break;
        }
    }
    private void initToolsBar(LinearLayout root,String selectTools,int pos){
        root.removeAllViews();
        if (!TextUtils.isEmpty(selectTools)){
            String[] arr = selectTools.split(",");
            for (int i=0;i<arr.length;i++){
                View fl = View.inflate(mContext,R.layout.layout_tools,null);
                TextView txtTv = fl.findViewById(R.id.layout_tools_tv);
                txtTv.setText(arr[i]);
                fl.setTag(pos);
                fl.setTag(R.id.tag,arr[i]);
                fl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pararentPos = (int) v.getTag();
                        String tag = (String) v.getTag(R.id.tag);
                        mCallBack.onCallBackTag(Const.FLAG_BUY_SELECT_TOOLS,pararentPos,tag);
                    }
                });
                root.addView(fl);
            }
        }
    }
    private void initBetList(LinearLayout root){
        root.removeAllViews();
        if (mHasBetList != null && !mHasBetList.isEmpty()){
            for (int i=0;i<mHasBetList.size();i++){
                TextView tipTv= (TextView) View.inflate(mContext,R.layout.layout_tzlb,null);
                tipTv.setText(mHasBetList.get(i).showTip);
                tipTv.setTag(i);
                tipTv.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        final int pos = (int) v.getTag();
                        new MaterialDialog.Builder(mContext).content("确定删除方案" + mHasBetList.get(pos).showTip +  "？").positiveText("确定").negativeText("取消")
                                .onAny(new MaterialDialog.SingleButtonCallback() {
                                    @Override
                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                        if (which == DialogAction.POSITIVE) {
                                            mHasBetList.remove(pos);
                                            notifyDataSetChanged();
                                            dialog.dismiss();
                                        } else if (which == DialogAction.NEGATIVE) {
                                            dialog.dismiss();
                                        }
                                    }
                                })
                                .positiveColorRes(R.color.color_text_red)
                                .negativeColorRes(R.color.color_text_red)
                                .build().show();

                        return true;
                    }
                });
                root.addView(tipTv);
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_buy_detail_type2_issue_iv:
                if (!issueIsSelect){
                    mCallBack.onCallBack(Const.FLAG_BUY_DETAL_ISSUE,-1,-1);
                }else{
                    issueIsSelect = false;
                    notifyDataSetChanged();
                }
                break;
            case R.id.item_buy_detail_type2_tzfs:
                mCallBack.onCallBack(Const.FLAG_BUY_HE,-1,-1);
                break;
            case R.id.item_buy_type2_add_list:
                mCallBack.onCallBack(Const.FLAG_BUY_DETAL_ADD_TO_LIST,-1,-1);
                break;
            case R.id.item_buy_type2_clear_list:
                mCallBack.onCallBack(Const.FLAG_BUY_DETAL_CLEAR_LIST,-1,-1);
                break;
            case R.id.count_reduce_iv:
                if (mCurrentMultiple > 1){
                    mCurrentMultiple = mCurrentMultiple - 1;
                    reSetButtomData();
                }
                break;
            case R.id.count_add_iv:
                mCurrentMultiple = mCurrentMultiple + 1;
                reSetButtomData();
                break;
            case R.id.buy_ok:
                mCallBack.onCallBack(Const.FLAG_BUY_OK,-1,-1);
                break;
        }
    }

    /**
     * pararentPos  行
     * numPos   行里面的 球
     */
    public interface OnCallBack {
        void onCallBack(int flag,int pararentPos ,int numPos);
        void onCallBackTag(int flag,int pararentPos ,String tag);
    }
}
