package com.xhh.ticketver2.ui.view;


import com.xhh.ticketver2.beans.UserInfoEntry;
import com.xhh.ticketver2.ui.view.base.BaseView;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public interface UserView extends BaseView {
    void showGetUserInfoSuccess(UserInfoEntry entry);
}
