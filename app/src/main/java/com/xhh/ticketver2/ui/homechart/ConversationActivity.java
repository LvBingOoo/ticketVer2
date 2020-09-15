package com.xhh.ticketver2.ui.homechart;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhhc.obsessive.library.utils.AndroidBug5497Workaround;
import com.hhhc.obsessive.library.utils.CommonUtils;
import com.hhhc.obsessive.library.utils.StatusBarUtil;
import com.xhh.ticketver2.R;

import io.rong.imkit.fragment.ConversationFragment;

/**
 * Created by cx on 2017/4/20.
 */

public class ConversationActivity extends FragmentActivity{
    protected ImageView mTopbarLeftIv;
    protected ImageView mTopbarRightIv;
    protected TextView mTopbarRightTv;
    protected TextView mTopbarTitleTv;
    String targetId;
    private Context mContext;
    private final int CLEARMESSAGE = 0x001;
    ConversationFragment conversationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_conversation);
        StatusBarUtil.setStatusBarColor(this, R.color.transparent);
        AndroidBug5497Workaround.assistActivity(this);
        mContext = this;
        initTopBar();
        conversationFragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
        targetId = getIntent().getData().getQueryParameter("targetId");
        String title = getIntent().getData().getQueryParameter("title");
        mTopbarTitleTv.setText(title + "");
        initNavBar();
        conversationFragment.setExtensionHind(false);
    }
    private void initNavBar(){
        int height = CommonUtils.getBottomKeyboardHeight(this);
        if (height != 0){
            View root = ((ViewGroup)findViewById(android.R.id.content)).getChildAt(0);
            ViewGroup.LayoutParams params = root.getLayoutParams();
            root.setPadding(root.getPaddingLeft(),root.getPaddingTop(),root.getPaddingRight(),root.getPaddingBottom() + height);
            root.setLayoutParams(params);
        }

    }
    public void initTopBar() {
        mTopbarLeftIv = (ImageView) findViewById(R.id.topbar_left_iv);
        mTopbarTitleTv = (TextView) findViewById(R.id.topbar_title);
        mTopbarRightTv = (TextView) findViewById(R.id.topbar_right_text);
        mTopbarRightIv = (ImageView) findViewById(R.id.topbar_right_iv);
        if (mTopbarLeftIv != null) {
            mTopbarLeftIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()) {
                        case R.id.topbar_left_iv:
                            finish();
                            break;
                    }
                }
            });
        }
    }
}
