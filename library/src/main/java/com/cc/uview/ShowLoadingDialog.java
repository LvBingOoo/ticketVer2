package com.cc.uview;

import android.app.Activity;
import android.app.Dialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhhc.obsessive.library.R;
import com.cc.util.yc.DensityUtil;

public class ShowLoadingDialog {
    public static String LOGDING_MSG = "加载中...";
    private static ShowLoadingDialog dialog;

    public static ShowLoadingDialog getInstance() {
        if (dialog == null) {
            dialog = new ShowLoadingDialog();
        }
        return dialog;
    }

    /**
     * 得到自定义的progressDialog
     * 
     * @param context
     * @param message
     */
    public Dialog loadingDialog(Activity context, String message) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.progress_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.dialog_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        if (TextUtils.isEmpty(message)) {
            tipTextView.setVisibility(View.GONE);
        } else {
            tipTextView.setVisibility(View.VISIBLE);
            tipTextView.setText(message);// 设置加载信息
        }
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog
        int screenWidth = DensityUtil.getPhoneWidth(context);
        int screenHeigh = DensityUtil.getPhoneHeigh(context);
        int wdith = Math.min(screenWidth, screenHeigh) / 4;
        int height = Math.min(screenWidth, screenHeigh) / 3;
		loadingDialog.setCancelable(true);// 不可以用“返回键”取消

        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(wdith, height));// 设置布局
        loadingDialog.setCanceledOnTouchOutside(false);
        return loadingDialog;

    }

	public void dismiss(Dialog mDialog) {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
			mDialog = null;
		}
	}
}
