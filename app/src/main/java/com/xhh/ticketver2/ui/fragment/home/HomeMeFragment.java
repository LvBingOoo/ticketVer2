package com.xhh.ticketver2.ui.fragment.home;


import android.os.Bundle;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.SysEntry;
import com.xhh.ticketver2.beans.UserInfoEntry;
import com.xhh.ticketver2.presenter.SysPresenter;
import com.xhh.ticketver2.presenter.UserPresenter;
import com.xhh.ticketver2.ui.WhWebViewActivity;
import com.xhh.ticketver2.ui.base.BaseFragment;
import com.xhh.ticketver2.ui.base.BaseWebViewActivity;
import com.xhh.ticketver2.ui.dialog.WxShareWindow;
import com.xhh.ticketver2.ui.user.BankBindActivity;
import com.xhh.ticketver2.ui.user.BillActivity;
import com.xhh.ticketver2.ui.user.ChongZhiJiLvActivity;
import com.xhh.ticketver2.ui.user.MyTeamActivity;
import com.xhh.ticketver2.ui.user.MyTouZhuActivity;
import com.xhh.ticketver2.ui.user.PayActivity;
import com.xhh.ticketver2.ui.user.PersonActivity;
import com.xhh.ticketver2.ui.user.PwdManagerActivity;
import com.xhh.ticketver2.ui.user.SysSetActivity;
import com.xhh.ticketver2.ui.user.TiXianActivity;
import com.xhh.ticketver2.ui.view.SysView;
import com.xhh.ticketver2.ui.view.UserView;
import com.xhh.ticketver2.utils.CommUtil;
import com.xhh.ticketver2.utils.FileUtil;
import com.xhh.ticketver2.utils.MLog;
import com.cc.util.KeyBoardUtils;
import com.cc.util.code.StringUtils;

import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class HomeMeFragment extends BaseFragment implements UserView, SysView {

    @InjectView(R.id.topbar_title)
    TextView mTopBarTitle;
    @InjectView(R.id.homeme_header_civ)
    CircleImageView homeme_header_civ;
    @InjectView(R.id.homeme_leve_tv)
    TextView homeme_leve_tv;
    @InjectView(R.id.homeme_nickname_tv)
    TextView homeme_nickname_tv;
    @InjectView(R.id.homeme_task_tv)
    TextView homeme_task_tv;
    @InjectView(R.id.homeme_money_tv)
    TextView homeme_money_tv;
    @InjectView(R.id.homeme_task_progress)
    ContentLoadingProgressBar homeme_task_progress;

    UserPresenter userPresenter;
    SysPresenter sysPresenter;
    private View contentView;

    @Override
    protected void onFirstUserVisible() {
        KeyBoardUtils.closeKeybord(mTopBarTitle, mContext);
        userPresenter.postGetInfo();
    }

    @Override
    protected void onUserVisible() {
        KeyBoardUtils.closeKeybord(mTopBarTitle, mContext);
        userPresenter.postGetInfo();
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViewsAndEvents() {
        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_homeme, null, false);
        mTopBarTitle.setText("我的");
        userPresenter = new UserPresenter(this, mContext);
        sysPresenter = new SysPresenter(this, mContext);
    }

    @Override
    protected int getContentViewLayoutID() {

        return R.layout.fragment_homeme;
    }

    @OnClick({R.id.me_touzhu_tv, R.id.homeme_tixian_tv, R.id.homeme_pwd_ll,
            R.id.homeme_sysset_ll, R.id.homeme_kefu_ll, R.id.homeme_person_ll, R.id.homeme_chongzhijilv_tv, R.id.homeme_bank_ll
            , R.id.homeme_pay_tv, R.id.homeme_header_civ, R.id.me_txjl_tv, R.id.me_bill_tv, R.id.homeme_myteam_ll, R.id.homeme_share_ll})
    public void onclick(View v) {
        final Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.me_touzhu_tv:
                readyGo(MyTouZhuActivity.class);
                break;
//            case R.id.me_gdjl_tv:
//                readyGo(ReportDetailActivity.class);
//                break;
            case R.id.homeme_tixian_tv:
                readyGo(TiXianActivity.class);
                break;
            case R.id.homeme_pwd_ll:
                readyGo(PwdManagerActivity.class);
                break;
            case R.id.homeme_sysset_ll:
                readyGo(SysSetActivity.class);
                break;
            case R.id.homeme_kefu_ll:
                String kfqq = FileUtil.getString(mContext, FileUtil.PRE_FILE_KFQQ);
                if (TextUtils.isEmpty(kfqq)) {
                    sysPresenter.postSys(Const.TAG_QQ_ID);
                } else {
                    CommUtil.startQQ(mContext, kfqq);
                }
                break;
            case R.id.homeme_person_ll:
                readyGo(PersonActivity.class);
                break;
            case R.id.homeme_chongzhijilv_tv:
                bundle.putString("flag", "1");
                readyGo(ChongZhiJiLvActivity.class, bundle);
                break;
            case R.id.me_txjl_tv:
                bundle.putString("flag", "2");
                readyGo(ChongZhiJiLvActivity.class, bundle);
                break;
            case R.id.homeme_bank_ll:
                bundle.putString("flag", "update");
                readyGo(BankBindActivity.class, bundle);
                break;
            case R.id.homeme_pay_tv:
                readyGo(PayActivity.class);
                break;
            case R.id.homeme_header_civ:
                readyGo(PersonActivity.class);
                break;
            case R.id.me_bill_tv:
                readyGo(BillActivity.class);
                break;
            case R.id.homeme_myteam_ll:
                readyGo(MyTeamActivity.class);
                break;
            case R.id.homeme_share_ll:
//                bundle.putString("url", ApiH.URL_SHARE + FileUtil.getString(getActivity(), "userId"));
//                readyGo(WhWebViewActivity.class, bundle);
                String shareUrl;
                String userType = FileUtil.getString(getActivity(), "userType");
                if ("1".equals(userType)) {
                    shareUrl = ApiH.URL_SHARE + FileUtil.getString(getActivity(), "userId");
                } else {
                    shareUrl = ApiH.URL_SHARE + FileUtil.getString(getActivity(), "parentId");
                }
                WxShareWindow wxShareWindow = new WxShareWindow(getActivity());
                wxShareWindow.setShareUrl(shareUrl);
                wxShareWindow.show(contentView);
                break;
        }
    }

    @Override
    protected void onPubEvent(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public void showGetUserInfoSuccess(UserInfoEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS) {
            if (TextUtils.isEmpty(entry.headPortrait)) {
                Glide.with(this).load(R.mipmap.test_logo2).dontAnimate().fitCenter().into(homeme_header_civ);
            } else {
                Glide.with(this).load(entry.headPortrait).dontAnimate().fitCenter().error(R.mipmap.test_logo2).into(homeme_header_civ);
            }
            homeme_nickname_tv.setText(entry.nickName);
            homeme_leve_tv.setText("Lv" + entry.userGrade);
            homeme_money_tv.setText(getString(R.string.rmb) + " " + entry.availableFund);

            float max = CommUtil.stringToFloat(entry.totalBetTask);
            float task = max - CommUtil.stringToFloat(entry.betTask);
            MLog.e("=============task= max=" + max + " task=" + task);
            if (task == 0) {
                homeme_task_tv.setText(0 + "/" + entry.totalBetTask);
            } else {
                homeme_task_tv.setText(StringUtils.floatTo2(task) + "/" + entry.totalBetTask);
            }
            homeme_task_progress.setMax((int) max);
            homeme_task_progress.setProgress((int) task);

            FileUtil.saveString(mContext, FileUtil.PRE_FILE_SAFETYPASSRORD, entry.safetyPassword);
        }
    }

    @Override
    public void showSysSuccess(SysEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS) {
            if (Const.TAG_QQ_ID.equals(entry.setId)) {
                FileUtil.saveString(mContext, FileUtil.PRE_FILE_KFQQ, entry.setContent);
                CommUtil.startQQ(mContext, entry.setContent);
            }
        }
    }
}
