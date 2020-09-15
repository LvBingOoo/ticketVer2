package com.xhh.ticketver2.beans;

import java.io.Serializable;

public class ShiShiCaiRuleEntry implements Serializable{
	public static String IS_REJECT_TRUE = "true";

	/* 确定需要选取个数 */
	public String nums;
	/* 最多选中个数 */
	public int max_nums;
	/* 至少选中个数 */
	public int min_nums;
	/* #是否排斥 */
	public String is_reject;
	/* 维度 系数 计算注数使用 */
	public String coefficient_flag = "N";
	/* 维度 主系数 */
	public String coefficient_main = "2";

	public String omitCode;
}
