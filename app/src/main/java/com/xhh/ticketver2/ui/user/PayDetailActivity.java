package com.xhh.ticketver2.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.dinpay.plugin.activity.DinpayChannelActivity;
import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.itrus.util.sign.RSAWithSoftware;
import com.merchant.model.OrderInfo;
import com.merchant.util.DigestUtils;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.presenter.PayPresenter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.base.BaseWebViewActivity;
import com.xhh.ticketver2.ui.view.PayView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.InjectView;
import butterknife.OnClick;

public class PayDetailActivity extends BaseActivity implements PayView {


    String title;
    String pay_way;
    String class_uuid;
    String pic;
    PayPresenter payPresenter;

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @InjectView(R.id.recharge_money_et)
    EditText recharge_money_et;

    private void zhiFuReturn() {
        Bundle xmlData = getIntent().getExtras();
        if (xmlData != null) {
            String response = xmlData.getString("xml");
            try {
                String status = "<trade_status>";
                int start = response.indexOf(status);
                int end = response.indexOf("</trade_status>");
                String str = response.substring(start + status.length(), end);
                if ("SUCCESS".equals(str)) {
                    // merchant_payResult.setText("支付结果：支付成功");
                    new MaterialDialog.Builder(mContext).content("支付成功").positiveText("确定")
                            .onAny(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    if (which == DialogAction.POSITIVE) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }
                            })
                            .positiveColorRes(R.color.color_text_red)
                            .build().show();
                } else if ("UNPAY".equals(str)) {
                    showToast("未支付");
                } else {
                    showToast("支付失败");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick({R.id.recharge_ok_bt})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.recharge_ok_bt:
                String inputMoney = recharge_money_et.getText().toString();
                if (TextUtils.isEmpty(inputMoney)) {
                    showToast("请先输入充值金额");
                } else {
//                    if ("网银".equals(title)) {
//                        initPay(inputMoney);
//                    }
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("payType", pay_way);
                    map.put("money", inputMoney);
                    payPresenter.postAlipay(map);
                }
                break;
            default:
                break;
        }

    }

    private void initPay(String money) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("platform", "3");
//        map.put("channelId", class_uuid);
        map.put("channel", pay_way);
        map.put("money", money);
        payPresenter.post(map);
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_paydetail;
    }

    @Override
    protected void onPubEvent(EventCenter eventCenter) {

    }

    @Override
    protected void initViewsAndEvents() {
        initTopBar();
        payPresenter = new PayPresenter(this, this);
        title = getIntent().getStringExtra("title");
        pay_way = getIntent().getStringExtra("pay_way");
        class_uuid = getIntent().getStringExtra("class_uuid");
        pic = getIntent().getStringExtra("pic");
        mTopbarTitleTv.setText(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        zhiFuReturn();
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

    // --------------------智付开始----------------------------
    private void goZhiFupay(String key, String merchant_code, String notify_url, String interface_version,
                            String sign_type,
                            String order_no,
                            String order_time, String order_amount, String product_name, String sign) {

        OrderInfo info = new OrderInfo();
        info.setMerchant_code(merchant_code);
        info.setNotify_url(notify_url);
        info.setInterface_version(interface_version);
        info.setOrder_no(order_no);
        info.setOrder_time(order_time);
        info.setOrder_amount(order_amount);
        info.setProduct_name(product_name);
//        info.setMerchant_code("1111110166");
//        info.setNotify_url("http://zhangl.imwork.net:48842/ServerDemo/Notify_Url.jsp");
//        info.setInterface_version("V3.0");
//        info.setOrder_no("1489571113478");
//        info.setOrder_time("2017-03-15 17:45:13");
//        info.setOrder_amount("0.01");
//        info.setProduct_name("iPhone 6s");

        info.setRedo_flag("");
        info.setProduct_code("");
        info.setProduct_num("");
        info.setProduct_desc("");
        info.setExtra_return_param("");
        info.setExtend_param(DigestUtils.toMosaic(this, "", ""));
/*
		//组织签名规则格式
		Map<String, String> maps = info.getMap();
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> entry : maps.entrySet()) {
			String value = entry.getValue();
			if (!TextUtils.isEmpty(value)) {
				sb.append(entry.getKey() + "=" + value + "&");
			}
		}

		sign = sb.toString().substring(0, sb.toString().length() - 1);
//      sign = "interface_version=V3.0&merchant_code=2000771738&notify_url=http://tpay.pejskek.top/pay/notify&order_amount=58&order_no=RE2272968453730010357&order_time=2017-03-15 18:00:45&product_name=test123";
//		sign = "interface_version=V3.0&merchant_code=1111110166&notify_url=http://zhangl.imwork.net:48842/ServerDemo/Notify_Url.jsp&order_amount=0.01&order_no=1489571113478&order_time=2017-03-15 17:45:13&product_name=iPhone 6s";

		try {
			//RSA-S签名方式
			sign = getRSASSignature(sign);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
        sign = sign.replaceAll("\\+", "%2B");
        //组织报文
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
                "<dinpay><request><merchant_code>" + info.getMerchant_code() + "</merchant_code>" +
                "<notify_url>" + info.getNotify_url() + "</notify_url>" +
                "<interface_version>" + info.getInterface_version() + "</interface_version>" +
                "<sign_type>" + sign_type + "</sign_type>" +
                "<sign>" + sign + "</sign>" +
                "<trade><order_no>" + info.getOrder_no() + "</order_no>" +
                "<order_time>" + info.getOrder_time() + "</order_time>" +
                "<order_amount>" + info.getOrder_amount() + "</order_amount>" +
                "<product_name>" + info.getProduct_name() + "</product_name>" +
                "<redo_flag>" + info.getRedo_flag() + "</redo_flag>" +
                "<product_code>" + info.getProduct_code() + "</product_code>" +
                "<product_num>" + info.getProduct_num() + "</product_num>" +
                "<product_desc>" + info.getProduct_desc() + "</product_desc>" +
                "<extra_return_param>" + info.getExtra_return_param() + "</extra_return_param>" +
                "<extend_param>" + info.getExtend_param() + "</extend_param>" +
                "</trade></request></dinpay>";
        Log.i("xml=", xml);
        Intent intent = new Intent(this, DinpayChannelActivity.class);
        intent.putExtra("xml", xml);
        intent.putExtra("ActivityName", "com.appticket.ui.tab4.PayDetailActivity");
        startActivity(intent);
    }

    private String getRSASSignature(String plainText) throws Exception {

        /**
         1)merchant_private_key，商户私钥，商户按照《密钥对获取工具说明》操作并获取商户私钥；获取商户私钥的同时，也要获取商户公钥（merchant_public_key）；调试运行
         代码之前首先先将商户公钥上传到智付商家后台"支付管理"->"公钥管理"（如何获取和上传请查看《密钥对获取工具说明》），不上传商户公钥会导致调试运行代码时报错。
         2)demo提供的merchant_private_key是测试商户号1111110166的商户私钥，请自行获取商户私钥并且替换	*/

//		String merchant_private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALf/+xHa1fDTCsLYPJLHy80aWq3djuV1T34sEsjp7UpLmV9zmOVMYXsoFNKQIcEzei4QdaqnVknzmIl7n1oXmAgHaSUF3qHjCttscDZcTWyrbXKSNr8arHv8hGJrfNB/Ea/+oSTIY7H5cAtWg6VmoPCHvqjafW8/UP60PdqYewrtAgMBAAECgYEAofXhsyK0RKoPg9jA4NabLuuuu/IU8ScklMQIuO8oHsiStXFUOSnVeImcYofaHmzIdDmqyU9IZgnUz9eQOcYg3BotUdUPcGgoqAqDVtmftqjmldP6F6urFpXBazqBrrfJVIgLyNw4PGK6/EmdQxBEtqqgXppRv/ZVZzZPkwObEuECQQDenAam9eAuJYveHtAthkusutsVG5E3gJiXhRhoAqiSQC9mXLTgaWV7zJyA5zYPMvh6IviX/7H+Bqp14lT9wctFAkEA05ljSYShWTCFThtJxJ2d8zq6xCjBgETAdhiH85O/VrdKpwITV/6psByUKp42IdqMJwOaBgnnct8iDK/TAJLniQJABdo+RodyVGRCUB2pRXkhZjInbl+iKr5jxKAIKzveqLGtTViknL3IoD+Z4b2yayXg6H0g4gYj7NTKCH1h1KYSrQJBALbgbcg/YbeU0NF1kibk1ns9+ebJFpvGT9SBVRZ2TjsjBNkcWR2HEp8LxB6lSEGwActCOJ8Zdjh4kpQGbcWkMYkCQAXBTFiyyImO+sfCccVuDSsWS+9jrc5KadHGIvhfoRjIj2VuUKzJ+mXbmXuXnOYmsAefjnMCI6gGtaqkzl527tw=";
        String merchant_private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKkYTnvUa6U+Gh3T/pnH5bgm7tBl44wEE2UtrRggnBHpY5Jk18nYLiDOeGtRWYXxiHP67qc0Qat/JDUHYJsuETQRap2WYAmp6I1rhXj70Lb9L+d7gqmWKyHlJJDLHbQYfZgmayvcJF9Lkrs7Iap3JuQsQIkkF2ZsZ7szE7LrpoTHAgMBAAECgYAqGagowomOmbis5oRES3XRdLgO4N9XlAg0L7wh/rR87SviXgMv+Qf7GQz4Q0fTxoAfyEE1Fhinlz8viMevuzeC5JmNIWYQHNBAbrNl80Mi9T9pkPalbRSn5XNsOBMLqzO94ST/cqgMwIT1HabwlJD/VDYz22JdBQlQgFwQ6pcj+QJBANNAxHZaneUIU2zPWbdLI3O46KBjvK0lqN9+aN2HsD8lhWghuPIsbyf95pJVUYrmuzAzrg/1iCbRmfkAp0AV1UMCQQDM6YLZ5z4hl9U5aD8jPmQiTYQtL/03LviOvGSHCZnhauIb091y4UbIAiCJy6tIcl32rRqQPusRYiSm9sT261gtAkAqsAzDCb6cOFl7RE8Rvco1KSlAt8a5ikGcn9Oa6D9SE0fo6d+QeDAh+als6namxccPj3Hd4bSYe4RMj7g8N+03AkEAtd5fU/WI0JoJFq2utCIxD70LHmwkazEWTOFqu+vKlyadTqOJPegyQVyZ8pNBD8jqGwFivToKuuAHteT5xkn1dQJACQdJDzkREw7EdOtpOPyiDgg32R8/WoHPMTEuQ6MC2+nm3PFraPCtOH+YdNNz0bT1LFBwypnq6enyZ6Az8fti5Q==";
        String signData = RSAWithSoftware.signByPrivateKey(plainText, merchant_private_key);
        signData = signData.replaceAll("\\+", "%2B");
        return signData;
    }

    // --------------------智付结束----------------------------
    private void goWebView(String url) {
        try {
            Intent in = new Intent(this, BaseWebViewActivity.class);
            in.putExtra(BaseWebViewActivity.BUNDLE_KEY_URL, url);
            in.putExtra(BaseWebViewActivity.BUNDLE_KEY_TITLE, title);
            startActivity(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showPaySuccess(CommEntry commEntry) {
        if (commEntry.status == Const.STATUS_SUCCESS) {
            try {
                JSONObject jo = new JSONObject(commEntry.json);
                jo = jo.getJSONObject("obj");
                if (pay_way.contains("dinpay")) {
                    String key = "";
                    if (jo.has("key")) {
                        key = jo.getString("key");
                    }
                    String merchant_code = jo.getString("merchant_code");
                    String notify_url = jo.getString("notify_url");
                    String interface_version = jo.getString("interface_version");
                    String sign_type = jo.getString("sign_type");
                    String order_no = jo.getString("order_no");
                    String order_time = jo.getString("order_time");
                    String order_amount = jo.getString("order_amount");
                    String product_name = jo.getString("product_name");
                    String sign = jo.getString("sign");
                    goZhiFupay(key, merchant_code, notify_url, interface_version, sign_type, order_no,
                            order_time, order_amount, product_name, sign);

                } else {
                    String type = jo.getString("type");
                    String data = jo.getString("data");
                    if ("url".equals(type)) {
                        goWebView(data);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showAliPaySuccess(CommEntry commEntry) {
        if (commEntry.status == Const.STATUS_SUCCESS) {
            goWebView(commEntry.json);
            finish();
        } else {
            showToast(commEntry.msg);
        }
    }
}
