package com.xhh.ticketver2.ui.homechart;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import io.rong.message.RichContentMessage;

/**
 * Author:    hup
 * Date:      2016/12/5.
 * Description:
 */

public class MyReceiveMessageListener  implements RongIMClient.OnReceiveMessageListener{
    /**
     * 收到消息的处理。
     * @param message 收到的消息实体。
     * @param left 剩余未拉取消息数目。
     * @return
     */
    @Override
    public boolean onReceived(Message message, int i) {
        RichContentMessage msg = (RichContentMessage) message.getContent();
        String content = msg.getContent();
        String extra = msg.getExtra();
        String imgurl = msg.getImgUrl();
        return false;
    }
}
