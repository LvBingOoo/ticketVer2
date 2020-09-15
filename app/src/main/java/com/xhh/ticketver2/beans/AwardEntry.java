package com.xhh.ticketver2.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Author:    hup
 * Date:      2017/10/20.
 * Description:
 */

public class AwardEntry implements MultiItemEntity ,Serializable{

    public String id;
    public int type = 0;
    public String numberId;
    public String period;
    public String lotteryTypeId;
    public String lotteryTypeName;
    public String dwawNumber;
    public String status;
    public CommEntry commEntry;
    public List<AwardEntry> mList;
    public String openTimeStr;

    public int getItemType() {
        return type;
    }
}
