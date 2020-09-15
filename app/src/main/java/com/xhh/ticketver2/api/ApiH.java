package com.xhh.ticketver2.api;

/**
 * Author:    hup
 * Date:      2017/10/20.
 * Description:
 */

public class ApiH {


    public static String NETWORK_ERROR = "网络异常，请稍后再试";
    public static String LOADING = "加载中";
    public static String NO_DATA = "暂无数据";
    public static String UPLOADING = "上传中";
    //    public static String URL_GET_DOMAIN = "http://116.255.137.149:9722/home/index";
    public static String URL_GET_DOMAIN = "http://test.iddc.site:8888";
    //    public static String ENDPOINT = "http://test.iddc.site:8899/";
    public static String ENDPOINT = "http://www.wanhao.zone:8086/";

    public static String URL_SHARE = "http://www.wanhao.zone:8085/lot/home/reg/";

    public static String URL_PAY_ALIPAY = ENDPOINT + "user/bankPay";
    public static String URL_PAY = ENDPOINT + "finance/pay";
    public static String URL_USER_LOGIN = ENDPOINT + "user/login";
    public static String URL_USER_GETINFO = ENDPOINT + "user/getinfo";
    public static String URL_USER_GETBANKS = ENDPOINT + "user/getbanks";
    public static String URL_USER_UPDATEBANK = ENDPOINT + "user/updatebank";
    public static String URL_USER_ADDBANK = ENDPOINT + "user/addbank";
    public static String URL_USER_UPDATEPWD = ENDPOINT + "user/updatepwd";
    public static String URL_USER_UPDATEPAYPWD = ENDPOINT + "user/updatepaypwd";
    public static String URL_USER_UPDATE = ENDPOINT + "user/update";
    public static String URL_USER_UPLOADUSERHEADIMG = ENDPOINT + "user/uploadUserHeadImg";

    public static String URL_NUMBER_GETNEWNUMBERS = ENDPOINT + "number/getnewnumbers";
    public static String URL_NUMBER_GETLOTTERYNUMBERS = ENDPOINT + "number/getlotterynumbers";
    public static String URL_FINANCE_WITHDRAWALAPPLY = ENDPOINT + "finance/withdrawalapply";
    public static String URL_SYS_GETSYSSET = ENDPOINT + "sys/getsysset";
    public static String URL_LOTTERY_GETPTORDERS = ENDPOINT + "lottery/getptorders";
    public static String URL_LOTTERY_GETSCHEMEDETAIL = ENDPOINT + "lottery/getschemedetail";
    public static String URL_LOTTERY_BETPARTNERSHIP = ENDPOINT + "lottery/betPartnership";

    public static String URL_LOTTERY_GETPERIODS = ENDPOINT + "lottery/getperiods";
    public static String URL_LOTTERY_GETLOTTERYNUMBERPROP = ENDPOINT + "lottery/getlotterynumberprop";
    public static String URL_LOTTERY_GETPLAYEDS = ENDPOINT + "lottery/getplayeds";
    public static String URL_LOTTERY_GETNEWNUMBER = ENDPOINT + "number/getnewnumber";
    public static String URL_LOTTERY_CALCULATEBETNUM = ENDPOINT + "lottery/calculatebetnum";
    public static String URL_LOTTERY_BET = ENDPOINT + "lottery/bet";
    public static String URL_SYS_GETREGVERCODE = ENDPOINT + "sys/getRegVercode";
    public static String URL_USER_REG = ENDPOINT + "user/reg";
    public static String URL_FINANCE_GETBETORDERS = ENDPOINT + "finance/getbetorders";
    public static String URL_FINANCE_GETRECHARGES = ENDPOINT + "finance/getRecharges";
    public static String URL_FINANCE_GETWITHDRAWALS = ENDPOINT + "finance/getWithdrawals";
    public static String URL_LOTTERY_GETLOTTERYTYPEGROUPS = ENDPOINT + "lottery/getlotterytypegroups";
    public static String URL_LOTTERY_GETLOTTERYTYPE = ENDPOINT + "lottery/getlotterytypes";
    public static String URL_FINANCE_GETACRECORDS = ENDPOINT + "finance/getacrecords";
    public static String URL_SYS_GETBLAN = ENDPOINT + "sys/getblan";
    public static String URL_CHAT_GETTOKEN = ENDPOINT + "chat/getToken";
    public static String URL_CHAT_JOIN = ENDPOINT + "chat/join";
    public static String URL_FINANCE_GETNEWWINPURCHASE = ENDPOINT + "finance/getNewWinPurchase";
    public static String URL_USER_GETUSERINFO = ENDPOINT + "user/getuserinfo";
    public static String URL_MY_TEAM = ENDPOINT + "user/getteam";


    public static void initEndpoint() {
        URL_PAY_ALIPAY = ENDPOINT + "user/bankPay";
        URL_PAY = ENDPOINT + "finance/pay";
        URL_USER_LOGIN = ENDPOINT + "user/login";
        URL_USER_GETINFO = ENDPOINT + "user/getinfo";
        URL_USER_GETBANKS = ENDPOINT + "user/getbanks";
        URL_USER_UPDATEBANK = ENDPOINT + "user/updatebank";
        URL_USER_ADDBANK = ENDPOINT + "user/addbank";
        URL_USER_UPDATEPWD = ENDPOINT + "user/updatepwd";
        URL_USER_UPDATEPAYPWD = ENDPOINT + "user/updatepaypwd";
        URL_USER_UPDATE = ENDPOINT + "user/update";
        URL_USER_UPLOADUSERHEADIMG = ENDPOINT + "user/uploadUserHeadImg";

        URL_NUMBER_GETNEWNUMBERS = ENDPOINT + "number/getnewnumbers";
        URL_NUMBER_GETLOTTERYNUMBERS = ENDPOINT + "number/getlotterynumbers";
        URL_FINANCE_WITHDRAWALAPPLY = ENDPOINT + "finance/withdrawalapply";
        URL_SYS_GETSYSSET = ENDPOINT + "sys/getsysset";
        URL_LOTTERY_GETPTORDERS = ENDPOINT + "lottery/getptorders";
        URL_LOTTERY_GETSCHEMEDETAIL = ENDPOINT + "lottery/getschemedetail";
        URL_LOTTERY_BETPARTNERSHIP = ENDPOINT + "lottery/betPartnership";

        URL_LOTTERY_GETPERIODS = ENDPOINT + "lottery/getperiods";
        URL_LOTTERY_GETLOTTERYNUMBERPROP = ENDPOINT + "lottery/getlotterynumberprop";
        URL_LOTTERY_GETPLAYEDS = ENDPOINT + "lottery/getplayeds";
        URL_LOTTERY_GETNEWNUMBER = ENDPOINT + "number/getnewnumber";
        URL_LOTTERY_CALCULATEBETNUM = ENDPOINT + "lottery/calculatebetnum";
        URL_LOTTERY_BET = ENDPOINT + "lottery/bet";
        URL_SYS_GETREGVERCODE = ENDPOINT + "sys/getRegVercode";
        URL_USER_REG = ENDPOINT + "user/reg";
        URL_FINANCE_GETBETORDERS = ENDPOINT + "finance/getbetorders";
        URL_FINANCE_GETRECHARGES = ENDPOINT + "finance/getRecharges";
        URL_FINANCE_GETWITHDRAWALS = ENDPOINT + "finance/getWithdrawals";
        URL_LOTTERY_GETLOTTERYTYPEGROUPS = ENDPOINT + "lottery/getlotterytypegroups";
        URL_LOTTERY_GETLOTTERYTYPE = ENDPOINT + "lottery/getlotterytypes";
        URL_FINANCE_GETACRECORDS = ENDPOINT + "finance/getacrecords";
        URL_SYS_GETBLAN = ENDPOINT + "sys/getblan";
        URL_CHAT_GETTOKEN = ENDPOINT + "chat/getToken";
        URL_CHAT_JOIN = ENDPOINT + "chat/join";
        URL_FINANCE_GETNEWWINPURCHASE = ENDPOINT + "finance/getNewWinPurchase";
        URL_USER_GETUSERINFO = ENDPOINT + "user/getuserinfo";

        URL_MY_TEAM = ENDPOINT + "user/getteam";
    }
}
