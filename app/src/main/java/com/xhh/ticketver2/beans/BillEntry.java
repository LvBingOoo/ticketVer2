package com.xhh.ticketver2.beans;

import java.util.List;

/**
 * Author:    hup
 * Date:      2017/11/21.
 * Description:
 */

public class BillEntry implements MultiItemEntity{
    public String id;
    public int type = 0;
    public CommEntry commEntry;
    public String acTypeStr;
    public String changeAmount;
    public String createTime;
    public List<BillEntry> mList;
    public String beforeAvailableFund;
    public String afterAvailableFund;

    public int getItemType() {
        return type;
    }
}
