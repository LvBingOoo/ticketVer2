package com.xhh.ticketver2.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.HeBuyDoEntry;
import com.xhh.ticketver2.utils.CommUtil;
import com.cc.util.code.StringUtils;


public class MenuDialog3 {

    private static Activity mmContext;
    static android.app.Dialog dialog;
    /**
     * 底部弹出dialog
     * 
     * @param mContext
     * @param dialogClickListener
     * @return
     */
	public static android.app.Dialog ShowDialog(Activity mContext, HeBuyDoEntry en,
                                                final DialogItemClickListener dialogClickListener) {
        mmContext = mContext;
		dialog = new android.app.Dialog(mContext, R.style.DialogStyle);
        // dialog.setCancelable(false);
		View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_main_menu3, null);
        dialog.setContentView(view);
		TextView menu_iussue = (TextView) view.findViewById(R.id.menu_iussue);
		TextView menu_times_num = (TextView) view.findViewById(R.id.menu_times_num);
		TextView menu_total_money = (TextView) view.findViewById(R.id.menu_total_money);
		TextView menu_status_text = (TextView) view.findViewById(R.id.menu_status_text);
		TextView menu_kjnums = (TextView) view.findViewById(R.id.menu_kjnums);
		TextView menu_total_bonus = (TextView) view.findViewById(R.id.menu_total_bonus);
		view.findViewById(R.id.menu_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        menu_iussue.setText("执行情况(第" + en.periods +"期)");
        menu_times_num.setText(en.betMultiple + "倍");
        menu_total_money.setText(StringUtils.floatTo2(CommUtil.stringToFloat(en.betMoney)) +"元");
        menu_status_text.setText(CommUtil.getStatusText(en.status));
        menu_kjnums.setText(en.openNumber);
        menu_total_bonus.setText(StringUtils.floatTo2(CommUtil.stringToFloat(en.winningAmount)) +"元");

        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        lp.width = getScreenWidth(mContext) * 9 / 10;
//		// lp.height = getScreenHeight(mContext) * 3 / 4;
//		lp.y = dip2px(mContext, 40);
        mWindow.setGravity(Gravity.CENTER);
        // 添加动画
        // mWindow.setWindowAnimations(R.style.dialogMenuAnim);
        mWindow.setAttributes(lp);
        dialog.show();
        return dialog;
    }

    /**
     * 回调接口
     * 
     * @author Administrator
     * 
     */
    public interface OnAlertViewClickListener {
        public abstract void confirm(String result);

        public abstract void cancel();
    }

    /**
     * 时间dialog回调接口
     * 
     * @author Administrator
     * 
     */
    public interface OnTimeAlertViewClickListener {
        public abstract void confirm();
    }

    public interface DialogItemClickListener {
        public abstract void confirm(String result);
    }

    /** 获取屏幕分辨率宽 */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /** 获取屏幕分辨率高 */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    /** 获取屏幕分辨率宽计算dialog的宽度 */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
