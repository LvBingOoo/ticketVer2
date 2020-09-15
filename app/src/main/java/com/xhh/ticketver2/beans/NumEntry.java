package com.xhh.ticketver2.beans;

import java.io.Serializable;

/**
 * Author:    hup
 * Date:      2017/10/20.
 * Description:
 */

public class NumEntry implements MultiItemEntity,Serializable {

    public String id;
    public boolean isSelect;
    public int type = 0;

    /* 显示的数字 */
    public String num;
    // /* 是否选中 */
    // public boolean isSelect;
    public String showS;// 小
    public String showB;// 大
    public String showL;// 漏选


    public int getItemType() {
        return type;
    }
}
