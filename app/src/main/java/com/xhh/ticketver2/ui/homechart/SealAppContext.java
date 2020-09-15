package com.xhh.ticketver2.ui.homechart;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.UserInfoEntry;
import com.xhh.ticketver2.presenter.UserPresenter;
import com.xhh.ticketver2.ui.view.UserView;
import com.xhh.ticketver2.utils.MLog;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

/**
 * 融云相关监听 事件集合类
 * Created by AMing on 16/1/7.
 * Company RongCloud
 */
public class SealAppContext implements RongIMClient.OnReceiveMessageListener, RongIM.UserInfoProvider ,UserView{


    private final static String TAG = "SealAppContext";
    public static final String UPDATE_FRIEND = "update_friend";
    public static final String UPDATE_RED_DOT = "update_red_dot";
    public static final String UPDATE_GROUP_NAME = "update_group_name";
    public static final String UPDATE_GROUP_MEMBER = "update_group_member";
    public static final String GROUP_DISMISS = "group_dismiss";
    private Context mContext;
    private static SealAppContext mRongCloudInstance;
    private UserPresenter userPresenter;
    public SealAppContext(Context mContext) {
        this.mContext = mContext;
        userPresenter = new UserPresenter(SealAppContext.this,mContext);
        initListener();
    }

    /**
     * 初始化 RongCloud.
     *
     * @param context 上下文。
     */
    public static void init(Context context) {

        if (mRongCloudInstance == null) {
            synchronized (SealAppContext.class) {

                if (mRongCloudInstance == null) {
                    mRongCloudInstance = new SealAppContext(context);
                }
            }
        }

    }

    /**
     * 获取RongCloud 实例。
     *
     * @return RongCloud。
     */
    public static SealAppContext getInstance() {
        return mRongCloudInstance;
    }

    /**
     * show 后就能设置的监听
     */
    private void initListener() {
        MLog.e("===================initListener=================");
        RongIM.setUserInfoProvider(this, true);
        setInputProvider();
    }

    private void setInputProvider() {
        RongIM.setOnReceiveMessageListener(this);
    }


    @Override
    public boolean onReceived(Message message, int i) {
        return false;
    }

    @Override
    public UserInfo getUserInfo(String s) {
        MLog.e("==============getUserInfo=========="+s);
        userPresenter.postGetInfoFromId(s);
        return null;
    }

    @Override
    public void showGetUserInfoSuccess(UserInfoEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS){
            if (TextUtils.isEmpty( entry.headPortrait)){
                UserInfo userInfo = new UserInfo(entry.userId,entry.nickName,Uri.parse(""));
                RongIM.getInstance().refreshUserInfoCache(userInfo);
            }else{
                UserInfo userInfo = new UserInfo(entry.userId,entry.nickName,Uri.parse(entry.headPortrait));
                RongIM.getInstance().refreshUserInfoCache(userInfo);
            }

        }
    }

    @Override
    public void showLoadingDialog(String msg) {

    }

    @Override
    public void hideLoadingDialog() {

    }

    @Override
    public void showToast(String msg) {

    }
}
