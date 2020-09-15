package com.xhh.ticketver2.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class GameListEntry implements MultiItemEntity, Serializable{
	public int type = 0;
    public String showTip;//投注列表显示
    public int showBetMoney;//投注列表显示
    public int showBetNum;//投注列表显示
	public String playedId;

	public int getItemType() {
		return type;
	}

	public CommEntry commEn;
    public String playedType;
	public String fullName;
	public List<GameListEntry> mlist;
	public String simpleDescribe;
	public List<ShiShiCaiEntry> ruleEntry;
	public String lotteryTypeName;

    public GameListEntry myclone() {
        GameListEntry outer = null;
          try {
              // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
               ByteArrayOutputStream baos = new ByteArrayOutputStream();
               ObjectOutputStream oos = new ObjectOutputStream(baos);
               oos.writeObject(this);
               // 将流序列化成对象
               ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
               ObjectInputStream ois = new ObjectInputStream(bais);
               outer = (GameListEntry) ois.readObject();
          } catch (IOException e) {
                e.printStackTrace();
          } catch (ClassNotFoundException e) {
              e.printStackTrace();
           }
        return outer;
    }
}
