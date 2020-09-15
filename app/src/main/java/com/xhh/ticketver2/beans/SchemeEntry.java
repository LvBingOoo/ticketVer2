package com.xhh.ticketver2.beans;

import java.util.List;

/**
 * Author:    hup
 * Date:      2017/11/16.
 * Description:
 */

public class SchemeEntry {
    public CommEntry commEn;
    public String lotteryTypeName;
    public String headPortrait;
    public String userName;
    public String bettingTotailAmount;
    public String unitPrice;
    public String completeAmount = "0";
    public String schemeId;
    public String chaseNum;
    public String betPeriod;
    public int totalNum;
    public List<HeBuyUserEntry> mUserList;
    public List<SchemeInfoEntry> mListSchemeInfo;
    public String betMultiple;
    public String winningTotailAmount;
    public String status;
    public String openNumber;
    public String modMoney;
    public List<HeBuyDoEntry> mIssueList;
    public String minNum = "0";
    public int purchasedNum;
    public String isChase;
    public String schemeType;
    public String userGrade;
    public int actualMinNum;
}
