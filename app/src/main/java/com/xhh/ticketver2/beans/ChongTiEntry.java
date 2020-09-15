package com.xhh.ticketver2.beans;

import java.util.List;

/**
 * Author:    hup
 * Date:      2017/11/21.
 * Description:
 */

public class ChongTiEntry implements MultiItemEntity{
    public String id;
    public int type = 0;
    public int getItemType() {
        return type;
    }

    public CommEntry commEntry;
    public String rechargeAmount;
    public String rechargeChannelStr;
    public String createTimeStr;
    public String statusStr;
    public List<ChongTiEntry> mList;
}
