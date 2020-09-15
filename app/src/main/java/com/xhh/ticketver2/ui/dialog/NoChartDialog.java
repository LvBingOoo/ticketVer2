package com.xhh.ticketver2.ui.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.xhh.ticketver2.R;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class NoChartDialog implements View.OnClickListener {

    private ViewHolder mViewHolder;
    private Dialog mDialog;
    private TZFSDialogCallBack mCallBack;

    public Dialog showDialog(final Context mContext, final TZFSDialogCallBack callBack) {
        mCallBack = callBack;
        mDialog = new Dialog(mContext, R.style.DialogStyle);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_nochart, null);
        mDialog.setContentView(view);
        mViewHolder = new ViewHolder(view);

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
                mCallBack.wayCallBack();
                closeDialog();
                break;
            case R.id.dialog_hemai_cancle:
                closeDialog();
                break;
        }
    }

    static class ViewHolder {
        @InjectView(R.id.dialog_hemai_ok)
        View dialog_hemai_ok;
        @InjectView(R.id.dialog_hemai_cancle)
        View dialog_hemai_cancle;
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
        void wayCallBack();
    }
}


