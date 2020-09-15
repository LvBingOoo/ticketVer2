package com.xhh.ticketver2.beans;

import java.util.List;

/**
 * Author:    hup
 * Date:      2017/10/20.
 * Description:
 */

public class HomeBuyEntry implements MultiItemEntity {

    public String id;
    public int type = 0;
    public CommEntry commEntry;
    public String lotteryTypeName;
    public String describes;
    public String lotteryTypeId;
    public String icon;
    public List<HomeBuyEntry> mList;
    public String nameGroup;
    public List<HomeBuyEntry> mMeiList;

    public int getItemType() {
        return type;
    }
}
