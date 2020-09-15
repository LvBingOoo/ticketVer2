package com.xhh.ticketver2.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hhhc.obsessive.library.base.BaseLazyFragment;
import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.hhhc.obsessive.library.smartlayout.SmartTabLayout;
import com.hhhc.obsessive.library.utils.AndroidBug5497Workaround;
import com.hhhc.obsessive.library.widgets.XViewPager;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.NavigationEntity;
import com.xhh.ticketver2.beans.RongEntry;
import com.xhh.ticketver2.presenter.RongPresenter;
import com.xhh.ticketver2.ui.adapter.base.BaseVPFragmentAdapter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.fragment.home.HomeAwardFragment;
import com.xhh.ticketver2.ui.fragment.home.HomeBuyFragment;
import com.xhh.ticketver2.ui.fragment.home.HomeBuyHeFragment;
import com.xhh.ticketver2.ui.fragment.home.HomeChartFragment;
import com.xhh.ticketver2.ui.fragment.home.HomeMeFragment;
import com.xhh.ticketver2.ui.view.RongView;
import com.xhh.ticketver2.utils.FileUtil;
import com.xhh.ticketver2.utils.MLog;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;


public class MainActivity extends BaseActivity implements RongView {
    private static long DOUBLE_CLICK_TIME = 0L;
    @InjectView(R.id.home_container)
    XViewPager mViewPager;
    @InjectView(R.id.fragment_images_tab_smart)
    SmartTabLayout mSmartTabLayout;
    RongPresenter rongPresenter;

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected void onPubEvent(EventCenter eventCenter) {

    }

    @Override
    protected void initViewsAndEvents() {
//        AndroidBug5497Workaround.assistActivity(this);
        initializeViews();
        rongPresenter = new RongPresenter(this, mContext);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String rongToken = FileUtil.getString(mContext, FileUtil.PRE_FILE_RONG_TOKEN);
        if (TextUtils.isEmpty(rongToken)) {
            // TODO: 2020/8/21  
//            rongPresenter.postGetRongToken();
        } else if (RongIMClient.getInstance().getCurrentConnectionStatus()
                != RongIMClient.ConnectionStatusListener.ConnectionStatus.CONNECTED) {
            connect(rongToken);
        } else {
            isRongConectSucc = true;
        }
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    public void initializeViews() {
        final List<NavigationEntity> navigationList = new ArrayList<>();
        navigationList.add(new NavigationEntity("购彩", R.drawable.selector_home_bottom1));
        navigationList.add(new NavigationEntity("合买", R.drawable.selector_home_bottom2));
//        navigationList.add(new NavigationEntity("彩聊", R.drawable.selector_home_bottom3));
        navigationList.add(new NavigationEntity("开奖", R.drawable.selector_home_bottom4));
        navigationList.add(new NavigationEntity("我的", R.drawable.selector_home_bottom5));

        List<BaseLazyFragment> fragments = new ArrayList<>();
        fragments.add(new HomeBuyFragment());
        fragments.add(new HomeBuyHeFragment());
//        fragments.add(new HomeChartFragment());
        fragments.add(new HomeAwardFragment());
        fragments.add(new HomeMeFragment());

        mViewPager.setOffscreenPageLimit(fragments.size());
        mViewPager.setAdapter(new BaseVPFragmentAdapter(getSupportFragmentManager(), fragments));
        final LayoutInflater inflater = LayoutInflater.from(this);
        mSmartTabLayout.setCustomTabView(new SmartTabLayout.TabProvider() {
            @Override
            public View createTabView(ViewGroup container, int position, PagerAdapter adapter) {
                View tab = inflater.inflate(R.layout.layout_smart_homenavigation, container, false);
                TextView mark = (TextView) tab.findViewById(R.id.custom_tab_notification_mark);
                ImageView icon = (ImageView) tab.findViewById(R.id.custom_tab_icon);
                mark.setText(navigationList.get(position).mName);
                icon.setImageResource(navigationList.get(position).mIconResId);
                return tab;
            }
        });
        //设置布局之后
        mSmartTabLayout.setViewPager(mViewPager);
//        mSmartTabLayout.setNOGoPage("2", new SmartTabLayout.NoGoPageCallBack() {
//            @Override
//            public void noGoPageCallBack(int pos) {
//                if (pos == 2) {
//                    if (isRongConectSucc) {
//                        String groupId = FileUtil.getString(mContext, FileUtil.PRE_FILE_RONG_GOURPID);
//                        RongIM.getInstance().startConversation(mContext, Conversation.ConversationType.GROUP, groupId, "彩聊");
//                    } else {
//                        showToast("消息初始化失败，请稍后再试");
//                    }
//                }
//            }
//
//            @Override
//            public void clickCallBack(int pos) {
//
//            }
//        });
        mViewPager.setEnableScroll(false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_MENU) {
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - DOUBLE_CLICK_TIME) > 2000) {
                showToast("再次点击退出");
                DOUBLE_CLICK_TIME = System.currentTimeMillis();
            } else {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private boolean isRongConectSucc = false;

    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(TicketApplication.getCurProcessName(getApplicationContext()))) {
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    MLog.e("--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 schemeId
                 */
                @Override
                public void onSuccess(String userid) {
                    MLog.e("--onSuccess" + userid);
                    isRongConectSucc = true;
                    rongPresenter.postGetRongAddGroup();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    MLog.e("--onError---" + errorCode.getMessage());
                }
            });
        }
    }

    @Override
    public void showGetRongTokenSuccess(RongEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS) {
            if (!TextUtils.isEmpty(entry.token) && !TextUtils.isEmpty(entry.groupId)) {
                FileUtil.saveString(mContext, FileUtil.PRE_FILE_RONG_TOKEN, entry.token);
                FileUtil.saveString(mContext, FileUtil.PRE_FILE_RONG_GOURPID, entry.groupId);
                connect(entry.token);
            }
        }
    }

    @Override
    public void showAddRongSuccess(CommEntry entry) {
        if (entry.status == Const.STATUS_SUCCESS) {
            FileUtil.saveBoolean(mContext, FileUtil.PRE_FILE_RONG_GOURPID_SUCCTESS, true);
        }
    }
}
