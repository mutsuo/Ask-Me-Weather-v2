/**
 * 
 */
package com.bot.entity.module.nlu.slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@desc:�����
 *@author �˕D
 *@date:2020��2��6������6:06:18
 */
public class Slot {
	//һ��list�д洢���ɸ�ʱ����ÿ��������map�洢��keyΪʱ�����ͱ�ǣ�start/end��ʱ���ı��Ϊstart����valueΪDATE�����ʵ��
	private List<Map<String, SlotDATE>> dateList = new ArrayList<Map<String, SlotDATE>>();
	private LOCNode loc = new LOCNode();
	
	public Slot() {
		
	}
	public Slot(List<Map<String, SlotDATE>> dateList, LOCNode loc) {
		this.dateList = dateList;
		this.loc = loc;
	}
	
	public List<Map<String, SlotDATE>> getDate() {
		return dateList;
	}
	public void setDate(List<Map<String, SlotDATE>> date) {
		this.dateList = date;
	}
	public LOCNode getLoc() {
		return loc;
	}
	public void setLoc(LOCNode loc) {
		this.loc = loc;
	}
	
	public boolean isLocComplited() {
		return loc.isFilled();
	}
	public boolean isDateComplited() {
		boolean flag = false;
		if(dateList.size()>0) {
			for(Map<String, SlotDATE> map:dateList) {
				if(map.get("start")!=null && map.get("start").isFilled()) {
					if(map.get("end")==null) flag = true;
					else if(map.get("end")!=null && map.get("end").isFilled()) flag = true;
					else{
						flag = false;
						break;
					}
				}else {
					flag = false;
					break;
				}
			}
		}else {
			flag = false;
		}
		
		return flag;
	}
}
