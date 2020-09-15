package com.xhh.ticketver2.utils;


import android.text.TextUtils;

import com.xhh.ticketver2.beans.GameListEntry;
import com.xhh.ticketver2.beans.NumEntry;
import com.xhh.ticketver2.beans.ShiShiCaiEntry;
import com.xhh.ticketver2.beans.ShiShiCaiRuleEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MRule {

	/* 判断是否已经选择了正确的注数 */
	public static synchronized String[] isRightMinChoice(List<ShiShiCaiEntry> list) {
		String[] ret = new String[2];
		ret[0] = "0";// 正常
		for (int i = 0; i < list.size(); i++) {
			int count = 0;
			ShiShiCaiEntry en = list.get(i);
			for (int j = 0; j < en.nums.size(); j++) {
				NumEntry numEn = en.nums.get(j);
				if (numEn.isSelect) {
					count++;
				}
			}
			if (count < en.rule.min_nums) {
				ret[0] = "1";// 提示
				ret[1] = en.name + "至少选择" + en.rule.min_nums + "个";
				return ret;
			}else if (count > en.rule.max_nums){
				ret[0] = "1";// 提示
				ret[1] = en.name + "最多选择" + en.rule.max_nums + "个";
				return ret;
			}
		}
		return ret;
	}
	public static synchronized String getRowsChoiceJSonNewCount(List<ShiShiCaiEntry> mList) {
		StringBuffer upStr = new StringBuffer();
		try {
			for (int mm = 0; mm < mList.size(); mm++) {
				ShiShiCaiEntry sscEn = mList.get(mm);
				// 当前维度UI显示数据
				StringBuffer sbShow = new StringBuffer();
				sbShow.append(sscEn.name).append("：");
				// 当前维度上报数据
				StringBuffer tempStr = new StringBuffer();
				for (int i = 0; i < sscEn.nums.size(); i++) {
					boolean isSelect = sscEn.nums.get(i).isSelect;
					if (isSelect) {
						tempStr.append(sscEn.nums.get(i).num).append(",");
						sbShow.append(sscEn.nums.get(i).num).append(" ");
					}
				}
				if (tempStr.length()>1){
					tempStr = tempStr.deleteCharAt(tempStr.length()-1);
				}
				upStr.append(tempStr).append("|");
				// 详情页面显示的 维度+号码拼接
				sscEn.showDetailNum = sbShow.toString();
			}
			if (!TextUtils.isEmpty(upStr)){
				upStr = upStr.deleteCharAt(upStr.length() - 1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return upStr.toString();
	}

	public static synchronized JSONArray getRowsChoiceNewJSon(List<GameListEntry> mList) {

		JSONArray reJso = new JSONArray();
		try {
			for (int nn = 0; nn < mList.size(); nn++) {
				// 当前玩法所有维度
//				JSONArray jaNums = new JSONArray();
				StringBuffer numsStr = new StringBuffer();
				JSONObject playType = new JSONObject();
				// 固定为第一个维度 赋值
				List<ShiShiCaiEntry> sscEnList = mList.get(nn).ruleEntry;
				for (int mm = 0; mm < sscEnList.size(); mm++) {
					JSONObject numsJso = new JSONObject();

					ShiShiCaiEntry sscEn = sscEnList.get(mm);
					// 当前维度UI显示数据
					StringBuffer sbShow = new StringBuffer();
					sbShow.append(sscEn.name).append("：");
					// 当前维度上报数据
//					JSONArray ja = new JSONArray();
					StringBuffer numsOne = new StringBuffer();
					for (int i = 0; i < sscEn.nums.size(); i++) {
						boolean isSelect = sscEn.nums.get(i).isSelect;
						if (isSelect) {
//							ja.put(sscEn.nums.get(i).num);
							numsOne.append(sscEn.nums.get(i).num).append(",");
							sbShow.append(sscEn.nums.get(i).num).append(" ");
						}
					}
					if (numsOne.length()>1){
						numsOne = numsOne.deleteCharAt(numsOne.length()-1);
					}
//					numsJso.put(sscEn.position, ja);
//					jaNums.put(ja);
					numsStr.append(numsOne).append("|");
					// 详情页面显示的 维度+号码拼接
					sscEn.showDetailNum = sbShow.toString();

				}
				if (numsStr.length()>1){
					numsStr =  numsStr.deleteCharAt(numsStr.length()-1);
				}
				playType.put("nameGroup",mList.get(nn).fullName);
				playType.put("betContent", numsStr);
				playType.put("playedType", mList.get(nn).playedType);
				playType.put("playedId",mList.get(nn).playedId);
				playType.put("totalNum",mList.get(nn).showBetNum);
				playType.put("betMoney",mList.get(nn).showBetMoney);

				reJso.put(playType);

			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return reJso;
	}

    public static synchronized boolean isRightRowsChoiceMax(ShiShiCaiEntry sscEn) {
        boolean is = true;
        int chioce = 0;
        for (int i = 0; i < sscEn.nums.size(); i++) {
            boolean isSelect = sscEn.nums.get(i).isSelect;
            if (isSelect) {
                chioce++;
            }
        }
        if (sscEn.rule.max_nums < (chioce + 1)) {
            is = false;
        }
        return is;
    }
    public static void cancleLastCheck(List<ShiShiCaiEntry> list,int tag_num_tv_i,int tag_num_tv_j) {
        if (list.get(tag_num_tv_i).rule != null
                && ShiShiCaiRuleEntry.IS_REJECT_TRUE.equals(list.get(tag_num_tv_i).rule.is_reject)) {
            String numShow = list.get(tag_num_tv_i).nums.get(tag_num_tv_j).num;
            for (int i = 0; i < list.size(); i++) {
                //除去最后一次选中的维度
                if (i != tag_num_tv_i) {
                    ShiShiCaiEntry en = list.get(i);
                    for (int j = 0; j < en.nums.size(); j++) {
                        NumEntry numEn = en.nums.get(j);
                        if (numShow.equals(numEn.num)) {
                            // 取消显示刷新界面
                            numEn.isSelect = false;
                        }
                    }
                }
            }
        }
    }
    public static int[] calculateBetMoneyAndNum(List<GameListEntry> listEntries){
	    int[] arr = new int[2];
	    if (listEntries != null && !listEntries.isEmpty()){
	        int money = 0;
	        int num = 0;
	        for (int i=0;i<listEntries.size();i++){
	            money = money + listEntries.get(i).showBetMoney;
                num = num + listEntries.get(i).showBetNum;
            }
            arr[0] = money;
            arr[1] = num;
        }
        return arr;
    }
}
