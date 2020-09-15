package com.xhh.ticketver2.ui.view;


import com.xhh.ticketver2.beans.BankEntry;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.ui.view.base.BaseView;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public interface BankView extends BaseView {
    void showGetBanksSuccess(BankEntry entry);
    void showAddBanksSuccess(CommEntry entry);
    void showUpdateBanksSuccess(CommEntry entry);
}
