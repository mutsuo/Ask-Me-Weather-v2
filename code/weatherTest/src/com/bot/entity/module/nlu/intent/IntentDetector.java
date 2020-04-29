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

import org.apache.log4j.Logger;

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
	
	private static Logger logger = Logger.getLogger(IntentDetector.class);
	
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
		
		ArrayList<String> w06 = new ArrayList<>();
		w06.add("����");
		keywordMap.put("W06", w06);
		
		ArrayList<String> w07 = new ArrayList<>();
		w07.add("����");
		keywordMap.put("W07", w07);
		
		ArrayList<String> w08 = new ArrayList<>();
		w08.add("����");
		w08.add("��");
		keywordMap.put("W08", w08);
		
		ArrayList<String> l01 = new ArrayList<>();
		l01.add("�յ�");
		keywordMap.put("L01", l01);
		
		ArrayList<String> l02 = new ArrayList<>();
		l02.add("����");
		keywordMap.put("L02", l02);
		
		ArrayList<String> l03 = new ArrayList<>();
		l03.add("��ɹ");
		keywordMap.put("L03", l03);
		
		ArrayList<String> l04 = new ArrayList<>();
		l04.add("����");
		keywordMap.put("L04", l04);
		
		ArrayList<String> l05 = new ArrayList<>();
		l05.add("��");
		keywordMap.put("L05", l05);
		
		ArrayList<String> l06 = new ArrayList<>();
		l06.add("����");
		l06.add("ͧ");
		l06.add("��");
		keywordMap.put("L06", l06);
		
		ArrayList<String> l07 = new ArrayList<>();
		l07.add("ϴ��");
		keywordMap.put("L07", l07);
		
		ArrayList<String> l08 = new ArrayList<>();
		l08.add("�纮");
		l08.add("��");
		l08.add("��");
		keywordMap.put("L08", l08);
		
		ArrayList<String> l09 = new ArrayList<>();
		l09.add("����");
		l09.add("���");
		l09.add("�о�");
		keywordMap.put("L09", l09);
		
		ArrayList<String> l10 = new ArrayList<>();
		l10.add("Լ��");
		l10.add("��");
		keywordMap.put("L10", l10);
		
		ArrayList<String> l11 = new ArrayList<>();
		l11.add("��");
		l11.add("�·�");
		keywordMap.put("L11", l11);
		
		ArrayList<String> l12 = new ArrayList<>();
		l12.add("����");
		l12.add("����");
		keywordMap.put("L12", l12);
		
		ArrayList<String> l13 = new ArrayList<>();
		l13.add("��ð");
		l13.add("����");
		l13.add("��");
		keywordMap.put("L13", l13);
		
		ArrayList<String> l14 = new ArrayList<>();
		l14.add("����");
		l14.add("����");
		l14.add("��ͷ��");
		l14.add("Tonny");
		l14.add("Tonny��ʦ");
		l14.add("������ʦ");
		keywordMap.put("L14", l14);
		
		ArrayList<String> l15 = new ArrayList<>();
		l15.add("�ŷ���");
		keywordMap.put("L15", l15);
		
		ArrayList<String> l16 = new ArrayList<>();
		l16.add("��ױ");
		keywordMap.put("L16", l16);
		
		ArrayList<String> l17 = new ArrayList<>();
		l17.add("����");
		keywordMap.put("L17", l17);
		
		ArrayList<String> l18 = new ArrayList<>();
		l18.add("����");
		keywordMap.put("L18", l18);
		
		ArrayList<String> l19 = new ArrayList<>();
		l19.add("����");
		l19.add("����");
		keywordMap.put("L19", l19);
		
		ArrayList<String> l20 = new ArrayList<>();
		l20.add("ɡ");
		l20.add("����");
		l20.add("����");
		keywordMap.put("L20", l20);
		
		ArrayList<String> l21 = new ArrayList<>();
		l21.add("������");
		l21.add("��ɹ");
		keywordMap.put("L21", l21);
		
		ArrayList<String> l22 = new ArrayList<>();
		l22.add("�˶�");
		l22.add("����");
		keywordMap.put("L22", l22);
		
		ArrayList<String> l23 = new ArrayList<>();
		l23.add("����");
		l23.add("����");
		l23.add("���");
		l23.add("������");
		l23.add("�ڻ�");
		keywordMap.put("L23", l23);
		
		ArrayList<String> x0 = new ArrayList<String>();
		x0.add("����");
		x0.add("����");
		keywordMap.put("X0", x0);
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
		logger.debug("detect by keywords...");
		String intentID = "";
		Intent intent = null;
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
		if(!intentID.equals("")) {
			IntentFactory intentFactory = IntentFactory.getInstance();
			intent = intentFactory.getIntent(intentID);		
		}
		
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
		logger.debug("detect by CNN...");
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
