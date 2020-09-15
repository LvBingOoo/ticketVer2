package com.xhh.ticketver2.ui.view;

import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.MyTeamListBean;
import com.xhh.ticketver2.ui.view.base.BaseView;

import java.util.Map;

public interface MyTeamView extends BaseView {
    void getListSuccess(MyTeamListBean response);

    void getListError();
}
