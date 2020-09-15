package com.xhh.ticketver2.ui.view;


import com.xhh.ticketver2.beans.AwardEntry;
import com.xhh.ticketver2.ui.view.base.BaseView;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public interface AwardView extends BaseView {
    void showGetNewNumberSuccess(AwardEntry entry);
    void showGetLotteryNumbersSuccess(AwardEntry entry);
    void pullRefreshOver();
}
