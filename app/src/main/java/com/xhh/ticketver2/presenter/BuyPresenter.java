package com.xhh.ticketver2.presenter;

import android.content.Context;
import android.text.TextUtils;

import com.xhh.ticketver2.api.ApiH;
import com.xhh.ticketver2.beans.GameInfoEntry;
import com.xhh.ticketver2.beans.GameListEntry;
import com.xhh.ticketver2.interactor.CommInteractor;
import com.xhh.ticketver2.json.JSonParamUtil;
import com.xhh.ticketver2.listeners.BaseLoadedListener;
import com.xhh.ticketver2.ui.view.BuyView;
import com.xhh.ticketver2.utils.MRule;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:    hup
 * Date:      2017/3/24.
 * Description:
 */

public class BuyPresenter implements BaseLoadedListener<Object> {

    private BuyView mView;
    private Context mContext;
    private CommInteractor mInteractor;
    public String postGetPeriods = "postGetPeriods";
    public String postGetPeriodsMore = "postGetPeriodsMore";
    public String postGetLotterynumberProp = "postGetLotterynumberProp";
    public String postGetPlay = "postGetPlay";
    public String postGetNewNumber = "postGetNewNumber";
    public String postCalculatebetnum = "postCalculatebetnum";
    public String postLotteryBet = "postLotteryBet";
    public BuyPresenter(BuyView view, Context context){
        mContext = context;
        mView = view;
        mInteractor = new CommInteractor(this);
    }
    public void postGetPeriods(String lotterType,int num,boolean isMore){
        Map<String,String> params = new HashMap<>();
        params.put("lotterType",lotterType);
        params.put("num",num + "");
        String tag = postGetPeriods;
        if (isMore){
            mView.showLoadingDialog(ApiH.LOADING);
            tag = postGetPeriodsMore;
        }
        mInteractor.post(tag, ApiH.URL_LOTTERY_GETPERIODS,params);
    }
    public void postGetLotterynumberProp(String lotterType){
        Map<String,String> params = new HashMap<>();
        params.put("lotterType",lotterType);
        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postGetLotterynumberProp, ApiH.URL_LOTTERY_GETLOTTERYNUMBERPROP,params);
    }
    public void postGetPlay(String lotterType){
        Map<String,String> params = new HashMap<>();
        params.put("lotterType",lotterType);
//        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postGetPlay, ApiH.URL_LOTTERY_GETPLAYEDS,params);
    }

    public void postGetNewNumber(String lotteryType){
        Map<String,String> params = new HashMap<>();
        params.put("lotterType",lotteryType);
//        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postGetNewNumber, ApiH.URL_LOTTERY_GETNEWNUMBER,params);
    }
    public void postCalculatebetnum(String lotTypeId,String playedType,String betContent){
        Map<String,String> params = new HashMap<>();
        params.put("lotTypeId",lotTypeId);
        params.put("playedType",playedType);
        params.put("betContent",betContent);
        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postCalculatebetnum, ApiH.URL_LOTTERY_CALCULATEBETNUM,params);
    }
    public void postLotteryBet(String lotTypeId,boolean isIssue, String isBuyOrHeBuy, String betPeriods , String betMultiple, String bettingTotailAmount,String[] buyNumAndBuyHeNum,
                               List<GameListEntry> mList, List<GameInfoEntry> issueList,boolean mIsAllBuy,boolean mIsStopIssue){
        Map<String,String> params = getLotterBetParams(lotTypeId,isIssue,isBuyOrHeBuy,betPeriods,betMultiple,bettingTotailAmount,buyNumAndBuyHeNum,mList,issueList,mIsAllBuy,mIsStopIssue);
        mView.showLoadingDialog(ApiH.LOADING);
        mInteractor.post(postLotteryBet, ApiH.URL_LOTTERY_BET,params);
    }
    private Map getLotterBetParams(String lotTypeId,boolean isIssue, String isBuyOrHeBuy, String betPeriods ,String betMultiple, String bettingTotailAmount,String[] buyNumAndBuyHeNum,
                                   List<GameListEntry> mList,List<GameInfoEntry> issueList,boolean mIsAllBuy,boolean mIsStopIssue){
        Map<String,String> params = new HashMap<>();
        try{
            params.put("platform","3");
            JSONArray jaIssue = new JSONArray();
            if (isIssue && issueList != null){
                for(int i =0;i<issueList.size();i++){
                    JSONObject joTradeIssue = new JSONObject();
                    GameInfoEntry issueEn = issueList.get(i);
                    if (issueEn.multipleInput == 0){
                        continue;
                    }
                    joTradeIssue.put("period",issueEn.issue);
                    joTradeIssue.put("betMultiple",issueEn.multipleInput);
                    jaIssue.put(joTradeIssue);
                }
                params.put("chaseInfos",jaIssue.toString());

//                JSONObject joTradeIsu = new JSONObject();
//                joTradeIsu.put("endType","1");//2中奖后结束,1:一直追完
//                params.put("chasePeriods",joTradeIsu.toString());
            }


//            JSONObject joTradeIssue = new JSONObject();
//            joTradeIssue.put("betNum","1");
//            params.put("buinfo",joTradeIssue.toString());

            JSONObject joTrade = new JSONObject();
            joTrade.put("lotteryTypeId",lotTypeId);
            joTrade.put("betPeriod",betPeriods);
            joTrade.put("schemeType",isBuyOrHeBuy);//,//1独买，2合买
            if (isIssue){
                joTrade.put("isChase","1"); //是否追期 1,0
            }else{
                joTrade.put("isChase","0"); //是否追期 1,0
            }
            joTrade.put("betMultiple",betMultiple);
            if ("1".equals(isBuyOrHeBuy)){

            }else{
                joTrade.put("isAllBuy","0"); //:1,//是否全额保底
                if (TextUtils.isEmpty(buyNumAndBuyHeNum[0])){
                    joTrade.put("purchasedNum","0");
                }else{
                    joTrade.put("purchasedNum",buyNumAndBuyHeNum[0]);
                }
                if (TextUtils.isEmpty(buyNumAndBuyHeNum[1])){
                    joTrade.put("minNum","0");
                }else{
                    joTrade.put("minNum",buyNumAndBuyHeNum[1]);
                }


            }
            joTrade.put("winningAmount",bettingTotailAmount);
            joTrade.put("secrecyType","1");
            joTrade.put("schemeTitle","schemeTitle");
            joTrade.put("schemeDescribe","schemeDescribe");
            joTrade.put("endType",mIsStopIssue == true ? "2":"1");
            joTrade.put("isAllBuy",mIsAllBuy == true ? "1":"0");
            params.put("betscheme",joTrade.toString());

            JSONArray jaDetail = MRule.getRowsChoiceNewJSon(mList);
            params.put("betschemeInfos",jaDetail.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return params;
    }
    @Override
    public void onSuccess(String event_tag, Object data) {
        if (postGetPeriods.equals(event_tag)){
            mView.showGetPeriodsSuccess(JSonParamUtil.paraPlayGameInfo(data.toString()));
        }else if (postGetPeriodsMore.equals(event_tag)){
            mView.hideLoadingDialog();
            mView.showGetPeriodsMoreSuccess(JSonParamUtil.paraPlayGameInfo(data.toString()));
        }else if (postGetLotterynumberProp.equals(event_tag)){
            mView.showGetNumberPropSuccess(JSonParamUtil.paramCommForOri(data.toString()));
        }else if (postGetPlay.equals(event_tag)){
            mView.hideLoadingDialog();
            mView.showGetPlaySuccess(JSonParamUtil.paraGameList(data.toString()));
        }else if (postGetNewNumber.equals(event_tag)){
            mView.showGetNewNumberSuccess(JSonParamUtil.paramGetNewNumber(data.toString()));
        }else if (postCalculatebetnum.equals(event_tag)){
            mView.hideLoadingDialog();
            mView.showCalculateBetNumSuccess(JSonParamUtil.paramCalculateBetNum(data.toString()));
        }else if (postLotteryBet.equals(event_tag)){
            mView.hideLoadingDialog();
            mView.showBetSuccess(JSonParamUtil.paramComm(data.toString()));
        }
    }

    @Override
    public void onException(String msg) {
        mView.hideLoadingDialog();
        mView.showToast(ApiH.NETWORK_ERROR);
    }
}
