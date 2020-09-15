package com.xhh.ticketver2.ui.view;


import com.xhh.ticketver2.beans.TouZhuEntry;
import com.xhh.ticketver2.ui.view.base.BaseView;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public interface TouZhuView extends BaseView {
    void showGetBetOrdersSuccess(TouZhuEntry entry);
    void refreshOver();
}
