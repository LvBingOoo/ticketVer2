package com.xhh.ticketver2.ui.fragment.home;


import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.ui.base.BaseFragment;

import butterknife.InjectView;

public class HomeChartFragment extends BaseFragment {
    @Override
    protected void onFirstUserVisible() {

    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViewsAndEvents() {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.item_ball;
    }

    @Override
    protected void onPubEvent(EventCenter eventCenter) {

    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }
/*
    @InjectView(R.id.topbar_title)
    TextView mTopBarTitle;
    ConversationFragment conversation;
    @Override
    protected void onFirstUserVisible() {
        MLog.e("====onFirstUserVisible======conversation=======" + conversation);
        initData();
    }

    @Override
    protected void onUserVisible() {
        MLog.e("====onUserVisible======conversation=======" + conversation);
        initData();
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    protected void initViewsAndEvents() {
        mTopBarTitle.setText("彩聊");
        conversation = (ConversationFragment) getChildFragmentManager().findFragmentById(R.id.conversation);
        MLog.e("====initViewsAndEvents======conversation=======" + conversation);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_homechart;
    }

    @Override
    protected void onPubEvent(EventCenter eventCenter) {
//        if (EventCenter.EVENT_CODE_CHART == eventCenter.getEventCode()){
//            initData();
//        }
    }
    private void initData(){
        String groupId = FileUtil.getString(mContext,FileUtil.PRE_FILE_RONG_GOURPID);
        if (conversation != null && !TextUtils.isEmpty(groupId)){
            conversation.setUri(getConverstionUri(mContext, Conversation.ConversationType.GROUP,groupId,"彩聊"));
        }
    }
    public Uri getConverstionUri(Context context, Conversation.ConversationType conversationType, String targetId, String title) {
        Uri uri = null;
        if(context != null && !TextUtils.isEmpty(targetId) && conversationType != null) {
            uri = Uri.parse("rong://" + context.getApplicationInfo().processName).buildUpon().appendPath("conversation").appendPath(conversationType.getName().toLowerCase()).appendQueryParameter("targetId", targetId).appendQueryParameter("title", title).build();
        }
        MLog.e("==========uri=======" + uri);
        return uri;
    }
    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }*/
}
