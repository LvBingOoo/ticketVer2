package com.xhh.ticketver2.beans;

import java.util.List;

public class GameInfoEntry implements MultiItemEntity{
	public boolean isSelect;
	public int type = 0;
	public int getItemType() {
		return type;
	}


	public CommEntry commEn;
	// public String id;
	// public String fullName;
	public String end_time;
	/** 期数 **/
	public String issue;
	public List<GameInfoEntry> mList;
    // public String cp_uuid;

    public int multipleInput = 1;
}
