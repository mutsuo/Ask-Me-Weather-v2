/**
 * 
 */
package com.bot.entity.util;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *@desc:һ�仰������
 *@author �˕D
 *@date:2020��2��6������5:06:07
 */
public class KeywordsMap extends HashMap<String, ArrayList<String>> {
	private static KeywordsMap instance = new KeywordsMap();
	/**
	 *@desc:һ�仰����
	 *@Fields:{field}
	 */
	private static final long serialVersionUID = 1L;
	
	public KeywordsMap() {
	}
	
	public void init() {
		ArrayList<String> w01 = new ArrayList<>();
		w01.add("����");
		instance.put("W01", w01);
		
		ArrayList<String> w02 = new ArrayList<>();
		w02.add("����");
		w02.add("�¶�");
		instance.put("W02", w02);
		
		ArrayList<String> w03 = new ArrayList<>();
		w03.add("�²�");
		instance.put("W03", w03);
		
		ArrayList<String> w04 = new ArrayList<>();
		w04.add("ʪ��");
		w04.add("����");
		instance.put("W04", w04);
		
		ArrayList<String> w05 = new ArrayList<>();
		w05.add("�ܼ���");
		w05.add("��");
		instance.put("W05", w05);
	}
	
	public static KeywordsMap getInstance() {
		return instance;
	}
}
