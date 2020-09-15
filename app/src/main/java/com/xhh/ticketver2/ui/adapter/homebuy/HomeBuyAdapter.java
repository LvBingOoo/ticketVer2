package com.xhh.ticketver2.ui.adapter.homebuy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.github.jdsjlzx.ItemDecoration.GridItemDecoration;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.BannaerEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.HomeBuyEntry;
import com.xhh.ticketver2.ui.adapter.base.BaseMultiAdapter;
import com.xhh.ticketver2.ui.adapter.base.SuperViewHolder;
import com.xhh.ticketver2.ui.homebuy.BuyDetailActivity;
import com.xhh.ticketver2.ui.view.WinEntry;
import com.xhh.ticketver2.utils.CommUtil;
import com.cc.DataUitl;
import com.cc.util.yc.DensityUtil;

import cn.bingoogolapple.bgabanner.BGABanner;


/**
 * Author:    hup
 * Date:      2017/3/28.
 * Description:
 */

public class HomeBuyAdapter extends BaseMultiAdapter<HomeBuyEntry> implements View.OnClickListener{

    private  int mScreenWidth;
    private DensityUtil densityUtil;
    onItemClickListenter mOnItemClick;
    BannaerEntry mBannaerEntry;
    WinEntry mWinEntry;
    public HomeBuyAdapter(Context context, int screenWidth , onItemClickListenter click) {
        super(context);
        addItemType(Const.TYPE_01, R.layout.item_homebuy_type01);
        addItemType(Const.TYPE_02, R.layout.item_homebuy);
        mScreenWidth = screenWidth;
        densityUtil = new DensityUtil(mContext);
        mOnItemClick = click;
    }
    public void setBannaer(BannaerEntry entry){
        mBannaerEntry = entry;
        notifyDataSetChanged();
    }
    public void setWinPurchase(WinEntry entry){
        mWinEntry = entry;
        notifyDataSetChanged();
    }
    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        HomeBuyEntry item = getDataList().get(position);
        switch (item.getItemType()){
            case Const.TYPE_01:
                LRecyclerView mLRecyclerViewType1 = holder.getView(R.id.item_recyclerview_type01);

                GridLayoutManager gridLayoutManager1 = new GridLayoutManager(mContext, 4);
                mLRecyclerViewType1.setLayoutManager(gridLayoutManager1);
                HomeBuyGridType1Adapter gridAdapterType1 = new HomeBuyGridType1Adapter(mContext, mScreenWidth, null,position);
                LRecyclerViewAdapter lRecyclerViewAdapter1 = new LRecyclerViewAdapter(gridAdapterType1);

                gridAdapterType1.setDataList(item.mList);
                mLRecyclerViewType1.setAdapter(lRecyclerViewAdapter1);
                mLRecyclerViewType1.setFocusableInTouchMode(false);
                mLRecyclerViewType1.requestFocus();
                mLRecyclerViewType1.setPullRefreshEnabled(false);
                mLRecyclerViewType1.setLoadMoreEnabled(false);
                BGABanner mContentBanner = holder.getView(R.id.banner_guide_content);
                if (mBannaerEntry != null){
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentBanner.getLayoutParams();
                    params.width = mScreenWidth;
                    params.height = CommUtil.getImgShowHeight(mScreenWidth,750f,194f);
                    mContentBanner.setLayoutParams(params);
                    initBannaer(mContentBanner);
                }
                ViewFlipper viewFlipper = holder.getView(R.id.item_viewf);
                viewFlipper.removeAllViews();
                if (mWinEntry != null){
                    for (int i=0;i<mWinEntry.mList.size();i++){
                        WinEntry winEntry = mWinEntry.mList.get(i);
                        View root = View.inflate(mContext,R.layout.item_viewflipper,null);
                        TextView flip_username = root.findViewById(R.id.flip_username);
                        TextView flip_time = root.findViewById(R.id.flip_time);
                        TextView flip_lotteryname = root.findViewById(R.id.flip_lotteryname);
                        TextView flip_winmoney = root.findViewById(R.id.flip_winmoney);
                        flip_lotteryname.setText(winEntry.lotteryTypeName);
                        flip_winmoney.setText("喜中￥" + winEntry.winningTotailAmount);
                        flip_lotteryname.setText(winEntry.lotteryTypeName);
                        flip_time.setText(DataUitl.surplusLongToStrngShort(winEntry.openTime));
                        root.setTag(winEntry.lotteryTypeId);
                        if (!TextUtils.isEmpty(winEntry.userName)){
                            flip_username.setText(CommUtil.getCutUserName(winEntry.userName));
                        }
                        root.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mContext, BuyDetailActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("lotteryTypeId", (String) v.getTag());
                                intent.putExtras(bundle);
                                mContext.startActivity(intent);
                            }
                        });
                        viewFlipper.addView(root);
                    }
                }
                break;
            case Const.TYPE_02:
                TextView item_goupname = holder.getView(R.id.item_goupname);
                item_goupname.setText(item.nameGroup);
                LRecyclerView mLRecyclerView = holder.getView(R.id.item_recyclerview);
                mLRecyclerView.setHasFixedSize(true);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
                mLRecyclerView.setLayoutManager(gridLayoutManager);
                GridItemDecoration divider = new GridItemDecoration.Builder(mContext)
                        .setHorizontal(R.dimen.divider_size)
                        .setVertical(R.dimen.divider_size)
                        .setColorResource(R.color.div_gray)
                        .build();
                mLRecyclerView.addItemDecoration(divider);
                 HomeBuyGridAdapter gridAdapter = new HomeBuyGridAdapter(mContext, mScreenWidth, null,position);
                LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(gridAdapter);

                gridAdapter.setDataList(item.mMeiList);
                mLRecyclerView.setAdapter(lRecyclerViewAdapter);
                mLRecyclerView.setPullRefreshEnabled(false);
                mLRecyclerView.setLoadMoreEnabled(false);
                mLRecyclerView.setFocusableInTouchMode(false);
                mLRecyclerView.requestFocus();
                break;
        }
    }
    private void initBannaer(BGABanner mContentBanner ){
        mContentBanner.setAdapter(new BGABanner.Adapter<FrameLayout, BannaerEntry>() {
            @Override
            public void fillBannerItem(BGABanner banner, FrameLayout fl, BannaerEntry model, int position) {
                ImageView itemView = fl.findViewById(R.id.bannaer_iv);
                Glide.with(mContext)
                        .load(model.imgUrl)
                        .fitCenter()
                        .dontAnimate()
                        .into(itemView);
            }
        });
        mContentBanner.setData(R.layout.my_baner,mBannaerEntry.mList, null);

        mContentBanner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
            }
        });
    }
    @Override
    public void onClick(View v) {
    }

    public interface onItemClickListenter {
        public void onItemClick(int pos);
    }
}
