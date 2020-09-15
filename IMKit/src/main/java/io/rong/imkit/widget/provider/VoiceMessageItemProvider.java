package io.rong.imkit.widget.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import io.rong.common.RLog;
import io.rong.eventbus.EventBus;
import io.rong.imkit.R;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.manager.AudioPlayManager;
import io.rong.imkit.manager.AudioRecordManager;
import io.rong.imkit.manager.IAudioPlayListener;
import io.rong.imkit.model.Event;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.utilities.OptionsPopupDialog;
import io.rong.imkit.utilities.RongUtils;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.VoiceMessage;

@ProviderTag(
        messageContent = VoiceMessage.class,
        showReadState = true
)
public class VoiceMessageItemProvider extends IContainerItemProvider.MessageProvider<VoiceMessage> {
    private static final String TAG = "VoiceMessageItemProvider";

    public VoiceMessageItemProvider(Context context) {
    }

    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_item_voice_message, (ViewGroup)null);
        ViewHolder holder = new ViewHolder();
        holder.left = (TextView)view.findViewById(R.id.rc_left);
        holder.right = (TextView)view.findViewById(R.id.rc_right);
        holder.img = (ImageView)view.findViewById(R.id.rc_img);
        holder.unread = (ImageView)view.findViewById(R.id.rc_voice_unread);
        view.setTag(holder);
        return view;
    }

    public void bindView(View v, int position, VoiceMessage content, UIMessage message) {
        ViewHolder holder = (ViewHolder)v.getTag();
        Uri playingUri;
        boolean listened;
        if(message.continuePlayAudio) {
            playingUri = AudioPlayManager.getInstance().getPlayingUri();
            if(playingUri == null || !playingUri.equals(content.getUri())) {
                listened = message.getMessage().getReceivedStatus().isListened();
                AudioPlayManager.getInstance().startPlay(v.getContext(), content.getUri(), new VoiceMessagePlayListener(v.getContext(), message, holder, listened));
            }
        } else {
            playingUri = AudioPlayManager.getInstance().getPlayingUri();
            if(playingUri != null && playingUri.equals(content.getUri())) {
                this.setLayout(v.getContext(), holder, message, true);
                listened = message.getMessage().getReceivedStatus().isListened();
                AudioPlayManager.getInstance().setPlayListener(new VoiceMessagePlayListener(v.getContext(), message, holder, listened));
            } else {
                this.setLayout(v.getContext(), holder, message, false);
            }
        }

    }

    public void onItemClick(View view, int position, VoiceMessage content, UIMessage message) {
//        Log.e("VoiceMessageItemProvider","Item index:" + position);
        ViewHolder holder = (ViewHolder)view.getTag();
        holder.unread.setVisibility(View.GONE);
        Uri playingUri = AudioPlayManager.getInstance().getPlayingUri();
        if(playingUri != null && playingUri.equals(content.getUri())) {
            AudioPlayManager.getInstance().stopPlay();
        } else {
            boolean listened = message.getMessage().getReceivedStatus().isListened();
            AudioPlayManager.getInstance().startPlay(view.getContext(), content.getUri(), new VoiceMessagePlayListener(view.getContext(), message, holder, listened));
        }

    }

    public void onItemLongClick(final View view, int position, VoiceMessage content, final UIMessage message) {
        long deltaTime = RongIM.getInstance().getDeltaTime();
        long normalTime = System.currentTimeMillis() - deltaTime;
        boolean enableMessageRecall = false;
        int messageRecallInterval = -1;
        boolean hasSent = !message.getSentStatus().equals(Message.SentStatus.SENDING) && !message.getSentStatus().equals(Message.SentStatus.FAILED);

        try {
            enableMessageRecall = RongContext.getInstance().getResources().getBoolean(R.bool.rc_enable_message_recall);
            messageRecallInterval = RongContext.getInstance().getResources().getInteger(R.integer.rc_message_recall_interval);
        } catch (Resources.NotFoundException var14) {
            RLog.e("VoiceMessageItemProvider", "rc_message_recall_interval not configure in rc_config.xml");
            var14.printStackTrace();
        }

        String[] items;
        if(hasSent && enableMessageRecall && normalTime - message.getSentTime() <= (long)(messageRecallInterval * 1000) && message.getSenderUserId().equals(RongIM.getInstance().getCurrentUserId()) && message.getSenderUserId().equals(RongIM.getInstance().getCurrentUserId()) && !message.getConversationType().equals(Conversation.ConversationType.CUSTOMER_SERVICE) && !message.getConversationType().equals(Conversation.ConversationType.APP_PUBLIC_SERVICE) && !message.getConversationType().equals(Conversation.ConversationType.PUBLIC_SERVICE) && !message.getConversationType().equals(Conversation.ConversationType.SYSTEM) && !message.getConversationType().equals(Conversation.ConversationType.CHATROOM)) {
            items = new String[]{view.getContext().getResources().getString(R.string.rc_dialog_item_message_delete), view.getContext().getResources().getString(R.string.rc_dialog_item_message_recall)};
        } else {
            items = new String[]{view.getContext().getResources().getString(R.string.rc_dialog_item_message_delete)};
        }

        OptionsPopupDialog.newInstance(view.getContext(), items).setOptionsPopupDialogListener(new OptionsPopupDialog.OnOptionsItemClickedListener() {
            public void onOptionsItemClicked(int which) {
                if(which == 0) {
                    AudioPlayManager.getInstance().stopPlay();
                    RongIM.getInstance().deleteMessages(new int[]{message.getMessageId()}, (RongIMClient.ResultCallback)null);
                } else if(which == 1) {
                    if(AudioPlayManager.getInstance().getPlayingUri() != null) {
                        AudioPlayManager.getInstance().stopPlay();
                    }

                    RongIM.getInstance().recallMessage(message.getMessage(), VoiceMessageItemProvider.this.getPushContent(view.getContext(), message));
                }

            }
        }).show();
    }

    private void setLayout(Context context, ViewHolder holder, UIMessage message, boolean playing) {
        VoiceMessage content = (VoiceMessage)message.getContent();
        byte minLength = 57;
        int duration = AudioRecordManager.getInstance().getMaxVoiceDuration();
        holder.img.getLayoutParams().width = (int)((float)(content.getDuration() * (180 / duration) + minLength) * context.getResources().getDisplayMetrics().density);
        holder.img.setPadding(RongUtils.dip2px(0),RongUtils.dip2px(0),RongUtils.dip2px(0),RongUtils.dip2px(0));
        AnimationDrawable animationDrawable;
        if(message.getMessageDirection() == Message.MessageDirection.SEND) {
            holder.left.setText(String.format("%s\"", new Object[]{Integer.valueOf(content.getDuration())}));
            holder.left.setVisibility(View.VISIBLE);
            holder.right.setVisibility(View.GONE);
            holder.unread.setVisibility(View.GONE);
            holder.img.setScaleType(ImageView.ScaleType.CENTER);
            holder.img.setBackgroundResource(R.drawable.rc_ic_bubble_right);
            animationDrawable = (AnimationDrawable)context.getResources().getDrawable(R.drawable.rc_an_voice_sent);
            if(playing) {
                holder.img.setImageDrawable(animationDrawable);
                if(animationDrawable != null) {
                    animationDrawable.start();
                }
            } else {
                holder.img.setImageDrawable(holder.img.getResources().getDrawable(R.drawable.rc_ic_voice_sent));
                if(animationDrawable != null) {
                    animationDrawable.stop();
                }
            }
        } else {
            holder.right.setText(String.format("%s\"", new Object[]{Integer.valueOf(content.getDuration())}));
            holder.right.setVisibility(View.VISIBLE);
            holder.left.setVisibility(View.GONE);
            if(!message.getReceivedStatus().isListened()) {
                holder.unread.setVisibility(View.VISIBLE);
            } else {
                holder.unread.setVisibility(View.GONE);
            }

            holder.img.setBackgroundResource(R.drawable.rc_ic_bubble_left);
            animationDrawable = (AnimationDrawable)context.getResources().getDrawable(R.drawable.rc_an_voice_receive);
            if(playing) {
                holder.img.setImageDrawable(animationDrawable);
                if(animationDrawable != null) {
                    animationDrawable.start();
                }
            } else {
                holder.img.setImageDrawable(holder.img.getResources().getDrawable(R.drawable.rc_ic_voice_receive));
                if(animationDrawable != null) {
                    animationDrawable.stop();
                }
            }

            holder.img.setScaleType(ImageView.ScaleType.CENTER);
        }

    }

    public Spannable getContentSummary(VoiceMessage data) {
        return new SpannableString(RongContext.getInstance().getString(R.string.rc_message_content_voice));
    }

    @TargetApi(8)
    private boolean muteAudioFocus(Context context, boolean bMute) {
        if(context == null) {
            RLog.d("VoiceMessageItemProvider", "muteAudioFocus context is null.");
            return false;
        } else if(Build.VERSION.SDK_INT < 8) {
            RLog.d("VoiceMessageItemProvider", "muteAudioFocus Android 2.1 and below can not stop music");
            return false;
        } else {
            boolean bool = false;
            AudioManager am = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
            int result;
            if(bMute) {
                result = am.requestAudioFocus((AudioManager.OnAudioFocusChangeListener)null, 3, 2);
                bool = result == 1;
            } else {
                result = am.abandonAudioFocus((AudioManager.OnAudioFocusChangeListener)null);
                bool = result == 1;
            }

            RLog.d("VoiceMessageItemProvider", "muteAudioFocus pauseMusic bMute=" + bMute + " result=" + bool);
            return bool;
        }
    }

    private class VoiceMessagePlayListener implements IAudioPlayListener {
        private Context context;
        private UIMessage message;
        private ViewHolder holder;
        private boolean listened;

        public VoiceMessagePlayListener(Context context, UIMessage message, ViewHolder holder, boolean listened) {
            this.context = context;
            this.message = message;
            this.holder = holder;
            this.listened = listened;
        }

        public void onStart(Uri uri) {
            this.message.continuePlayAudio = false;
            this.message.setListening(true);
            this.message.getReceivedStatus().setListened();
            RongIMClient.getInstance().setMessageReceivedStatus(this.message.getMessageId(), this.message.getReceivedStatus(), (RongIMClient.ResultCallback)null);
            VoiceMessageItemProvider.this.setLayout(this.context, this.holder, this.message, true);
            EventBus.getDefault().post(new Event.AudioListenedEvent(this.message.getMessage()));
        }

        public void onStop(Uri uri) {
            this.message.setListening(false);
            VoiceMessageItemProvider.this.setLayout(this.context, this.holder, this.message, false);
        }

        public void onComplete(Uri uri) {
            Event.PlayAudioEvent event = Event.PlayAudioEvent.obtain();
            event.messageId = this.message.getMessageId();
            if(this.message.isListening() && this.message.getMessageDirection().equals(Message.MessageDirection.RECEIVE)) {
                try {
                    event.continuously = RongContext.getInstance().getResources().getBoolean(R.bool.rc_play_audio_continuous);
                } catch (Resources.NotFoundException var4) {
                    var4.printStackTrace();
                }
            }

            if(event.continuously) {
                EventBus.getDefault().post(event);
            }

            this.message.setListening(false);
            VoiceMessageItemProvider.this.setLayout(this.context, this.holder, this.message, false);
        }
    }

    private static class ViewHolder {
        ImageView img;
        TextView left;
        TextView right;
        ImageView unread;

        private ViewHolder() {
        }
    }
}
