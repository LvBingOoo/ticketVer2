package com.xhh.ticketver2.beans;

/**
 * Author:    hup
 * Date:      2017/10/20.
 * Description:
 */

public class GameTypeEntry implements MultiItemEntity {

    public String id;
    public boolean isSelect;
    public int type = 0;
    public int getItemType() {
        return type;
    }
}
