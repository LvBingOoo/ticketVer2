package com.xhh.ticketver2.ui.homechart;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;


import com.xhh.ticketver2.utils.MLog;

import java.util.List;

import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

import static android.content.Context.ACTIVITY_SERVICE;


public class SealNotificationReceiver extends PushMessageReceiver {
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage message) {
        MLog.e("push==="+message.getPushContent()+"=data"+message.getPushData()+"flag="+message.getPushFlag());
        if (!TextUtils.isEmpty(message.getPushContent()) && message.getPushContent().startsWith("game_")){
            return true;
        }
        return true;
    }

    /*Intent intent = new Intent();
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    Uri.Builder builder = Uri.parse("rong://" + this.getPackageName()).buildUpon();

    builder.appendPath("conversation").appendPath(type.getName())
            .appendQueryParameter("targetId", targetId)
    .appendQueryParameter("title", targetName);
    uri = builder.build();
    intent.setData(uri);
    startActivity(intent);*/
    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage message) {
        MLog.e("push==="+message.getPushContent() + "act= "+ getTopActivity(context));
        return false;
    }
    String getTopActivity(Context context){
        ActivityManager manager = (ActivityManager)context.getSystemService(ACTIVITY_SERVICE) ;
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = manager.getRunningTasks(1) ;
        if(runningTaskInfos != null)
            return (runningTaskInfos.get(0).topActivity.getClassName()).toString() ;
        else
            return null ;
    }
}
