package com.xhh.ticketver2.ui.view;


import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.ui.view.base.BaseView;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public interface DomainView extends BaseView {
    void showDomainSuccess(CommEntry entry);
    void showDomainFath();
}
