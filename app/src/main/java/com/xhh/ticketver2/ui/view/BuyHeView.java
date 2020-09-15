package com.xhh.ticketver2.ui.view;


import com.xhh.ticketver2.beans.BuyHeEntry;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.SchemeEntry;
import com.xhh.ticketver2.ui.view.base.BaseView;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public interface BuyHeView extends BaseView {
    void showGetHeListSuccess(BuyHeEntry entry);
    void showGetSchemeDetailSuccess(SchemeEntry entry);
    void showBetPartnershipSuccess(CommEntry entry);
    void pullRefreshOver();
}
