package com.xhh.ticketver2.ui.view;


import com.xhh.ticketver2.beans.BillEntry;
import com.xhh.ticketver2.beans.ChongTiEntry;
import com.xhh.ticketver2.ui.view.base.BaseView;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public interface ChongTiView extends BaseView {
    void showGetRechargesSuccess(ChongTiEntry entry);
    void showGetWithdrawalsSuccess(ChongTiEntry entry);
    void showGetBillSuccess(BillEntry entry);
    void refreshOver();
}
