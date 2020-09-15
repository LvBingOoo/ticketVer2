package com.xhh.ticketver2.ui.view;

import com.xhh.ticketver2.beans.BannaerEntry;
import com.xhh.ticketver2.beans.HomeBuyEntry;
import com.xhh.ticketver2.ui.view.base.BaseView;

/**
 * Author:    hup
 * Date:      2017/11/21.
 * Description:
 */

public interface HomeBuyView extends BaseView{
    void showGetLotteryTypeGroupsSuccess(HomeBuyEntry entry);
    void showGetLotteryTypeSuccess(HomeBuyEntry entry);
    void showGetBlanSuccess(BannaerEntry entry);
    void showGetWinPurchaseSuccess(WinEntry entry);
}
