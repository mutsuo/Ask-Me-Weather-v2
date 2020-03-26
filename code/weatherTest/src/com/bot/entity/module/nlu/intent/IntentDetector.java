/**
 * 
 */
package com.bot.entity.module.nlu.intent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bot.entity.util.KeywordsMap;

/**
 *@desc:һ�仰������
 *@author �˕D
 *@date:2020��2��13������2:46:11
 */
public class IntentDetector {
	private Map<String, ArrayList<String>> keywordMap = new HashMap<String, ArrayList<String>>();
	private static IntentDetector instance = new IntentDetector();
	
	private static final String FILE_NAME = "CNNClassification.py";
	private static final String FILE_DIR = "F:/file_temp/";
	private static final String FILE_PATH = FILE_DIR + FILE_NAME;
	private static final String COMPILER_PATH = "H:/Anaconda3/python.exe";
	
	private IntentDetector() {
		initKeywordMap();
	}
	public void initKeywordMap() {
		ArrayList<String> w01 = new ArrayList<>();
		w01.add("����");
		keywordMap.put("W01", w01);
		
		ArrayList<String> w02 = new ArrayList<>();
		w02.add("����");
		w02.add("�¶�");
		keywordMap.put("W02", w02);
		
		ArrayList<String> w03 = new ArrayList<>();
		w03.add("�²�");
		keywordMap.put("W03", w03);
		
		ArrayList<String> w04 = new ArrayList<>();
		w04.add("ʪ��");
		w04.add("����");
		keywordMap.put("W04", w04);
		
		ArrayList<String> w05 = new ArrayList<>();
		w05.add("�ܼ���");
		w05.add("��");
		keywordMap.put("W05", w05);
	}
	
	public static IntentDetector getInstance() {
		return instance;
	}
	
	/***
	 * 
	 *@desc:���ڹؼ��ʵ���ͼ���
	 *@param utterance
	 *@return
	 *@return:Intent
	 *@trhows
	 */
	public Intent intentDetectionKeywords(String utterance) {
		String intentID = "";
		for(Map.Entry<String, ArrayList<String>> entry : keywordMap.entrySet()) {
			for(int i = 0;i<entry.getValue().size();i++) {
				if(utterance.indexOf(entry.getValue().get(i))!=-1) {
					intentID = entry.getKey();
					break;
				}
			}
			if(!intentID.equals("")) {
				break;
			}
		}
		
		IntentFactory intentFactory = IntentFactory.getInstance();
		Intent intent = intentFactory.getIntent(intentID);		
		
		return intent;
	}
	
	/***
	 * 
	 *@desc:����CNN����ͼ����
	 *@param utterance
	 *@return
	 *@return:Intent
	 *@trhows
	 */
	public Intent intentDetectionCNN(String utterance) {
		String intentId = classificationCNN(utterance);		
		Intent intent = IntentFactory.getInstance().getIntent(intentId);
		
		return intent;
	}
	
	/***
	 * 
	 *@desc:��CNN������з��ࣺ��ʽ��ͼ
	 *@param utterance
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String classificationCNN(String utterance) {
		String res = "";
		try {
		    String[] args = new String[] { COMPILER_PATH, FILE_PATH, utterance};
		    Process proc = Runtime.getRuntime().exec(args);// ִ��py�ļ�
		 
		    BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		    String line = null;
		    while ((line = in.readLine()) != null) {
		        res = line;
//		        System.out.println("line:"+line);
		    }
		    in.close();
		    proc.waitFor();
		} catch (IOException e) {
		    e.printStackTrace();
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		
		return res;
	}
}
