package com.xhh.ticketver2.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.GameInfoEntry;
import com.xhh.ticketver2.beans.GameListEntry;
import com.xhh.ticketver2.utils.CommUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class HeMaiDialog implements View.OnClickListener {

    private ViewHolder mViewHolder;
    private Dialog mDialog;
    private TZFSDialogCallBack mCallBack;
    /* 最低购买份数 */
    private float MIN_PERSERCENT = 0.05f;
    int mTotalMoney;
    public Dialog showDialog(final Context mContext, final GameListEntry gameListEntry, final int mTotalMoney,int totalIssue, GameInfoEntry mCrentIssueEntry, TZFSDialogCallBack callBack) {

        mCallBack = callBack;
        this.mTotalMoney = mTotalMoney;
        mDialog = new Dialog(mContext, R.style.DialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_hemai, null);
        mDialog.setContentView(view);
        mViewHolder = new ViewHolder(view);

        if (totalIssue == 0){
            mViewHolder.hemai_title_tv.setText(gameListEntry.lotteryTypeName + "第" + mCrentIssueEntry.issue + "期");
        }else{
            mViewHolder.hemai_title_tv.setText(gameListEntry.lotteryTypeName + "第" + mCrentIssueEntry.issue + "期[追" + totalIssue + "期]");
        }
        mViewHolder.hemai_money.setText(mTotalMoney + "元");
        mViewHolder.hemai_total_num.setText(mTotalMoney + "份");
        int minNum = getMinBuyCount(mTotalMoney);
        mViewHolder.hemai_buynum_et.setText(minNum + "");
        mViewHolder.hemai_baodinum_et.setText(mTotalMoney - minNum + "");
        mViewHolder.hemai_buynum_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tempNum = mViewHolder.hemai_buynum_et.getText().toString();
                float tempNumFloat =  CommUtil.stringToInt(tempNum);
                if (tempNumFloat > mTotalMoney){
                    mViewHolder.hemai_buynum_et.setText( mTotalMoney + "");
                }
                initBaodiInput();
            }
        });

        mViewHolder.hemai_baodinum_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!mViewHolder.item_buy_he_qebd_iv.isSelected()){
                    initBaodiInput();
                }
            }
        });

        mViewHolder.item_buy_he_qebd_iv.setSelected(true);
        mViewHolder.hemai_baodinum_et.setEnabled(false);
        mViewHolder.item_buy_he_qebd_iv.setOnClickListener(this);
        mViewHolder.dialog_hemai_ok.setOnClickListener(this);
        mViewHolder.dialog_hemai_cancle.setOnClickListener(this);

        Window mWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.width = getScreenWidth(mContext);
        mWindow.setGravity(Gravity.BOTTOM);
        mWindow.setWindowAnimations(R.style.dialogAnim);
        mWindow.setAttributes(lp);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
//                dialogClickListener.confirm(null);
            }
        });
        mDialog.show();

        return mDialog;
    }
    private void initBaodiInput(){
        int tempbaoDiNumInt = CommUtil.stringToInt(mViewHolder.hemai_baodinum_et.getText().toString());
        int leftNum = (mTotalMoney - CommUtil.stringToInt(mViewHolder.hemai_buynum_et.getText().toString()));
        if (tempbaoDiNumInt >leftNum || mViewHolder.item_buy_he_qebd_iv.isSelected()){
            mViewHolder.hemai_baodinum_et.setText(leftNum + "");
        }
    }
    private int getMinBuyCount(float totalMoney) {
        float all = totalMoney;
        float min = (all * MIN_PERSERCENT);

        int m = (int) min;
        if (min - m > 0) {
            m = m + 1;
        }
        return m;
    }
    /**
     * 获取屏幕分辨率宽
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕分辨率高
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * 获取屏幕分辨率宽计算dialog的宽度
     */
    private static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_hemai_ok:
                String baoDiNum = mViewHolder.hemai_baodinum_et.getText().toString();
                String tempNum = mViewHolder.hemai_buynum_et.getText().toString();
                float tempNumFloat =  CommUtil.stringToInt(tempNum);
                int minNum = getMinBuyCount(mTotalMoney);
                if (tempNumFloat < minNum){
                    Toast.makeText(v.getContext(),"最低认购5%，"+ minNum + "份",Toast.LENGTH_SHORT).show();
                } else{
                    mCallBack.wayCallBack(tempNum,baoDiNum,mViewHolder.item_buy_he_qebd_iv.isSelected());
                    closeDialog();
                }
                break;
            case R.id.dialog_hemai_cancle:
                closeDialog();
                break;
            case R.id.item_buy_he_qebd_iv:
                if (mViewHolder.item_buy_he_qebd_iv.isSelected()){
                    mViewHolder.item_buy_he_qebd_iv.setSelected(false);
                    mViewHolder.hemai_baodinum_et.setEnabled(true);
                }else{
                    mViewHolder.item_buy_he_qebd_iv.setSelected(true);
                    mViewHolder.hemai_baodinum_et.setEnabled(false);
                    String tempNum2 = mViewHolder.hemai_buynum_et.getText().toString();
                    int tempNumFloat2 =  CommUtil.stringToInt(tempNum2);
                    mViewHolder.hemai_baodinum_et.setText((mTotalMoney - tempNumFloat2) + "");
                }

                break;
        }
    }

    static class ViewHolder {
        @InjectView(R.id.dialog_hemai_ok)
        View dialog_hemai_ok;
        @InjectView(R.id.dialog_hemai_cancle)
        View dialog_hemai_cancle;
        @InjectView(R.id.hemai_title_tv)
        TextView hemai_title_tv;
        @InjectView(R.id.hemai_money)
        TextView hemai_money;
        @InjectView(R.id.hemai_total_num)
        TextView hemai_total_num;
        @InjectView(R.id.hemai_buynum_et)
        EditText hemai_buynum_et;
        @InjectView(R.id.hemai_baodinum_et)
        EditText hemai_baodinum_et;
        @InjectView(R.id.item_buy_he_qebd_iv)
        ImageView item_buy_he_qebd_iv;
        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public void closeDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public interface TZFSDialogCallBack {
        void wayCallBack(String buyNum,String baodiNum,boolean isAllBuy);
    }
}


