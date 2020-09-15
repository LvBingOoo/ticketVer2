package com.xhh.ticketver2.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.jdsjlzx.ItemDecoration.GridItemDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.GameInfoEntry;
import com.xhh.ticketver2.ui.adapter.homebuy.GameIssueAdapter;
import com.xhh.ticketver2.utils.CommUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class GameIssueDialog implements View.OnClickListener {

    private ViewHolder mViewHolder;
    private Dialog mDialog;
    private TZFSDialogCallBack mCallBack;
    GameIssueAdapter gameIssueAdapter ;
    private int multiple;
    public Dialog showDialog( Context mContext, List<GameInfoEntry> list ,int[] arrMoneyAndNum ,int multiple,  TZFSDialogCallBack callBack) {
        this.multiple = multiple;
        mCallBack = callBack;
        mDialog = new Dialog(mContext, R.style.DialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_gameissue, null);
        mDialog.setContentView(view);
        mViewHolder = new ViewHolder(view);

        mViewHolder.item_buy_he_isstop_iv.setSelected(true);
        mViewHolder.item_buy_he_isstop_iv.setOnClickListener(this);
        mViewHolder.dialog_hemai_ok.setOnClickListener(this);
        mViewHolder.dialog_hemai_cancle.setOnClickListener(this);
        mViewHolder.count_reduce_iv.setOnClickListener(this);
        mViewHolder.count_add_iv.setOnClickListener(this);
        mViewHolder.count_input_tv.setText("5");
        mViewHolder.count_input_tv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView text, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String num = mViewHolder.count_input_tv.getText().toString();
                    if (TextUtils.isEmpty(num) || "0".equals(num)){
                        mViewHolder.count_input_tv.setText("1");
                    }
                    mCallBack.wayCallBackGetData(CommUtil.stringToInt(mViewHolder.count_input_tv.getText().toString()));
                    return true;
                }
                return false;
            }
        });

        mViewHolder.mLRecyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 1);
        mViewHolder.mLRecyclerView.setLayoutManager(gridLayoutManager);
        GridItemDecoration divider = new GridItemDecoration.Builder(mContext)
                .setHorizontal(R.dimen.margin_size_16_8dp)
                .setVertical(R.dimen.margin_size_16_8dp)
                .setColorResource(R.color.transparent)
                .build();
        mViewHolder.mLRecyclerView.addItemDecoration(divider);
        gameIssueAdapter = new GameIssueAdapter(mContext, 0, null,0,arrMoneyAndNum);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(gameIssueAdapter);

        gameIssueAdapter.setDataList(list);
        gameIssueAdapter.setNum(multiple);
        mViewHolder.mLRecyclerView.setAdapter(lRecyclerViewAdapter);
        mViewHolder.mLRecyclerView.setPullRefreshEnabled(false);
        mViewHolder.mLRecyclerView.setLoadMoreEnabled(false);

        Window mWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.width = getScreenWidth(mContext);
        mWindow.setGravity(Gravity.BOTTOM);
        mWindow.setWindowAnimations(R.style.dialogAnim);
        mWindow.setAttributes(lp);
        mDialog.show();

        return mDialog;
    }
    public void upDate( List<GameInfoEntry> list){
        gameIssueAdapter.setDataList(list);
        gameIssueAdapter.setNum(multiple);
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
        String input = "";
        switch (v.getId()){
            case R.id.dialog_hemai_ok:
                mCallBack.wayCallBack(mViewHolder.item_buy_he_isstop_iv.isSelected());
                closeDialog();
                break;
            case R.id.dialog_hemai_cancle:
                closeDialog();
                break;
            case R.id.count_reduce_iv:
                input = mViewHolder.count_input_tv.getText().toString();
                int count = CommUtil.stringToInt(input);
                if (count > 1){
                    mViewHolder.count_input_tv.setText((count - 1) + "");
//                    gameIssueAdapter.setNum((count - 1));
                    mCallBack.wayCallBackGetData(count - 1);
                }
                break;
            case R.id.count_add_iv:
                input = mViewHolder.count_input_tv.getText().toString();
                int num = (CommUtil.stringToInt(input) + 1);
                mViewHolder.count_input_tv.setText(num+ "");
//                gameIssueAdapter.setNum(num);
                mCallBack.wayCallBackGetData(num);
                break;
            case R.id.item_buy_he_isstop_iv:
                if (mViewHolder.item_buy_he_isstop_iv.isSelected()){
                    mViewHolder.item_buy_he_isstop_iv.setSelected(false);
                }else{
                    mViewHolder.item_buy_he_isstop_iv.setSelected(true);
                }
                break;
        }
    }

    static class ViewHolder {
        @InjectView(R.id.dialog_hemai_ok)
        View dialog_hemai_ok;
        @InjectView(R.id.dialog_hemai_cancle)
        View dialog_hemai_cancle;
        @InjectView(R.id.dialog_gametype_recycleview)
        LRecyclerView mLRecyclerView;

        @InjectView(R.id.count_reduce_iv)
        ImageView count_reduce_iv;
        @InjectView(R.id.count_add_iv)
        ImageView count_add_iv;
        @InjectView(R.id.count_input_tv)
        EditText count_input_tv;
        @InjectView(R.id.item_buy_he_isstop_iv)
        ImageView item_buy_he_isstop_iv;
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
        void wayCallBack(boolean isStop);
        void wayCallBackGetData(int size);
    }
}


