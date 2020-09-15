package com.xhh.ticketver2.ui.dialog;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.xhh.ticketver2.R;

public class WxShareWindow {
    private Context context;
    private PopupWindow popupWindow;
    private String shareUrl;
    private TextView tvContent;

    public WxShareWindow(Context context) {
        this.context = context;
        init();
    }


    private void init() {
        popupWindow = new PopupWindow(context);
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_dialog_wx_share, null, false);
        popupWindow.setContentView(contentView);
        popupWindow.setFocusable(true);
        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(FrameLayout.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        popupWindow.setBackgroundDrawable(dw);

        tvContent = contentView.findViewById(R.id.tvContent);
        TextView tvShare = contentView.findViewById(R.id.tvShare);
        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    if (cmb != null) {
                        cmb.setPrimaryClip(ClipData.newPlainText("simple_text", shareUrl));
                    }
                    Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "分享失败", Toast.LENGTH_SHORT).show();
                }
                popupWindow.dismiss();

            }
        });

        ImageView ivClose = contentView.findViewById(R.id.ivClose);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    public void show(View parent) {
        if (popupWindow != null && !popupWindow.isShowing()) {
            popupWindow.showAtLocation(parent, Gravity.CENTER, 0, 0);
        }
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
        if (!TextUtils.isEmpty(shareUrl)) {
            tvContent.setText(shareUrl);
        }
    }
}
