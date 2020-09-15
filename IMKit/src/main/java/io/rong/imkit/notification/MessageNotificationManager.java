//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.rong.imkit.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Vibrator;
import android.text.TextUtils;
import io.rong.common.RLog;
import io.rong.common.SystemUtils;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongNotificationManager;
import io.rong.imkit.model.ConversationInfo;
import io.rong.imkit.model.ConversationKey;
import io.rong.imkit.utils.NotificationUtil;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.MentionedInfo;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.Conversation.ConversationNotificationStatus;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.imlib.model.MentionedInfo.MentionedType;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class MessageNotificationManager {
    private static final String TAG = "MessageNotificationManager";
    private static final int SOUND_INTERVAL = 3000;
    private long lastSoundTime = 0L;

    public MessageNotificationManager() {
    }

    public static MessageNotificationManager getInstance() {
        return MessageNotificationManager.SingletonHolder.instance;
    }

    public void notifyIfNeed(Context context, Message message, int left) {
//        if(message.getContent().getMentionedInfo() != null) {
//            MentionedInfo key = message.getContent().getMentionedInfo();
//            if(key.getType().equals(MentionedType.ALL) || key.getType().equals(MentionedType.PART) && key.getMentionedUserIdList() != null && key.getMentionedUserIdList().contains(RongIMClient.getInstance().getCurrentUserId())) {
//                this.notify(context, message, left);
//                return;
//            }
//        }
//
//        if(!this.isInQuietTime(context)) {
//            ConversationKey key1 = ConversationKey.obtain(message.getTargetId(), message.getConversationType());
//            ConversationNotificationStatus notificationStatus = RongContext.getInstance().getConversationNotifyStatusFromCache(key1);
//            if(notificationStatus != null && notificationStatus == ConversationNotificationStatus.NOTIFY) {
//                this.notify(context, message, left);
//            }
//
//        }
    }

    private void notify(Context context, Message message, int left) {
        boolean isInBackground = SystemUtils.isInBackground(context);
        if(message.getConversationType() != ConversationType.CHATROOM) {
            if(isInBackground) {
                RongNotificationManager.getInstance().onReceiveMessageFromApp(message);
            } else if(!this.isInConversationPager(message.getTargetId(), message.getConversationType()) && left == 0 && System.currentTimeMillis() - this.lastSoundTime > 3000L) {
                this.lastSoundTime = System.currentTimeMillis();
                int ringerMode = NotificationUtil.getRingerMode(context);
                if(ringerMode != 0) {
                    if(ringerMode != 1) {
                        this.sound();
                    }

                    this.vibrate();
                }
            }

        }
    }

    private boolean isInQuietTime(Context context) {
        String startTimeStr = this.getNotificationQuietHoursForStartTime(context);
        int hour = -1;
        int minute = -1;
        int second = -1;
        if(!TextUtils.isEmpty(startTimeStr) && startTimeStr.contains(":")) {
            String[] startCalendar = startTimeStr.split(":");

            try {
                if(startCalendar.length >= 3) {
                    hour = Integer.parseInt(startCalendar[0]);
                    minute = Integer.parseInt(startCalendar[1]);
                    second = Integer.parseInt(startCalendar[2]);
                }
            } catch (NumberFormatException var13) {
                RLog.e("MessageNotificationManager", "getConversationNotificationStatus NumberFormatException");
            }
        }

        if(hour != -1 && minute != -1 && second != -1) {
            Calendar startCalendar1 = Calendar.getInstance();
            startCalendar1.set(Calendar.HOUR_OF_DAY, hour);
            startCalendar1.set(Calendar.MINUTE, minute);
            startCalendar1.set(Calendar.SECOND, second);
            long spanTime = (long)(this.getNotificationQuietHoursForSpanMinutes(context) * 60);
            long startTime = startCalendar1.getTimeInMillis();
            Calendar endCalendar = Calendar.getInstance();
            endCalendar.setTimeInMillis(startTime + spanTime * 1000L);
            Calendar currentCalendar = Calendar.getInstance();
            if(currentCalendar.get(Calendar.DATE) != endCalendar.get(Calendar.DATE)) {
                if(currentCalendar.before(startCalendar1)) {
                    endCalendar.set(Calendar.DATE, currentCalendar.get(Calendar.DATE));
                    return currentCalendar.before(endCalendar);
                } else {
                    return true;
                }
            } else {
                return currentCalendar.after(startCalendar1) && currentCalendar.before(endCalendar);
            }
        } else {
            return false;
        }
    }

    private boolean isInConversationPager(String id, ConversationType type) {
        List list = RongContext.getInstance().getCurrentConversationList();
        Iterator i$ = list.iterator();
        if(!i$.hasNext()) {
            return false;
        } else {
            ConversationInfo conversationInfo = (ConversationInfo)i$.next();
            return id.equals(conversationInfo.getTargetId()) && type == conversationInfo.getConversationType();
        }
    }

    public void saveNotificationQuietHours(Context mContext, String startTime, int spanMinutes) {
        SharedPreferences preferences = mContext.getSharedPreferences("RONG_SDK", 0);
        Editor editor = preferences.edit();
        editor.putString("QUIET_HOURS_START_TIME", startTime);
        editor.putInt("QUIET_HOURS_SPAN_MINUTES", spanMinutes);
        editor.apply();
    }

    private String getNotificationQuietHoursForStartTime(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("RONG_SDK", 0);
        return preferences.getString("QUIET_HOURS_START_TIME", "");
    }

    private int getNotificationQuietHoursForSpanMinutes(Context mContext) {
        SharedPreferences preferences = mContext.getSharedPreferences("RONG_SDK", 0);
        return preferences.getInt("QUIET_HOURS_SPAN_MINUTES", 0);
    }

    private void sound() {
        Uri res = RingtoneManager.getDefaultUri(2);

        try {
            MediaPlayer e = new MediaPlayer();
            e.setOnCompletionListener(new OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
                    mp.reset();
                    mp.release();
                }
            });
            e.setAudioStreamType(2);
            e.setDataSource(RongContext.getInstance(), res);
            e.prepare();
            e.start();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator)RongContext.getInstance().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{0L, 200L, 250L, 200L}, -1);
    }

    private static class SingletonHolder {
        static final MessageNotificationManager instance = new MessageNotificationManager();

        private SingletonHolder() {
        }
    }
}
