package com.xhh.ticketver2.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.PayEntry;
import com.xhh.ticketver2.beans.SysEntry;
import com.xhh.ticketver2.presenter.SysPresenter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.view.SysView;
import com.cc.uview.MyDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

public class PayActivity extends BaseActivity implements SysView {

	@InjectView(R.id.pay_list_ll)
	LinearLayout pay_list_ll;

	SysPresenter sysPresenter;
    String alipay_qrcode;
    String weixin_qrcode;
    boolean isFirstQr = true;
	protected boolean isApplyKitKatTranslucency() {
		return false;
	}
	@Override
	protected void getBundleExtras(Bundle extras) {

	}

	@Override
	protected int getContentViewLayoutID() {
		return R.layout.activity_pay;
	}

	@Override
	protected void onPubEvent(EventCenter eventCenter) {

	}

	@Override
	protected void initViewsAndEvents() {
	    initTopBar();
	    mTopbarTitleTv.setText("充值");
		sysPresenter = new SysPresenter(this,mContext);
		List<PayEntry> list = new ArrayList<>();
		PayEntry payEntry = new PayEntry();
		payEntry.title = "网银";
		payEntry.pay_way = "0";
		list.add(payEntry);
		PayEntry payEntry2 = new PayEntry();
		payEntry2.title = "扫码支付";
		list.add(payEntry2);

        PayEntry payEntry3 = new PayEntry();
        payEntry3.title = "支付宝";
        payEntry3.pay_way = "1";
        list.add(payEntry3);

		initUiList(list);
        getQr();
	}
    private void getQr(){
        sysPresenter.postSys(Const.TAG_ALIPAY_ID);
        sysPresenter.postSys(Const.TAG_WEIXIN_ID);
    }
    private void initUiList(List<PayEntry> list){
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i<list.size();i++){
            LinearLayout itemLayout = (LinearLayout) inflater.inflate(R.layout.item_pay_type,null);
            TextView title = itemLayout.findViewById(R.id.item_pay_title);
            ImageView item_pay_img = itemLayout.findViewById(R.id.item_pay_img);

            title.setText(list.get(i).title);
            itemLayout.setTag(list.get(i));

            if ("网银".equals(list.get(i).title)){
                item_pay_img.setImageResource(R.mipmap.pay_icon_01);
            }else if ("支付宝".equals(list.get(i).title)){
                item_pay_img.setImageResource(R.mipmap.pay_icon_05);
            }else{
                item_pay_img.setImageResource(R.mipmap.pay_icon_02);
            }
            itemLayout.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    PayEntry en = (PayEntry) view.getTag();
                    if ("网银".equals(en.title)){
                        Intent intent = new Intent(PayActivity.this, PayDetailActivity.class);
                        intent.putExtra("title", en.title);
                        intent.putExtra("pay_way", en.pay_way);
                        intent.putExtra("pic", en.pic);
                        intent.putExtra("class_uuid", en.class_uuid);
                        startActivity(intent);
                    }else if ("支付宝".equals(en.title)){
                        Intent intent = new Intent(PayActivity.this, PayDetailActivity.class);
                        intent.putExtra("title", en.title);
                        intent.putExtra("pay_way", en.pay_way);
                        intent.putExtra("pic", en.pic);
                        intent.putExtra("class_uuid", en.class_uuid);
                        startActivity(intent);
                    }else{
                        goQrDetail();
                    }

                }
            });
            pay_list_ll.addView(itemLayout);
        }

    }
    private void goQrDetail(){
        MyDialog.ShowDialog(mContext, "", new String[]{"支付宝", "微信"}, new MyDialog.DialogItemClickListener() {
            @Override
            public void confirm(String result) {
                if ("支付宝".equals(result)){
                    if (TextUtils.isEmpty(alipay_qrcode)){
                        isFirstQr = false;
                        sysPresenter.postSys(Const.TAG_ALIPAY_ID);
                    }else{
                        Intent intent = new Intent(PayActivity.this, PayCodeActivity.class);
                        intent.putExtra("alipay_qrcode", alipay_qrcode);
                        startActivity(intent);
                    }

                }else if ("微信".equals(result)){
                    if (TextUtils.isEmpty(weixin_qrcode)){
                        isFirstQr = false;
                        sysPresenter.postSys(Const.TAG_WEIXIN_ID);
                    }else{
                        Intent intent = new Intent(PayActivity.this, PayCodeActivity.class);
                        intent.putExtra("weixin_qrcode", weixin_qrcode);
                        startActivity(intent);
                    }

                }
            }
        });

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

	@Override
	public void showSysSuccess(SysEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS){
            if (Const.TAG_WEIXIN_ID.equals(entry.setId)){
                weixin_qrcode = entry.setContent;
                if (!isFirstQr){
                    Intent intent = new Intent(PayActivity.this, PayCodeActivity.class);
                    intent.putExtra("weixin_qrcode", weixin_qrcode);
                    startActivity(intent);
                }
            }else if (Const.TAG_ALIPAY_ID.equals(entry.setId)){
                alipay_qrcode = entry.setContent;
                if (!isFirstQr){
                    Intent intent = new Intent(PayActivity.this, PayCodeActivity.class);
                    intent.putExtra("alipay_qrcode", alipay_qrcode);
                    startActivity(intent);
                }
            }
        }
	}
}
