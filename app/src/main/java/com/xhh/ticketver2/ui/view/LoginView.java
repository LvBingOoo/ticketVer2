package com.xhh.ticketver2.ui.view;


import com.xhh.ticketver2.beans.LoginEntry;
import com.xhh.ticketver2.ui.view.base.BaseView;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public interface LoginView extends BaseView {
    void showLoginSuccess(LoginEntry commEntry);
}
