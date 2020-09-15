package com.xhh.ticketver2.beans;

import java.util.List;

/**
 * Author:    hup
 * Date:      2017/10/20.
 * Description:
 */

public class TouZhuEntry implements MultiItemEntity {

    public String id;
    public int type = 0;
    public String schemeId;
    public String schemeType;
    public String isChase;
    public String lotteryTypeId;
    public String lotteryTypeName;
    public String betPeriod;
    public String status;
    public String statusStr;
    public String betTime;
    public String betMoney;
    public CommEntry commEntry;
    public List<TouZhuEntry> mList;
    public String winningAmount;
    public String chaseNum;

    public int getItemType() {
        return type;
    }
}
