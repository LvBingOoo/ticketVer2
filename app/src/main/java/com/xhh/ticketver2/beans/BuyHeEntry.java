package com.xhh.ticketver2.beans;

import java.util.List;

/**
 * Author:    hup
 * Date:      2017/10/20.
 * Description:
 */

public class BuyHeEntry implements MultiItemEntity {

    public String schemeId;
    public int type = 0;
    public CommEntry commEntry;
    public String lotteryTypeName;
    public String headPortrait;
    public String userName;
    public String bettingTotailAmount;
    public String unitPrice;
    public String completeAmount;
    public List<BuyHeEntry> mList;
    public String chaseNum;
    public String betPeriod;
    public int purchasedNum;
    public int totalNum;
    public String userGrade;
    public int actualMinNum;
    public String status;
    public String statusStr;

    public int getItemType() {
        return type;
    }
}
