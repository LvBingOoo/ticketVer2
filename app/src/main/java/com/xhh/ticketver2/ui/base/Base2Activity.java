/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xhh.ticketver2.ui.base;

import android.app.Dialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhhc.obsessive.library.base.BaseAppCompatActivity;
import com.hhhc.obsessive.library.utils.StatusBarUtil;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.ui.view.base.BaseView;
import com.cc.uview.ShowLoadingDialog;

public abstract class Base2Activity extends BaseAppCompatActivity implements BaseView {

    private Dialog mShowLoadingDialog;

    protected ImageView mTopbarLeftIv;
    protected ImageView mTopbarRightIv;
    protected TextView mTopbarRightTv;
    protected TextView mTopbarTitleTv;
    public void initStatusBar(boolean isLight){
        int status = StatusBarUtil.StatusBarLightMode(this,isLight);
        if (status == 0){
            StatusBarUtil.setStatusBarColor(this, R.color.color_text_main_black_001);
        }else{
            if (isApplyKitKatTranslucency()) {
                StatusBarUtil.setStatusBarColor(this, R.color.transparent);
            }else{
                StatusBarUtil.setStatusBarColor(this, R.color.white);
            }
        }
    }
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideLoadingDialog();
    }
    public void initTopBar(){
        mTopbarLeftIv = (ImageView) findViewById(R.id.topbar_left_iv);
        mTopbarTitleTv = (TextView) findViewById(R.id.topbar_title);
        mTopbarRightTv = (TextView) findViewById(R.id.topbar_right_text);
        mTopbarRightIv = (ImageView) findViewById(R.id.topbar_right_iv);
        if (mTopbarLeftIv != null){
            mTopbarLeftIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (view.getId()){
                        case R.id.topbar_left_iv:
                            finish();
                            break;
                    }
                }
            });
        }
    }
    @Override
    public void showLoadingDialog(String msg) {
        if (mShowLoadingDialog == null){
            mShowLoadingDialog = ShowLoadingDialog.getInstance().loadingDialog(this,msg);
            mShowLoadingDialog.show();
        }
    }

    @Override
    public void hideLoadingDialog() {
        if(mShowLoadingDialog != null && mShowLoadingDialog.isShowing()){
            mShowLoadingDialog.dismiss();
            mShowLoadingDialog = null;
        }

    }

    protected abstract boolean isApplyKitKatTranslucency();

}
