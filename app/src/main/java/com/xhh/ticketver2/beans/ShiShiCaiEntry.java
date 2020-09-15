package com.xhh.ticketver2.beans;


import java.io.Serializable;
import java.util.List;

public class ShiShiCaiEntry implements MultiItemEntity,Serializable{

	public String id;
	public int type = 0;
	public String selectTools;

    public int getItemType() {
		return type;
	}

	/** 维度 ui显示名字 **/
	public String name;
	/** 维度 上传标志 **/
	public String position;
	/** 维度 名字 **/
	public String name2 = "遗漏";
	/** 维度 描述 **/
	public String description;
	/** 维度 界面号码 **/
	public List<NumEntry> nums;
	/** 维度 统计遗漏的号码 **/
	public String nums_attr;
	/** 维度 快捷操作号码 **/
	public String quick_methods;
	/** 维度 选号规则 **/
	public ShiShiCaiRuleEntry rule = new ShiShiCaiRuleEntry();
	public CommEntry commEn;
	public String price;
	/** 中奖金额 ***/
	public String bonus;
	/** 详情页面显示选取号码拼接 维度+号码 ***/
	public String showDetailNum;
	/** 详情页面显示 注数 ***/
	public String showDetailCount;
	/** 详情页面显示 金额 ***/
	public String showDetailMoney;
	/** 每个彩票的每种玩法标示 ***/
	public String showType;
	/** 每个彩票的每种玩法标示 名字 ***/
	public String showName;
	/** 插件的uuid */
	public String gameplay_uuid;
	/** 插件的名字 */
	public String gameplay_name;

}
