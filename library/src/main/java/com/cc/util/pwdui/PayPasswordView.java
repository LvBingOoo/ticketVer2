package com.cc.util.pwdui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hhhc.obsessive.library.R;
import com.hhhc.obsessive.library.utils.CommonUtils;

import java.util.ArrayList;


@SuppressLint("InflateParams")
public class PayPasswordView implements View.OnClickListener {

    public ImageView del;
    public ImageView zero;
    public ImageView one;
    public ImageView two;
    public ImageView three;
    public ImageView four;
    public ImageView five;
    public ImageView sex;
    public ImageView seven;
    public ImageView eight;
	public ImageView nine;
	public TextView cancel;
	public TextView sure;
	public TextView box1;
	public TextView box2;
	public TextView box3;
	public TextView box4;
	public TextView box5;
	public TextView box6;
	public TextView title;
	public TextView content;
	public TextView pay_detail;
	public TextView pay_bankName;
	public ImageView paypassword_bank_pic;
	public ImageView pay_close;
	public RelativeLayout paypassword_bank_rl;
	public LinearLayout pwd_bank_ll;

	public ArrayList<String> mList=new ArrayList<String>();
	public View mView;
	public OnPayListener listener;
	public Activity mContext;

	public PayPasswordView(int flag, String monney, Activity mContext,
			OnPayListener listener) {
		getDecorView(flag, monney, mContext, listener);
	}

	public static PayPasswordView getInstance(int flag, String money,
			Activity mContext,
			OnPayListener listener) {
		return new PayPasswordView(flag, money, mContext, listener);
	}
	public static int FLAG_FORM_TIXIAN = 1;
	public static int FLAG_FORM_PAY = 2;
    @SuppressLint("NewApi")
	public void getDecorView(int flag , String monney, Activity mContext,
			OnPayListener listener) {
		this.listener=listener;
		this.mContext = mContext;
        mView = LayoutInflater.from(mContext).inflate(R.layout.item_paypassword, null);
		findView(mView);
		setListenter();
		if (flag == FLAG_FORM_TIXIAN){
			pwd_bank_ll.setVisibility(View.INVISIBLE);
		}else if (flag == FLAG_FORM_PAY){
			pwd_bank_ll.setVisibility(View.GONE);
		}
		initShow(monney);
        LinearLayout  keyboardLl=  mView.findViewById(R.id.keyboard);
        RelativeLayout.LayoutParams pa  = (RelativeLayout.LayoutParams) keyboardLl.getLayoutParams();
        pa.bottomMargin = CommonUtils.getBottomKeyboardHeight(mContext);
        pa.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        keyboardLl.setLayoutParams(pa);
    }
    private void findView(View root){
		del = root.findViewById(R.id.pay_keyboard_del);
		zero = root.findViewById(R.id.pay_keyboard_zero);
		one =root.findViewById(R.id.pay_keyboard_one);
		two = root.findViewById(R.id.pay_keyboard_two);
		three = root.findViewById(R.id.pay_keyboard_three);
		four = root.findViewById(R.id.pay_keyboard_four);
		five = root.findViewById(R.id.pay_keyboard_five);
		 sex = root.findViewById(R.id.pay_keyboard_sex);
		 seven = root.findViewById(R.id.pay_keyboard_seven);
		 eight = root.findViewById(R.id.pay_keyboard_eight);
		nine = root.findViewById(R.id.pay_keyboard_nine);
		cancel = root.findViewById(R.id.pay_cancel);
		sure = root.findViewById(R.id.pay_sure);
		box1 = root.findViewById(R.id.pay_box1);
		box2 = root.findViewById(R.id.pay_box2);
		box3 = root.findViewById(R.id.pay_box3);
		box4 = root.findViewById(R.id.pay_box4);
		box5 = root.findViewById(R.id.pay_box5);
		box6 = root.findViewById(R.id.pay_box6);
		title = root.findViewById(R.id.pay_title);
		content = root.findViewById(R.id.pay_content);
		pay_detail = root.findViewById(R.id.pay_detail);
		pay_bankName = root.findViewById(R.id.pay_bankName);
		paypassword_bank_pic = root.findViewById(R.id.paypassword_bank_pic);
		pay_close = root.findViewById(R.id.pay_close);
		paypassword_bank_rl = root.findViewById(R.id.paypassword_bank_rl);
		pwd_bank_ll = root.findViewById(R.id.pwd_bank_ll);
	}
	private void setListenter(){
		del.setOnClickListener(this);
		zero.setOnClickListener(this);
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		five.setOnClickListener(this);
		sex.setOnClickListener(this);
		seven.setOnClickListener(this);
		eight.setOnClickListener(this);
		nine.setOnClickListener(this);
		cancel.setOnClickListener(this);
		sure.setOnClickListener(this);
        pay_close.setOnClickListener(this);
	}
	@Override
	public void onClick(View v){
		if(v==zero){
			parseActionType(KeyboardEnum.zero);
		}else if(v==one){
			parseActionType(KeyboardEnum.one);
		}else if(v==two){
			parseActionType(KeyboardEnum.two);
		}else if(v==three){
			parseActionType(KeyboardEnum.three);
		}else if(v==four){
			parseActionType(KeyboardEnum.four);
		}else if(v==five){
			parseActionType(KeyboardEnum.five);
		}else if(v==sex){
			parseActionType(KeyboardEnum.sex);
		}else if(v==seven){
			parseActionType(KeyboardEnum.seven);
		}else if(v==eight){
			parseActionType(KeyboardEnum.eight);
		}else if(v==nine){
			parseActionType(KeyboardEnum.nine);
		}else if(v==cancel){
			parseActionType(KeyboardEnum.cancel);
		}else if(v==sure){
			parseActionType(KeyboardEnum.sure);
		}else if(v==del){
			parseActionType(KeyboardEnum.del);
		} else if (v == paypassword_bank_rl) {
//			Intent intent = new Intent(mContext, BankManagerActivity.class);
//			intent.putExtra("flag", FLAG_PAYPASSWORD);
//			mContext.startActivityForResult(intent, requestCode);
		} else if (v == pay_close) {
//			cancleRegister();
			listener.onCancelPay();
		}
	}

//	@OnLongClick(R.id.pay_keyboard_del)
//	public boolean onLongClick(View v) {
//		parseActionType(KeyboardEnum.longdel);
//		return false;
//	}

	public void parseActionType(KeyboardEnum type) {
		// TODO Auto-generated method stub
		if(type.getType()== KeyboardEnum.ActionEnum.add){
			if(mList.size()<6){
				mList.add(type.getValue());
				updateUi();
			}
		}else if(type.getType()== KeyboardEnum.ActionEnum.delete){
			if(mList.size()>0){
				mList.remove(mList.get(mList.size()-1));
				updateUi();
			}
		}else if(type.getType()== KeyboardEnum.ActionEnum.cancel){
//			cancleRegister();
			listener.onCancelPay();
		}else if(type.getType()== KeyboardEnum.ActionEnum.sure){
			if(mList.size()<6){
                Toast.makeText(mContext, "支付密码必须6位", Toast.LENGTH_SHORT).show();
			}else{
				String payValue="";
				for (int i = 0; i < mList.size(); i++) {
					payValue+=mList.get(i);
				}
//				cancleRegister();
				listener.onSurePay(payValue);
			}
		}else if(type.getType()== KeyboardEnum.ActionEnum.longClick){
			mList.clear();
			updateUi();
		}

        if (mList.size() == 6) {
            sure.setPressed(true);
            sure.setEnabled(true);
			String payValue="";
			for (int i = 0; i < mList.size(); i++) {
				payValue+=mList.get(i);
			}
			listener.onSurePay(payValue);
        } else {
            sure.setPressed(false);
            sure.setEnabled(false);
        }
	}
	public void updateUi() {
		// TODO Auto-generated method stub
		if(mList.size()==0){
			box1.setText("");
			box2.setText("");
			box3.setText("");
			box4.setText("");
			box5.setText("");
			box6.setText("");
		}else if(mList.size()==1){
			box1.setText(mList.get(0));
			box2.setText("");
			box3.setText("");
			box4.setText("");
			box5.setText("");
			box6.setText("");
		}else if(mList.size()==2){
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText("");
			box4.setText("");
			box5.setText("");
			box6.setText("");
		}else if(mList.size()==3){
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText(mList.get(2));
			box4.setText("");
			box5.setText("");
			box6.setText("");
		}else if(mList.size()==4){
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText(mList.get(2));
			box4.setText(mList.get(3));
			box5.setText("");
			box6.setText("");
		}else if(mList.size()==5){
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText(mList.get(2));
			box4.setText(mList.get(3));
			box5.setText(mList.get(4));
			box6.setText("");
		}else if(mList.size()==6){
			box1.setText(mList.get(0));
			box2.setText(mList.get(1));
			box3.setText(mList.get(2));
			box4.setText(mList.get(3));
			box5.setText(mList.get(4));
			box6.setText(mList.get(5));
		}
	}

	public interface OnPayListener{
		void onCancelPay();
		void onSurePay(String password);
	}

	public View getView(){
		return mView;
	}

	public void initShow(String monney) {
		if (!TextUtils.isEmpty(monney)) {
			content.setText("¥" + monney);
		}
	}
}
