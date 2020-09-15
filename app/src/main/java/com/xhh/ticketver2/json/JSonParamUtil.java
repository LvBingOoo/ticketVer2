package com.xhh.ticketver2.json;

import com.xhh.ticketver2.beans.AwardEntry;
import com.xhh.ticketver2.beans.BankEntry;
import com.xhh.ticketver2.beans.BannaerEntry;
import com.xhh.ticketver2.beans.BillEntry;
import com.xhh.ticketver2.beans.BuyHeEntry;
import com.xhh.ticketver2.beans.ChongTiEntry;
import com.xhh.ticketver2.beans.CommEntry;
import com.xhh.ticketver2.beans.Const;
import com.xhh.ticketver2.beans.GameInfoEntry;
import com.xhh.ticketver2.beans.GameListEntry;
import com.xhh.ticketver2.beans.HeBuyDoEntry;
import com.xhh.ticketver2.beans.HeBuyUserEntry;
import com.xhh.ticketver2.beans.HomeBuyEntry;
import com.xhh.ticketver2.beans.LoginEntry;
import com.xhh.ticketver2.beans.NumEntry;
import com.xhh.ticketver2.beans.RongEntry;
import com.xhh.ticketver2.beans.SchemeEntry;
import com.xhh.ticketver2.beans.SchemeInfoEntry;
import com.xhh.ticketver2.beans.ShiShiCaiEntry;
import com.xhh.ticketver2.beans.ShiShiCaiRuleEntry;
import com.xhh.ticketver2.beans.SysEntry;
import com.xhh.ticketver2.beans.TouZhuEntry;
import com.xhh.ticketver2.beans.UserInfoEntry;
import com.xhh.ticketver2.ui.TicketApplication;
import com.xhh.ticketver2.ui.view.CalculateEntry;
import com.xhh.ticketver2.ui.view.WinEntry;
import com.xhh.ticketver2.utils.FileUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:    hup
 * Date:      2017/10/20.
 * Description:
 */

public class JSonParamUtil {
    public static CommEntry paramComm(String json) {
        CommEntry commEntry = new CommEntry();
        try {
            JSONObject jsonObject = new JSONObject(json);
            commEntry.status = jsonObject.getInt("status");
            commEntry.msg = jsonObject.getString("msg");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commEntry;
    }

    public static CommEntry paramCommForOri(String json) {
        CommEntry commEntry = new CommEntry();
        try {
            JSONObject jsonObject = new JSONObject(json);
            commEntry.status = jsonObject.getInt("status");
            commEntry.msg = jsonObject.getString("msg");
            commEntry.json = json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commEntry;
    }

    public static CommEntry paramCommForValue(String json) {
        CommEntry commEntry = new CommEntry();
        try {
            JSONObject jsonObject = new JSONObject(json);
            commEntry.status = jsonObject.getInt("status");
            commEntry.msg = jsonObject.getString("msg");
            commEntry.json = jsonObject.getString("obj");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commEntry;
    }

    public static CommEntry paramCommForDomain(String json) {
        CommEntry commEntry = new CommEntry();
        try {
            JSONObject jsonObject = new JSONObject(json);
            commEntry.status = jsonObject.getInt("Status");
            commEntry.json = jsonObject.getString("Domain");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return commEntry;
    }

    public static LoginEntry paramLogin(String json) {
        LoginEntry entry = new LoginEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);
            JSONObject obj = jsonObject.getJSONObject("obj");
            entry.groupId = obj.getString("groupId");
            String token = obj.getString("token");
            JSONObject userinfo = obj.getJSONObject("userinfo");
            if (userinfo.has("nickName")) {
                entry.nickName = userinfo.getString("nickName");
            } else {
                entry.nickName = userinfo.getString("userName");
            }
            if (userinfo.has("headPortrait")) {
                entry.headPortrait = userinfo.getString("headPortrait");
            }
            if (userinfo.has("userId")) {
                entry.userId = userinfo.getString("userId");
            } else {
                entry.userId = userinfo.getString("");
            }
            if (userinfo.has("parentId")) {
                entry.parentId = userinfo.getString("parentId");
            } else {
                entry.parentId = userinfo.getString("");
            }
            if (userinfo.has("userType")) {
                entry.userType = userinfo.getString("userType");
            } else {
                entry.userType = userinfo.getString("");
            }
            TicketApplication.TOKEN = token;
            FileUtil.saveString(TicketApplication.getAppContext(), FileUtil.TOKEN, token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public static RongEntry paramRongToken(String json) {
        RongEntry entry = new RongEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);
            JSONObject obj = jsonObject.getJSONObject("obj");
            entry.token = obj.getString("token");
            entry.groupId = obj.getString("groupId");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public static UserInfoEntry paramGetInfo(String json) {
        UserInfoEntry entry = new UserInfoEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);
            JSONObject obj = jsonObject.getJSONObject("obj");
            if (obj.has("nickName")) {
                entry.nickName = obj.getString("nickName");
            } else {
                entry.nickName = obj.getString("userName");
            }
            if (obj.has("headPortrait")) {
                entry.headPortrait = obj.getString("headPortrait");
            }
            if (obj.has("qq")) {
                entry.qq = obj.getString("qq");
            }
            if (obj.has("userName")) {
                entry.userName = obj.getString("userName");
            }
            if (obj.has("betTask")) {
                entry.betTask = obj.getString("betTask");
            }
            if (obj.has("totalBetTask")) {
                entry.totalBetTask = obj.getString("totalBetTask");
            }
            if (obj.has("userPropStr")) {
                entry.userPropStr = obj.getString("userPropStr");
            }
            if (obj.has("availableFund")) {
                entry.availableFund = obj.getString("availableFund");
            }
            if (obj.has("safetyPassword")) {
                entry.safetyPassword = obj.getString("safetyPassword");
            }
            if (obj.has("userGrade")) {
                entry.userGrade = obj.getString("userGrade");
            }
            if (obj.has("userId")) {
                entry.userId = obj.getString("userId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public static SysEntry paramSys(String json) {
        SysEntry entry = new SysEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);
            JSONObject obj = jsonObject.getJSONObject("obj");
            if (obj.has("setContent")) {
                entry.setContent = obj.getString("setContent");
            }
            if (obj.has("setId")) {
                entry.setId = obj.getString("setId");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public static BankEntry paramGetBank(String json) {
        BankEntry entry = new BankEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray obj = jsonObject.getJSONArray("obj");
            List<BankEntry> list = new ArrayList<>();
            for (int i = 0; i < obj.length(); i++) {
                BankEntry entry1 = new BankEntry();
                JSONObject jo = obj.getJSONObject(i);
                entry1.bankcarId = jo.getString("bankcarId");
                entry1.accountName = jo.getString("accountName");
                entry1.bankName = jo.getString("bankName");
                entry1.bankNumber = jo.getString("bankNumber");
                entry1.bankArea = jo.getString("bankArea");
                list.add(entry1);
            }
            entry.mList = list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public static AwardEntry paramGetNewNumbers(String json) {
        AwardEntry entry = new AwardEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray obj = jsonObject.getJSONArray("obj");
            List<AwardEntry> list = new ArrayList<>();
            for (int i = 0; i < obj.length(); i++) {
                AwardEntry entry1 = new AwardEntry();
                JSONObject jo = obj.getJSONObject(i);
                entry1.numberId = jo.getString("numberId");
                entry1.period = jo.getString("period");
                entry1.lotteryTypeId = jo.getString("lotteryTypeId");
                entry1.lotteryTypeName = jo.getString("lotteryTypeName");
                entry1.dwawNumber = jo.getString("dwawNumber");
                entry1.status = jo.getString("status");
                entry1.openTimeStr = jo.getString("openTimeStr");
                list.add(entry1);
            }
            entry.mList = list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public static AwardEntry paramGetNewNumber(String json) {
        AwardEntry entry = new AwardEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jo = jsonObject.getJSONObject("obj");
            entry.numberId = jo.getString("numberId");
            entry.period = jo.getString("period");
            entry.lotteryTypeId = jo.getString("lotteryTypeId");
            entry.lotteryTypeName = jo.getString("lotteryTypeName");
            entry.dwawNumber = jo.getString("dwawNumber");
            entry.status = jo.getString("status");
            entry.openTimeStr = jo.getString("openTimeStr");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public static CalculateEntry paramCalculateBetNum(String json) {
        CalculateEntry entry = new CalculateEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);
            JSONObject jo = jsonObject.getJSONObject("obj");
            entry.betMoney = jo.getInt("betMoney");
            entry.betNum = jo.getInt("betNum");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public static AwardEntry paramLotteryNumbers(String json) {
        AwardEntry entry = new AwardEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);

            JSONObject obj = jsonObject.getJSONObject("obj");
            JSONArray rows = obj.getJSONArray("rows");

            List<AwardEntry> list = new ArrayList<>();
            for (int i = 0; i < rows.length(); i++) {
                AwardEntry entry1 = new AwardEntry();
                JSONObject jo = rows.getJSONObject(i);
                entry1.numberId = jo.getString("numberId");
                entry1.period = jo.getString("period");
                entry1.lotteryTypeId = jo.getString("lotteryTypeId");
                entry1.lotteryTypeName = jo.getString("lotteryTypeName");
                if (jo.has("dwawNumber")) {
                    entry1.dwawNumber = jo.getString("dwawNumber");
                }
                entry1.status = jo.getString("status");
                entry1.openTimeStr = jo.getString("openTimeStr");
                list.add(entry1);
            }
            entry.mList = list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public static BuyHeEntry paramBuyHeList(String json) {
        BuyHeEntry entry = new BuyHeEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);

            JSONObject obj = jsonObject.getJSONObject("obj");
            JSONArray rows = obj.getJSONArray("rows");

            List<BuyHeEntry> list = new ArrayList<>();
            for (int i = 0; i < rows.length(); i++) {
                BuyHeEntry entry1 = new BuyHeEntry();
                JSONObject jo = rows.getJSONObject(i);
                entry1.lotteryTypeName = jo.getString("lotteryTypeName");
                if (jo.has("headPortrait")) {
                    entry1.headPortrait = jo.getString("headPortrait");
                }
                entry1.userName = jo.getString("userName");
                if (jo.has("bettingTotailAmount")) {
                    entry1.bettingTotailAmount = jo.getString("bettingTotailAmount");
                }
                entry1.unitPrice = jo.getString("unitPrice");
                if (jo.has("completeAmount")) {
                    entry1.completeAmount = jo.getString("completeAmount");
                }
                entry1.purchasedNum = jo.getInt("purchasedNum");
                entry1.totalNum = jo.getInt("totalNum");
                if (jo.has("actualMinNum")) {
                    entry1.actualMinNum = jo.getInt("actualMinNum");
                }
                entry1.schemeId = jo.getString("schemeId");
                if (jo.has("chaseNum")) {
                    entry1.chaseNum = jo.getString("chaseNum");
                }
                if (jo.has("status")) {
                    entry1.status = jo.getString("status");
                }
                if (jo.has("statusStr")) {
                    entry1.statusStr = jo.getString("statusStr");
                }
                entry1.userGrade = jo.getString("userGrade");
                entry1.betPeriod = jo.getString("betPeriod");
                list.add(entry1);
            }
            entry.mList = list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public static SchemeEntry paraHeBuyDetail(String json) {
        SchemeEntry enM = new SchemeEntry();
        List<SchemeInfoEntry> mlist = new ArrayList<SchemeInfoEntry>();
        try {
            enM.commEn = paramComm(json);
            JSONObject jo = new JSONObject(json);
            JSONObject data = jo.getJSONObject("obj");

            enM.lotteryTypeName = data.getString("lotteryTypeName");
            if (data.has("headPortrait")) {
                enM.headPortrait = data.getString("headPortrait");
            }
            if (data.has("userGrade")) {
                enM.userGrade = data.getString("userGrade");
            }
            enM.userName = data.getString("userName");

            enM.bettingTotailAmount = data.getString("bettingTotailAmount");
            enM.totalNum = data.getInt("totalNum");
            enM.purchasedNum = data.getInt("purchasedNum");
            if (data.has("actualMinNum")) {
                enM.actualMinNum = data.getInt("actualMinNum");
            }
            enM.unitPrice = data.getString("unitPrice");
            if (data.has("completeAmount")) {
                enM.completeAmount = data.getString("completeAmount");
            }
            enM.schemeId = data.getString("schemeId");
            enM.isChase = data.getString("isChase");
            enM.schemeType = data.getString("schemeType");
            if (data.has("chaseNum")) {
                enM.chaseNum = data.getString("chaseNum");
            }
            enM.betPeriod = data.getString("betPeriod");
            enM.betMultiple = data.getString("betMultiple");
            enM.winningTotailAmount = data.getString("winningTotailAmount");
            enM.status = data.getString("status");
            enM.openNumber = data.getString("openNumber");
            enM.modMoney = data.getString("modMoney");
            if (data.has("minNum")) {
                enM.minNum = data.getString("minNum");
            }

            // 用户
            JSONArray rs_trade_user = data.getJSONArray("betUserinfos");
            List<HeBuyUserEntry> mUserList = new ArrayList<HeBuyUserEntry>();
            for (int i = 0; i < rs_trade_user.length(); i++) {
                HeBuyUserEntry userEn = new HeBuyUserEntry();
                JSONObject joUser = rs_trade_user.getJSONObject(i);
                userEn.userName = joUser.getString("userName");
                userEn.betNum = joUser.getInt("betNum");
                userEn.totalNum = data.getInt("totalNum");
                userEn.betMoney = joUser.getString("betMoney");
                userEn.winningAmount = joUser.getString("winningAmount");
                mUserList.add(userEn);
            }
            enM.mUserList = mUserList;
            // 方案内容
            JSONArray listJa = data.getJSONArray("betSchemeInfos");
            for (int i = 0; i < listJa.length(); i++) {
                JSONObject joo = listJa.getJSONObject(i);
                SchemeInfoEntry en = new SchemeInfoEntry();
                en.betInfoId = joo.getString("betInfoId");
                en.playedName = joo.getString("playedName");
                en.betContent = joo.getString("betContent");
                mlist.add(en);
            }
            enM.mListSchemeInfo = mlist;
            // 期号列表
            List<HeBuyDoEntry> mDoList = new ArrayList<HeBuyDoEntry>();
            if (data.has("betChasePeriodInfos")) {
                JSONArray listIssueJa = data.getJSONArray("betChasePeriodInfos");
                for (int is = 0; is < listIssueJa.length(); is++) {
                    HeBuyDoEntry doEn = new HeBuyDoEntry();
                    JSONObject joIssue = listIssueJa.getJSONObject(is);
                    if (joIssue.has("cpiId")) {
                        doEn.cpiId = joIssue.getString("cpiId");
                    }
                    doEn.periods = joIssue.getString("period");
                    doEn.betMultiple = joIssue.getString("betMultiple");
                    doEn.betMoney = joIssue.getString("betMoney");
                    doEn.winningAmount = joIssue.getString("winningAmount");
                    doEn.status = joIssue.getString("status");
                    if (joIssue.has("openNumber")) {
                        doEn.openNumber = joIssue.getString("openNumber");
                    }
                    mDoList.add(doEn);
                }
            } else {
                HeBuyDoEntry doEn = new HeBuyDoEntry();
//                doEn.id = id;
                doEn.periods = enM.betPeriod;
                doEn.betMultiple = enM.betMultiple;
                doEn.betMoney = enM.modMoney;
                doEn.winningAmount = enM.winningTotailAmount;
                doEn.status = enM.status;
                doEn.openNumber = enM.openNumber;
                mDoList.add(doEn);
            }

            enM.mIssueList = mDoList;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return enM;
    }

    public static GameInfoEntry paraPlayGameInfo(String json) {
        GameInfoEntry enM = new GameInfoEntry();
        try {
            enM.commEn = paramComm(json);
            JSONObject jo = new JSONObject(json);
            JSONArray data = jo.getJSONArray("obj");
            List<GameInfoEntry> list = new ArrayList<>();
            for (int i = 0; i < data.length(); i++) {
                GameInfoEntry entry = new GameInfoEntry();
                JSONObject joo = data.getJSONObject(i);
                entry.issue = joo.getString("period");
                entry.end_time = joo.getString("openTimeStr");
                list.add(entry);
            }
            enM.mList = list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enM;
    }

    public static GameListEntry paraGameList(String json) {
        GameListEntry enM = new GameListEntry();
        List<GameListEntry> mlist = new ArrayList<GameListEntry>();
        try {
            enM.commEn = paramComm(json);
            JSONObject jo = new JSONObject(json);
            JSONArray data = jo.getJSONArray("obj");
            for (int i = 0; i < data.length(); i++) {
                JSONObject joo = data.getJSONObject(i);
                GameListEntry en = new GameListEntry();
                en.playedType = joo.getString("playedType");
                en.playedId = joo.getString("playedId");
                en.fullName = joo.getString("fullName");
                en.lotteryTypeName = joo.getString("lotteryTypeName");
                en.simpleDescribe = joo.getString("simpleDescribe");
                JSONArray playedRules = joo.getJSONArray("playedRules");
                List<ShiShiCaiEntry> ruleEntry = paraGameInfoQiu(playedRules, joo.getString("simpleDescribe"), joo.getString("playedId"));
                en.ruleEntry = ruleEntry;
                mlist.add(en);

            }
            enM.mlist = mlist;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return enM;
    }

    public static List<ShiShiCaiEntry> paraGameInfoQiu(JSONArray num_show, String tip, String playedId) {
        List<ShiShiCaiEntry> mlist = new ArrayList<ShiShiCaiEntry>();
        try {
            String tips = tip;
            for (int i = 0; i < num_show.length(); i++) {
                JSONObject showJo = num_show.getJSONObject(i);
                String position = playedId;// 维度标示
                String name = showJo.getString("itemName");// 维度名
                String selectTools = null;
                if (showJo.has("selectTools")) {
                    selectTools = showJo.getString("selectTools");// 维度名
                    selectTools = selectTools.replaceFirst("all", "全");
                    selectTools = selectTools.replaceFirst("big", "大");
                    selectTools = selectTools.replaceFirst("small", "小");
                    selectTools = selectTools.replaceFirst("odd", "单");
                    selectTools = selectTools.replaceFirst("even", "双");
                    selectTools = selectTools.replaceFirst("clear", "清");
                }
                String nums_attr[] = showJo.getString("numbers").split(",");
                List<NumEntry> numList = new ArrayList<NumEntry>();
                for (int j = 0; j < nums_attr.length; j++) {
                    String num = nums_attr[j];
                    NumEntry nuEn = new NumEntry();
                    nuEn.num = num;
                    numList.add(nuEn);
                }
                // 规则添加
                int max_nums = showJo.getInt("selectMaxNum");
                int min_nums = showJo.getInt("selectMinNum");
                ShiShiCaiRuleEntry ruleEn = new ShiShiCaiRuleEntry();
                ruleEn.max_nums = max_nums;
                ruleEn.min_nums = min_nums;
                ruleEn.omitCode = showJo.getString("omitCode");

                String mutex = showJo.getString("isMutex");
                if ("1".equals(mutex)) {
                    ruleEn.is_reject = ShiShiCaiRuleEntry.IS_REJECT_TRUE;
                }

                ShiShiCaiEntry sscEn = new ShiShiCaiEntry();
                sscEn.nums = numList;
                sscEn.rule = ruleEn;
                sscEn.description = tips;
                sscEn.position = position;
                sscEn.name = name;
                sscEn.selectTools = selectTools;
                mlist.add(sscEn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mlist;
    }

    public static TouZhuEntry paramGetBetOrders(String json) {
        TouZhuEntry entry = new TouZhuEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);

            JSONObject obj = jsonObject.getJSONObject("obj");
            JSONArray rows = obj.getJSONArray("rows");

            List<TouZhuEntry> list = new ArrayList<>();
            for (int i = 0; i < rows.length(); i++) {
                TouZhuEntry entry1 = new TouZhuEntry();
                JSONObject jo = rows.getJSONObject(i);
                entry1.schemeId = jo.getString("schemeId");
                entry1.schemeType = jo.getString("schemeType");
                entry1.isChase = jo.getString("isChase");
                if (jo.has("chaseNum")) {
                    entry1.chaseNum = jo.getString("chaseNum");
                }
                entry1.lotteryTypeId = jo.getString("lotteryTypeId");
                entry1.lotteryTypeName = jo.getString("lotteryTypeName");
                entry1.betPeriod = jo.getString("betPeriod");
                entry1.status = jo.getString("status");
                entry1.statusStr = jo.getString("statusStr");
                entry1.betTime = jo.getString("betTime");
                entry1.betMoney = jo.getString("betMoney");
                entry1.winningAmount = jo.getString("winningAmount");
                list.add(entry1);
            }
            entry.mList = list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public static ChongTiEntry paramGetRecharges(String json) {
        ChongTiEntry entry = new ChongTiEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);

            JSONObject obj = jsonObject.getJSONObject("obj");
            JSONArray rows = obj.getJSONArray("rows");

            List<ChongTiEntry> list = new ArrayList<>();
            for (int i = 0; i < rows.length(); i++) {
                ChongTiEntry entry1 = new ChongTiEntry();
                JSONObject jo = rows.getJSONObject(i);
                entry1.rechargeAmount = jo.getString("rechargeAmount");
                entry1.rechargeChannelStr = jo.getString("rechargeChannelStr");
                entry1.createTimeStr = jo.getString("createTime");
                entry1.statusStr = jo.getString("statusStr");
                list.add(entry1);
            }
            entry.mList = list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public static ChongTiEntry paramGetTiXian(String json) {
        ChongTiEntry entry = new ChongTiEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);

            JSONObject obj = jsonObject.getJSONObject("obj");
            JSONArray rows = obj.getJSONArray("rows");

            List<ChongTiEntry> list = new ArrayList<>();
            for (int i = 0; i < rows.length(); i++) {
                ChongTiEntry entry1 = new ChongTiEntry();
                JSONObject jo = rows.getJSONObject(i);
                entry1.rechargeAmount = jo.getString("withdrawalAmount");
                entry1.rechargeChannelStr = "提现";
                entry1.createTimeStr = jo.getString("createTime");
                entry1.statusStr = jo.getString("statusStr");
                list.add(entry1);
            }
            entry.mList = list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public static BillEntry paramGetBill(String json) {
        BillEntry entry = new BillEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);

            JSONObject obj = jsonObject.getJSONObject("obj");
            JSONArray rows = obj.getJSONArray("rows");

            List<BillEntry> list = new ArrayList<>();
            for (int i = 0; i < rows.length(); i++) {
                BillEntry entry1 = new BillEntry();
                JSONObject jo = rows.getJSONObject(i);
                entry1.acTypeStr = jo.getString("acTypeStr");
                entry1.changeAmount = jo.getString("changeAmount");
                entry1.createTime = jo.getString("createTime");
                entry1.beforeAvailableFund = jo.getString("beforeAvailableFund");
                entry1.afterAvailableFund = jo.getString("afterAvailableFund");
                list.add(entry1);
            }
            entry.mList = list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public static HomeBuyEntry paramGetLotteryType(String json) {
        HomeBuyEntry entry = new HomeBuyEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);

            JSONArray obj = jsonObject.getJSONArray("obj");

            List<HomeBuyEntry> list = new ArrayList<>();
            for (int i = 0; i < obj.length(); i++) {
                HomeBuyEntry entry1 = new HomeBuyEntry();
                JSONObject jo = obj.getJSONObject(i);
                entry1.lotteryTypeName = jo.getString("lotteryTypeName");
                entry1.describes = jo.getString("describes");
                entry1.lotteryTypeId = jo.getString("lotteryTypeId");
                entry1.icon = jo.getString("icon");
                list.add(entry1);
                if (i == 3) {
                    break;
                }
            }
            entry.mList = list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        entry.type = Const.TYPE_01;
        return entry;
    }

    public static HomeBuyEntry paramGetLotteryTypeGroup(String json) {
        HomeBuyEntry entry = new HomeBuyEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);
            JSONArray obj = jsonObject.getJSONArray("obj");
            List<HomeBuyEntry> listWai = new ArrayList<>();
            for (int i = 0; i < obj.length(); i++) {
                JSONObject jo = obj.getJSONObject(i);
                HomeBuyEntry entryF = new HomeBuyEntry();
                List<HomeBuyEntry> list = new ArrayList<>();
                entryF.nameGroup = jo.getString("name");
                JSONArray lotteryTypesJa = jo.getJSONArray("lotteryTypes");
                for (int j = 0; j < lotteryTypesJa.length(); j++) {
                    JSONObject joo = lotteryTypesJa.getJSONObject(j);
                    HomeBuyEntry entry1 = new HomeBuyEntry();
                    entry1.lotteryTypeName = joo.getString("lotteryTypeName");
                    entry1.describes = joo.getString("describes");
                    entry1.lotteryTypeId = joo.getString("lotteryTypeId");
                    entry1.icon = joo.getString("icon");
                    list.add(entry1);
                }
                entryF.mMeiList = list;
                entryF.type = Const.TYPE_02;
                listWai.add(entryF);
            }
            entry.mList = listWai;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public static BannaerEntry paramGetBannaer(String json) {
        BannaerEntry entry = new BannaerEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);

            JSONObject obj = jsonObject.getJSONObject("obj");
            JSONArray rows = obj.getJSONArray("rows");

            List<BannaerEntry> list = new ArrayList<>();
            for (int i = 0; i < rows.length(); i++) {
                BannaerEntry entry1 = new BannaerEntry();
                JSONObject jo = rows.getJSONObject(i);
                entry1.imgUrl = jo.getString("imgUrl");
                entry1.targetUrl = jo.getString("targetUrl");
                list.add(entry1);
            }
            entry.mList = list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }

    public static WinEntry paramGetNewWin(String json) {
        WinEntry entry = new WinEntry();
        try {
            entry.commEntry = paramComm(json);
            JSONObject jsonObject = new JSONObject(json);

            JSONArray rows = jsonObject.getJSONArray("obj");

            List<WinEntry> list = new ArrayList<>();
            for (int i = 0; i < rows.length(); i++) {
                WinEntry entry1 = new WinEntry();
                JSONObject jo = rows.getJSONObject(i);
                entry1.schemeId = jo.getString("schemeId");
                entry1.lotteryTypeId = jo.getString("lotteryTypeId");
                entry1.lotteryTypeName = jo.getString("lotteryTypeName");
                entry1.betPeriod = jo.getString("betPeriod");
                entry1.winningTotailAmount = jo.getString("winningTotailAmount");
                entry1.openTime = jo.getString("openTime");
                if (jo.has("userName")) {
                    entry1.userName = jo.getString("userName");
                }
                list.add(entry1);
            }
            entry.mList = list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entry;
    }
}
