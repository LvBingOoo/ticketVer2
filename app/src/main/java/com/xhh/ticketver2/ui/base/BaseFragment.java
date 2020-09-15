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
import android.os.Build;

import com.hhhc.obsessive.library.base.BaseLazyFragment;
import com.xhh.ticketver2.ui.view.base.BaseView;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.cc.uview.ShowLoadingDialog;

public abstract class BaseFragment extends BaseLazyFragment implements BaseView {

    SystemBarTintManager mTintManager;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        hideLoadingDialog();
    }

    Dialog mShowLoadingDialog;
    @Override
    public void showLoadingDialog(String msg) {
        try {
            if (mShowLoadingDialog == null){
                mShowLoadingDialog = ShowLoadingDialog.getInstance().loadingDialog(getActivity(),msg);
                mShowLoadingDialog.show();
            }
        }catch (IllegalArgumentException i){
            i.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void hideLoadingDialog() {
        try {
            if(mShowLoadingDialog != null && mShowLoadingDialog.isShowing()){
                mShowLoadingDialog.dismiss();
                mShowLoadingDialog = null;
            }
        }catch (IllegalArgumentException i){
            i.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setSystemBarTintColor(int tintDrawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if(mTintManager == null)
                mTintManager = new SystemBarTintManager(getActivity());
            if (tintDrawable != 0) {
                mTintManager.setStatusBarTintEnabled(true);
                mTintManager.setTintColor(tintDrawable);
            } else {
                mTintManager.setStatusBarTintEnabled(false);
                mTintManager.setTintDrawable(null);
            }
        }
    }

}
