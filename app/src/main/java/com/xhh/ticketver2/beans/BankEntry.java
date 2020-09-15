package com.xhh.ticketver2.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Author:    hup
 * Date:      2017/11/15.
 * Description:
 */

public class BankEntry implements Serializable{
    public CommEntry commEntry;
    public String bankcarId;
    public String accountName;
    public String bankName;
    public String bankNumber;
    public String bankArea;
    public List<BankEntry> mList;
}
