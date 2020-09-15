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
import android.os.Bundle;

import com.xhh.ticketver2.R;
import com.xhh.ticketver2.ui.view.base.BaseView;
import com.cc.uview.ShowLoadingDialog;

public abstract class BaseSwipeBackActivity extends BaseActivity implements BaseView {

    Dialog mShowLoadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isApplyKitKatTranslucency()){
            setSystemBarTintDrawable(getResources().getDrawable(R.drawable.transparent));
        }else{
            setSystemBarTintDrawable(getResources().getDrawable(R.drawable.sr_primary));
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


    protected abstract boolean isApplyKitKatTranslucency();

    @Override
    public void showLoadingDialog(String msg) {
        if (mShowLoadingDialog == null){
            mShowLoadingDialog = ShowLoadingDialog.getInstance().loadingDialog(this,msg);
        }
    }

    @Override
    public void hideLoadingDialog() {
        if(mShowLoadingDialog != null && mShowLoadingDialog.isShowing()){
            mShowLoadingDialog.dismiss();
            mShowLoadingDialog = null;
        }
    }
}
