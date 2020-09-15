//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.rong.imkit.widget.provider;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.graphics.Color;
import android.text.ClipboardManager;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import io.rong.common.RLog;
import io.rong.imkit.R;
import io.rong.imkit.RongContext;
import io.rong.imkit.RongIM;
import io.rong.imkit.R.bool;
import io.rong.imkit.R.drawable;
import io.rong.imkit.R.integer;
import io.rong.imkit.R.layout;
import io.rong.imkit.R.string;
import io.rong.imkit.RongIM.ConversationBehaviorListener;
import io.rong.imkit.emoticon.AndroidEmoji;
import io.rong.imkit.model.ProviderTag;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.utilities.OptionsPopupDialog;
import io.rong.imkit.utilities.OptionsPopupDialog.OnOptionsItemClickedListener;
import io.rong.imkit.utilities.RongUtils;
import io.rong.imkit.widget.AutoLinkTextView;
import io.rong.imkit.widget.ILinkClickListener;
import io.rong.imkit.widget.LinkTextViewMovementMethod;
import io.rong.imkit.widget.provider.IContainerItemProvider.MessageProvider;
import io.rong.imlib.RongIMClient.ResultCallback;
import io.rong.imlib.model.Conversation.ConversationType;
import io.rong.imlib.model.Message.MessageDirection;
import io.rong.imlib.model.Message.SentStatus;
import io.rong.message.TextMessage;

import static android.R.id.text1;

@ProviderTag(
        messageContent = TextMessage.class,
        showReadState = true
)
public class TextMessageItemProvider extends MessageProvider<TextMessage> {
    private static final String TAG = "TextMessageItemProvider";

    public TextMessageItemProvider() {
    }

    public View newView(Context context, ViewGroup group) {
        View view = LayoutInflater.from(context).inflate(R.layout.rc_item_text_message, null);
        TextMessageItemProvider.ViewHolder holder = new TextMessageItemProvider.ViewHolder();
        holder.message = (AutoLinkTextView)view.findViewById(android.R.id.text1);
        view.setTag(holder);
        return view;
    }

    public Spannable getContentSummary(TextMessage data) {
        if(data == null) {
            return null;
        } else {
            String content = data.getContent();
            if(content != null) {
                if(content.length() > 100) {
                    content = content.substring(0, 100);
                }

                return new SpannableString(AndroidEmoji.ensure(content));
            } else {
                return null;
            }
        }
    }

    public void onItemClick(View view, int position, TextMessage content, UIMessage message) {
    }

    public void onItemLongClick(final View view, int position, final TextMessage content, final UIMessage message) {
        TextMessageItemProvider.ViewHolder holder = (TextMessageItemProvider.ViewHolder)view.getTag();
        holder.longClick = true;
        if(view instanceof TextView) {
            CharSequence items = ((TextView)view).getText();
            if(items != null && items instanceof Spannable) {
                Selection.removeSelection((Spannable)items);
            }
        }

        long deltaTime = RongIM.getInstance().getDeltaTime();
        long normalTime = System.currentTimeMillis() - deltaTime;
        boolean enableMessageRecall = false;
        int messageRecallInterval = -1;
        boolean hasSent = !message.getSentStatus().equals(SentStatus.SENDING) && !message.getSentStatus().equals(SentStatus.FAILED);

        try {
            enableMessageRecall = RongContext.getInstance().getResources().getBoolean(bool.rc_enable_message_recall);
            messageRecallInterval = RongContext.getInstance().getResources().getInteger(integer.rc_message_recall_interval);
        } catch (NotFoundException var15) {
            RLog.e("TextMessageItemProvider", "rc_message_recall_interval not configure in rc_config.xml");
            var15.printStackTrace();
        }

        String[] items1;
        if(hasSent && enableMessageRecall && normalTime - message.getSentTime() <= (long)(messageRecallInterval * 1000) && message.getSenderUserId().equals(RongIM.getInstance().getCurrentUserId()) && !message.getConversationType().equals(ConversationType.CUSTOMER_SERVICE) && !message.getConversationType().equals(ConversationType.APP_PUBLIC_SERVICE) && !message.getConversationType().equals(ConversationType.PUBLIC_SERVICE) && !message.getConversationType().equals(ConversationType.SYSTEM) && !message.getConversationType().equals(ConversationType.CHATROOM)) {
            items1 = new String[]{view.getContext().getResources().getString(string.rc_dialog_item_message_copy), view.getContext().getResources().getString(string.rc_dialog_item_message_delete), view.getContext().getResources().getString(string.rc_dialog_item_message_recall)};
        } else {
            items1 = new String[]{view.getContext().getResources().getString(string.rc_dialog_item_message_copy), view.getContext().getResources().getString(string.rc_dialog_item_message_delete)};
        }

        OptionsPopupDialog.newInstance(view.getContext(), items1).setOptionsPopupDialogListener(new OnOptionsItemClickedListener() {
            public void onOptionsItemClicked(int which) {
                if(which == 0) {
                    ClipboardManager clipboard = (ClipboardManager)view.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    clipboard.setText(content.getContent());
                } else if(which == 1) {
                    RongIM.getInstance().deleteMessages(new int[]{message.getMessageId()}, null);
                } else if(which == 2) {
                    RongIM.getInstance().recallMessage(message.getMessage(), TextMessageItemProvider.this.getPushContent(view.getContext(), message));
                }

            }
        }).show();
    }

    public void bindView(final View v, int position, TextMessage content, final UIMessage data) {
        TextMessageItemProvider.ViewHolder holder = (TextMessageItemProvider.ViewHolder)v.getTag();
        if(data.getMessageDirection() == MessageDirection.SEND) {
            holder.message.setBackgroundResource(R.drawable.rc_ic_bubble_right);
            holder.message.setPadding(RongUtils.dip2px(16),RongUtils.dip2px(2),RongUtils.dip2px(12),RongUtils.dip2px(2));
            holder.message.setTextColor(Color.WHITE);
        } else {
            holder.message.setTextColor(Color.BLACK);
            holder.message.setBackgroundResource(R.drawable.rc_ic_bubble_left);
            holder.message.setPadding(RongUtils.dip2px(12),RongUtils.dip2px(2),RongUtils.dip2px(16),RongUtils.dip2px(2));
        }

        final AutoLinkTextView textView = holder.message;
        if(data.getTextMessageContent() != null) {
            int len = data.getTextMessageContent().length();
            if(v.getHandler() != null && len > 500) {
                v.getHandler().postDelayed(new Runnable() {
                    public void run() {
                        textView.setText(data.getTextMessageContent());
                    }
                }, 50L);
            } else {
                textView.setText(data.getTextMessageContent());
            }
        }

        holder.message.setMovementMethod(new LinkTextViewMovementMethod(new ILinkClickListener() {
            public boolean onLinkClick(String link) {
                ConversationBehaviorListener listener = RongContext.getInstance().getConversationBehaviorListener();
                boolean result = false;
                if(listener != null) {
                    result = listener.onMessageLinkClick(v.getContext(), link);
                }

                if(listener == null || !result) {
                    String str = link.toLowerCase();
                    if(str.startsWith("http") || str.startsWith("https")) {
                        Intent intent = new Intent("io.rong.imkit.intent.action.webview");
                        intent.setPackage(v.getContext().getPackageName());
                        intent.putExtra("url", link);
                        v.getContext().startActivity(intent);
                        result = true;
                    }
                }

                return result;
            }
        }));
    }

    private static class ViewHolder {
        AutoLinkTextView message;
        boolean longClick;

        private ViewHolder() {
        }
    }
}
