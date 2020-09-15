package com.xhh.ticketver2.ui.view;


import com.xhh.ticketver2.beans.AwardEntry;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.GameInfoEntry;
import com.xhh.ticketver2.beans.GameListEntry;
import com.xhh.ticketver2.ui.view.base.BaseView;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public interface BuyView extends BaseView {
    void showGetPeriodsSuccess(GameInfoEntry entry);
    void showGetPeriodsMoreSuccess(GameInfoEntry entry);
    void showGetNumberPropSuccess(CommEntry entry);
    void showGetPlaySuccess(GameListEntry entry);
    void showGetNewNumberSuccess(AwardEntry entry);
    void showCalculateBetNumSuccess(CalculateEntry entry);
    void showBetSuccess(CommEntry entry);
}
