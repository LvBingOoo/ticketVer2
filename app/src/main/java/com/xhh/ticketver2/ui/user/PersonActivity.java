package com.xhh.ticketver2.ui.user;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v13.app.ActivityCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hhhc.obsessive.library.eventbus.EventCenter;
import com.hhhc.obsessive.library.netstatus.NetUtils;
import com.lling.photopicker.PhotoPickerActivity;
import com.xhh.ticketver2.R;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.UserInfoEntry;
import com.xhh.ticketver2.presenter.PersonPresenter;
import com.xhh.ticketver2.presenter.UserPresenter;
import com.xhh.ticketver2.ui.adapter.user.MyTouZhuAdapter;
import com.xhh.ticketver2.ui.base.BaseActivity;
import com.xhh.ticketver2.ui.view.PersonView;
import com.xhh.ticketver2.ui.view.UserView;
import com.xhh.ticketver2.utils.BmpUtil;

import java.io.File;
import java.util.ArrayList;

import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonActivity extends BaseActivity implements MyTouZhuAdapter.onItemClickListenter ,UserView, PersonView {

    @InjectView(R.id.person_header)
    CircleImageView person_header;
    @InjectView(R.id.person_nickname)
    EditText person_nickname;
    @InjectView(R.id.person_qq)
    EditText person_qq;
    UserPresenter userPresenter;
    PersonPresenter personPresenter;

    String mPath;
    @Override
    protected void initViewsAndEvents() {
        initTopBar();
        mTopbarTitleTv.setText("基本资料");
        mTopbarRightTv.setText("保存");
        userPresenter = new UserPresenter(this,mContext);
        personPresenter = new PersonPresenter(this,mContext);
        userPresenter.postGetInfo();
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_person;
    }

    @OnClick({R.id.topbar_right_text,R.id.person_header_ll})
    public void onclick(View v){
        switch (v.getId()){
            case R.id.topbar_right_text:
                String nickName = person_nickname.getText().toString();
                String qq = person_qq.getText().toString();
                personPresenter.postUserUpdate(nickName,qq);
                break;
            case R.id.person_header_ll:
                checkPermission();
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
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    @Override
    public void onItemClick(int pos) {
    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void showGetUserInfoSuccess(UserInfoEntry entry) {
        if (entry.commEntry.status == Const.STATUS_SUCCESS){
            Glide.with(this).load(entry.headPortrait).dontAnimate().fitCenter().into(person_header);
            person_nickname.setText(entry.nickName);
            person_qq.setText(entry.qq);
        }
    }

    @Override
    public void showUpdateUserSuccess(CommEntry entry) {
        if (entry.status == Const.STATUS_SUCCESS){
            showToast("修改成功");
        }else{
            showToast(entry.msg);
        }
    }

    @Override
    public void showUpdateHeaderSuccess(final CommEntry entry) {
        mTopbarTitleTv.post(new Runnable() {
            @Override
            public void run() {
                if (entry.status == Const.STATUS_SUCCESS){
                    Glide.with(PersonActivity.this).load(mPath).fitCenter().dontAnimate().into(person_header);
                    showToast("上传成功");
                }else{
                    showToast(entry.msg);
                }

            }
        });

    }


    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            getPhoto();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0x001);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0x001) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto();
            } else {
                Toast.makeText(this,"权限获取失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private static final int PICK_PHOTO = 101;
    private void getPhoto(){
        Intent intent = new Intent(this, PhotoPickerActivity.class);
        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_SINGLE);
        intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN,1);
        startActivityForResult(intent, PICK_PHOTO);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_PHOTO){
            if(resultCode == RESULT_OK){
                 ArrayList<String> result = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                if (result != null && !result.isEmpty()){
                    mPath = BmpUtil.compressByScale(result.get(0));
                    personPresenter.postUploadHeader(new File(mPath));
                }
            }
        }
    }
}
