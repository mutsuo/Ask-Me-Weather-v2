/**
 * 
 */
package com.bot.entity.module.nlu.slot;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *@desc:����ʵ��ʶ����������ģʽ
 *@author �˕D
 *@date:2020��2��7������5:03:02
 */
public class SlotDateDetector {
	private static SlotDateDetector dateDet = new SlotDateDetector();
	private Map<String,String> operation = new HashMap<String,String>();
	
	private static final String FILE_DIR = "F:/data temp/";
	private static final String FILE_NAME = "���ʱ��ʱ�.xls";
	private static final String FILE_PATH = FILE_DIR + FILE_NAME;
	/**
	 * 
	 */
	private SlotDateDetector() {
		 try {
	            //��ȡϵͳ�ĵ�
	            POIFSFileSystem fspoi=new POIFSFileSystem(new FileInputStream(FILE_PATH));
	            //��������������
	            HSSFWorkbook workbook=new HSSFWorkbook(fspoi);
	            //�������������
	            HSSFSheet sheet=workbook.getSheet("���ʱ��ʱ�");
	            for(int i = 1;i<sheet.getPhysicalNumberOfRows();i++) {
	            	//�õ�Excel���
	            	HSSFRow row = sheet.getRow(i);
	            	operation.put(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());
	            }
            } catch (IOException e) {
                e.printStackTrace();
            }
	}
	public static SlotDateDetector getInstance() {
		return dateDet;
	}
	
	/***
	 * 
	 *@desc:����ʵ��ʶ��
	 *@param utterance
	 *@return
	 *@return:Map<String, SlotDate>
	 *@trhows
	 */
	public List<Map<String, SlotDATE>> start(String utterance) {
		//����������ʽ���ԣ�https://c.runoob.com/front-end/854
		
		//1. Ԥʶ��Ԥ��׼��
		List<Map<String, SlotDATE>> recognitionDate = preRecognition(utterance);
		
		//2. ������ʱ�����
		List<Map<String, SlotDATE>> relativeTimePhrase = relativeTimePhraseRecognition(utterance);
		
		//3. ��ȫ
		//3-0. ���ʱ�������䣺�����ʱ�����;���ʱ�����ϲ�
		if(relativeTimePhrase.size()!=0) {
			if(recognitionDate.size()==0) {
				for(Map<String, SlotDATE>relativeMap: relativeTimePhrase) {
					recognitionDate.add(relativeMap);
				}
			}else {
				for(Map<String, SlotDATE>relativeMap: relativeTimePhrase) {
					List<Map<String, SlotDATE>> recognitionDate1 = new ArrayList<Map<String, SlotDATE>>();
					for(Map<String, SlotDATE> map: recognitionDate) {
						Map<String, SlotDATE> newMap = new HashMap<String, SlotDATE>();
						newMap.put("start", map.get("start"));
						if(map.get("end")!=null) newMap.put("end", map.get("end"));
						recognitionDate1.add(newMap);
					}
					int index = 0;
					boolean flag = false;{}
					for(Map<String, SlotDATE>recoMap: recognitionDate1) {
						SlotDATE startDate = recoMap.get("start");
						SlotDATE endDate = recoMap.get("end");
						/*
						//ʱ���
						if(relativeMap.get("start")!=null && relativeMap.get("end")!=null
								&& recoMap.get("start")!=null && recoMap.get("end")!=null) {
							if(relativeMap.get("start").isBefore(startDate)) {
								recognitionDate.get(index).put("start", relativeMap.get("start"));
								flag = true;
							}
							if(!relativeMap.get("end").isBefore(endDate)) {
								recognitionDate.get(index).put("end", relativeMap.get("end"));
								flag = true;
							}						
						}
						//ʱ���
						if(relativeMap.get("start")!=null && relativeMap.get("end")==null
								&& recoMap.get("start")!=null && recoMap.get("end")==null){
							if(!relativeMap.get("start").equals(recoMap.get("start"))) {
								recognitionDate.add(relativeMap);
								flag = true;
							}
						}
						*/
						if(relativeMap.get("start")!=null && relativeMap.get("end")!=null) {
							if(startDate!=null && relativeMap.get("start").getStartIndex()<=startDate.getStartIndex()
									&& endDate!=null && relativeMap.get("end").getEndIndex()>=endDate.getEndIndex()
									|| startDate!=null && relativeMap.get("start").getStartIndex()<=startDate.getStartIndex()
									&& relativeMap.get("end").getEndIndex()>=startDate.getStartIndex()) {
								recognitionDate.get(index).put("start", relativeMap.get("start"));
								recognitionDate.get(index).put("end", relativeMap.get("end"));
								flag = true;
							}
						}
						index ++;
					}
					if(!flag) {
						recognitionDate.add(relativeMap);
						recognitionDate1.add(relativeMap);
					}
				}
				
			}
		}
		//ȥ��
		List<Integer> indexList = new ArrayList<Integer>();
		for(int i = 0;i<recognitionDate.size();i++) {
			for(int j = i+1;j<recognitionDate.size();j++) {
				if(i!=j) {
					Map<String, SlotDATE> mapA = recognitionDate.get(i);
					Map<String, SlotDATE> mapB = recognitionDate.get(j);
					//ʱ���
					if(mapA.get("start")!=null && mapA.get("end")!=null) {
						if(mapB.get("start")!=null && mapB.get("end")!=null
								&& mapA.get("start").equals(mapB.get("start"))
								&& mapA.get("end").equals(mapB.get("end"))) {
//							recognitionDate1.remove(index--);
//							System.out.println("remove");
							indexList.add(j);
						}
					}
					//ʱ���
					if(mapA.get("start")!=null && mapA.get("end")==null){
						if(mapB.get("start")!=null && mapB.get("end")==null
								&& mapA.get("start").equals(mapB.get("start"))) {
//							recognitionDate1.remove(index--);
//							System.out.println("remove");
							indexList.add(j);
						}
					}
				}
			}
		}
		int indexT = 0;
		for(Integer index: indexList) {
			recognitionDate.remove(recognitionDate.get(index-indexT));
			indexT++;
		}
		
		//3-1. �ڲ���ȫ��ʱ����ڲ�ȫ
		for(int i=0;i<recognitionDate.size();i++) {
			//����ʱ��Σ�������ڲ���ȫ
			if(recognitionDate.get(i).get("end")!=null) {
				//��ʼʱ�䲻����
				if(recognitionDate.get(i).get("start").getMonth()==-1 && recognitionDate.get(i).get("end").getMonth()!=-1) {
					recognitionDate.get(i).get("start").setMonth(recognitionDate.get(i).get("end").getMonth());
				}
				if(recognitionDate.get(i).get("start").getYear()==-1 && recognitionDate.get(i).get("end").getYear()!=-1) {
					recognitionDate.get(i).get("start").setYear(recognitionDate.get(i).get("end").getYear());
				}
				//��ֹʱ�䲻����
				if(recognitionDate.get(i).get("end").getMonth()==-1 && recognitionDate.get(i).get("start").getMonth()!=-1) {
					recognitionDate.get(i).get("end").setMonth(recognitionDate.get(i).get("start").getMonth());
				}
				if(recognitionDate.get(i).get("end").getYear()==-1 && recognitionDate.get(i).get("start").getYear()!=-1) {
					recognitionDate.get(i).get("end").setYear(recognitionDate.get(i).get("start").getYear());
				}
			}
		}
		//3-2. ���岹ȫ
		String[] todayDate = getTodayDate().split("-");
		for(int i=0;i<recognitionDate.size();i++) {
			if(recognitionDate.get(i).get("start")!=null) {
//				if(recognitionDate.get(i).get("start").getDay()==-1) {
//					recognitionDate.get(i).get("start").setDay(Integer.parseInt(todayDate[2]));
//				}
				if(recognitionDate.get(i).get("start").getMonth()==-1) {
					recognitionDate.get(i).get("start").setMonth(Integer.parseInt(todayDate[1]));
				}
				if(recognitionDate.get(i).get("start").getYear()==-1) {
					recognitionDate.get(i).get("start").setYear(Integer.parseInt(todayDate[0]));
				}
			}
			if(recognitionDate.get(i).get("end")!=null) {
//				if(recognitionDate.get(i).get("end").getDay()==-1) {
//					recognitionDate.get(i).get("end").setDay(Integer.parseInt(todayDate[2]));
//				}
				if(recognitionDate.get(i).get("end").getMonth()==-1) {
					recognitionDate.get(i).get("end").setMonth(Integer.parseInt(todayDate[1]));
				}
				if(recognitionDate.get(i).get("end").getYear()==-1) {
					recognitionDate.get(i).get("end").setYear(Integer.parseInt(todayDate[0]));
				}
			}
		}
		
		return recognitionDate;
	}
	/***
	 * 
	 *@desc:Ԥʶ��Ԥ��׼��
	 *@param utterance
	 *@return
	 *@return:Map<String,SlotDATE>
	 *@trhows
	 */
	public List<Map<String, SlotDATE>> preRecognition(String utterance){
		List<Map<String, SlotDATE>> list = new ArrayList<Map<String, SlotDATE>>();
		//����������ʽ���ԣ�https://c.runoob.com/front-end/854
		String regex = "(([0-9]{4}|[һ�����������߰˾���]{4})([\\.-/\\\\]|([��]))?)?(([0-9]{1,2}|[һ�����������߰˾�ʮ]{1,2})([\\.-/\\\\]|[��])?)?(([0-9]{1,2}|[һ�����������߰˾�ʮ]{1,3})([\\.-/\\\\]|([�պ�]))?)?";   
		Pattern p = Pattern.compile(regex); 
		Matcher m = p.matcher(utterance); // ��ȡ matcher ���� 
		List<SlotDATE> dateList = new ArrayList<SlotDATE>();
		while(m.find()) {
			if(m.start()==m.end())	continue;
			else {
				if(utterance.indexOf("��Ѯ")!=-1 || utterance.indexOf("��Ѯ")!=-1 || utterance.indexOf("��Ѯ")!=-1) {
					int index = -1;
					if(utterance.indexOf("��Ѯ")!=-1) index = utterance.indexOf("��Ѯ");
					else if(utterance.indexOf("��Ѯ")!=-1) index = utterance.indexOf("��Ѯ");
					else if(utterance.indexOf("��Ѯ")!=-1) index = utterance.indexOf("��Ѯ");
					if(index == m.end()) continue;
				}else {
					SlotDATE slotDate = new SlotDATE(utterance.substring(m.start(),m.end()));
					slotDate.setStartIndex(m.start());
					slotDate.setEndIndex(m.end()-1);
					dateList.add(slotDate);					
				}
			}
		} 
		if(dateList.size()==0) {
		}else if(dateList.size()==1) {
			Map<String, SlotDATE> map = new HashMap<String, SlotDATE>();
			map.put("start", dateList.get(0));
			list.add(map);
		}else{
			if(utterance.indexOf("��")!=-1 || utterance.indexOf("��")!=-1 || utterance.indexOf("~")!=-1) {
				int mayBeLocIndex = -1;
				if(utterance.indexOf("��")!=-1) mayBeLocIndex = utterance.indexOf("��");
				if(utterance.indexOf("��")!=-1) mayBeLocIndex = utterance.indexOf("��");
				if(utterance.indexOf("~")!=-1) mayBeLocIndex = utterance.indexOf("~");
				boolean flag = false;
				for(int i = 1;i<dateList.size();i++) {
					if(!flag && mayBeLocIndex > dateList.get(i-1).getEndIndex() && mayBeLocIndex < dateList.get(i).getStartIndex()) {
						flag = false;
						Map<String, SlotDATE> map = new HashMap<String, SlotDATE>();
						map.put("start", dateList.get(i-1));
						map.put("end", dateList.get(i));
						list.add(map);
					}else if(!flag){
						Map<String, SlotDATE> map = new HashMap<String, SlotDATE>();
						map.put("start", dateList.get(i-1));
						list.add(map);
					}else {
						Map<String, SlotDATE> map = new HashMap<String, SlotDATE>();
						map.put("start", dateList.get(i));
						list.add(map);
					}
				}
			}else {
				for(SlotDATE slotDate: dateList) {
					Map<String, SlotDATE> map = new HashMap<String, SlotDATE>();
					map.put("start", slotDate);
					list.add(map);
				}
			}
		}
		return list;
	}
	
	/***
	 * 
	 *@desc:�ԡ�yyyy-MM-dd����ʽ���ص�ǰ����
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String getTodayDate() {
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		return ft.format(dNow);
	}
	
	public int getTodayWeek() {
		int weekNum = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1;
		if(weekNum == 0) weekNum = 7;
		return weekNum;
	}
	
	/**
	 * 
	 *@desc:�������ڷ���
	 *@param mark
	 *@return
	 *@return:int
	 *@trhows
	 */
	public int decodeWeekMark(String mark) {
		int weekNum = -1;
		switch(mark) {
		case "@MON": weekNum = 1;break;
		case "@TUE": weekNum = 2;break;
		case "@WED": weekNum = 3;break;
		case "@THU": weekNum = 4;break;
		case "@FRI": weekNum = 5;break;
		case "@SAT": weekNum = 6;break;
		case "@SUN": weekNum = 7;break;
		}
		return weekNum;
	}
	
	/**
	 * 
	 *@desc:������������������
	 *@param y �������
	 *@param m �·�����
	 *@param d ������
	 *@return:Map<String,Integer>
	 *@trhows
	 */
	public Map<String, Integer> getTargetDate(int y, int m, int d) {
		Map<String, Integer> targetDate = new HashMap<String,Integer>();
		String[] todayDate = getTodayDate().split("-");
		int[] monthDaySum = {-1,31,-1,31,30,31,30,31,31,30,31,30,31};
		int year = Integer.parseInt(todayDate[0]);
		int month = Integer.parseInt(todayDate[1]);
		int day = Integer.parseInt(todayDate[2]);
		if(year%4==0 && year%100!=0 || year%400==0) {
			monthDaySum[2] = 29;
		}else {
			monthDaySum[2] = 28;
		}
		
		day += d;
		month += m;
		year += y;
		
		if(day<=0) {
			month -= 1;
			if(month <= 0) {
				year -= 1;
				month = 12 + month;
			}
			day = monthDaySum[month] + day;
		}else {
			while(day>monthDaySum[month]) {
				day -= monthDaySum[month];
				month++;
			}
			year += month / 12;
			month %= 12;			
		}
		
		targetDate.put("year", year);
		targetDate.put("month", month);
		targetDate.put("day", day);
		
		return targetDate;
	}
	
	public SlotDATE decodeControlStatement(SlotDATE slotDate,String op) {
		if(op.indexOf("@year") == -1
				&& op.indexOf("@month") == -1 
				&& op.indexOf("@day") == -1) {
			
		}else{
			if(op.indexOf("@year")!=-1) {
				String num =  "";
				if(op.indexOf("+")==-1 && op.indexOf("-")==-1) {
					num = op.substring(op.indexOf("=") + 1, op.length());
					slotDate.setYear(Integer.parseInt(num));
				}else {
					if(op.indexOf("+")!=-1) {
						num = op.substring(op.indexOf("+") + 1, op.length());
						Map<String, Integer> targetDate = getTargetDate(Integer.parseInt(num),0,0);
						slotDate.setDay(targetDate.get("day"));
						slotDate.setMonth(targetDate.get("month"));
						slotDate.setYear(targetDate.get("year"));
					}else if(op.indexOf("-")!=-1) {
						num = op.substring(op.indexOf("-") + 1, op.length());
						Map<String, Integer> targetDate = getTargetDate(-1 * Integer.parseInt(num),0,0);
						slotDate.setDay(targetDate.get("day"));
						slotDate.setMonth(targetDate.get("month"));
						slotDate.setYear(targetDate.get("year"));
					} 
				}
			}else if(op.indexOf("@month")!=-1){
				String num =  "";
				if(op.indexOf("+")==-1 && op.indexOf("-")==-1) {
					num = op.substring(op.indexOf("=") + 1, op.length());
					slotDate.setMonth(Integer.parseInt(num));
				}else {
					if(op.indexOf("+")!=-1) {
						num = op.substring(op.indexOf("+") + 1, op.length());
						Map<String, Integer> targetDate = getTargetDate(0,Integer.parseInt(num),0);
						slotDate.setDay(targetDate.get("day"));
						slotDate.setMonth(targetDate.get("month"));
						slotDate.setYear(targetDate.get("year"));
					}else if(op.indexOf("-")!=-1) {
						num = op.substring(op.indexOf("-") + 1, op.length());
						Map<String, Integer> targetDate = getTargetDate(0,-1 * Integer.parseInt(num),0);
						slotDate.setDay(targetDate.get("day"));
						slotDate.setMonth(targetDate.get("month"));
						slotDate.setYear(targetDate.get("year"));
					} 
				}
			}else if(op.indexOf("@day")!=-1){
				String num =  "";
				if(op.indexOf("+")==-1 && op.indexOf("-")==-1) {
					num = op.substring(op.indexOf("=") + 1, op.length());
					slotDate.setDay(Integer.parseInt(num));
				}else {
					if(op.indexOf("+")!=-1) {
						num = op.substring(op.indexOf("+") + 1, op.length());
						Map<String, Integer> targetDate = getTargetDate(0,0,Integer.parseInt(num));
						slotDate.setDay(targetDate.get("day"));
						slotDate.setMonth(targetDate.get("month"));
						slotDate.setYear(targetDate.get("year"));
					}else if(op.indexOf("-")!=-1) {
						num = op.substring(op.indexOf("-") + 1, op.length());
						Map<String, Integer> targetDate = getTargetDate(0,0,-1 * Integer.parseInt(num));
						slotDate.setDay(targetDate.get("day"));
						slotDate.setMonth(targetDate.get("month"));
						slotDate.setYear(targetDate.get("year"));
					} 
				}
			}
		}
		return slotDate;
	}
	
	/***
	 * 
	 *@desc:���ʱ�����ʶ��
	 *		</br>��ע��ʱ��Σ��á�start���͡�end����ǣ�ʱ���:��ֻ�С�start�����
	 *@param map
	 *@return:Map<String,SlotDATE>
	 *@trhows
	 */
	public List<Map<String, SlotDATE>> relativeTimePhraseRecognition(String utterance){
		/**
		 * 1. һ�����������ֻ����һ���������Ʊ�ʶ��
		 * 		-	�������Ʊ�ʶ�����磬at day+1
		 * 2. Ԥ���峣������д����������Ʊ�ʶ������
		 * 		-	Ԥ���峣�����磬at TODAY
		 * 		-	���Ʊ�ʶ�����磬at month
		 */
		List<Map<String, SlotDATE>> relativeTimePhrase = new ArrayList<Map<String, SlotDATE>>();
		for(Map.Entry<String, String> entry : operation.entrySet()) {
			for(String phrase : entry.getKey().split(" ")) {
				//���ҵ���Զ��ִ����Ӧ����
				if(utterance.indexOf(phrase)!=-1) {
					int startIndex = utterance.indexOf(phrase);
					int endIndex = startIndex + phrase.length() - 1 ;
					String[] operations = entry.getValue().split(" ");
					
					boolean flag = false;
					int toIndex = 0;
					for(String op : operations) {
						if(op.equals("@to")) {
							flag = true;
							break;
						}
						toIndex++;
					}
					
					//��ʱ��Σ�һ����������һ������
					String[] date = getTodayDate().split("-");
					if(!flag) {
						//if Ԥ���峣��
						if(operations.length == 1
								&& operations[0].indexOf("@year") == -1
								&& operations[0].indexOf("@month") == -1 
								&& operations[0].indexOf("@day") == -1) {
							String con = operations[0];
							if(con.indexOf("@TODAY")!=-1) {
								Map<String, SlotDATE> map = new HashMap<String, SlotDATE>();
								SlotDATE slotDate = new SlotDATE();
								slotDate.setStartIndex(startIndex);
								slotDate.setEndIndex(endIndex);
								slotDate.setRawString(phrase);
								slotDate.setDay(Integer.parseInt(date[2]));
								slotDate.setMonth(Integer.parseInt(date[1]));
								slotDate.setYear(Integer.parseInt(date[0]));
								if(this.isTimeScale(relativeTimePhrase,utterance,startIndex)) {
									relativeTimePhrase.get(relativeTimePhrase.size()-1).put("end", slotDate);
								}else {
									map.put("start", slotDate);
									map.put("end", null);
									relativeTimePhrase.add(map);									
								}
							}else if(con.indexOf("@MON")!=-1
									|| con.indexOf("@TUE")!=-1
									|| con.indexOf("@WED")!=-1
									|| con.indexOf("@THU")!=-1
									|| con.indexOf("@FRI")!=-1
									|| con.indexOf("@SAT")!=-1
									|| con.indexOf("@SUN")!=-1){
								Map<String, SlotDATE> map = new HashMap<String, SlotDATE>();
								SlotDATE slotDate = new SlotDATE();
								slotDate.setStartIndex(startIndex);
								slotDate.setEndIndex(endIndex);
								slotDate.setRawString(phrase);
								int thisWeekNum = getTodayWeek();
								int weekNum = decodeWeekMark(con);
								Map<String, Integer> targetDate = null;
								if(weekNum>=thisWeekNum) {
									targetDate = getTargetDate(0,0,weekNum-thisWeekNum);
								}else {
									targetDate = getTargetDate(0,0,7 - thisWeekNum  + weekNum);
								}
								slotDate.setDay(targetDate.get("day"));
								slotDate.setMonth(targetDate.get("month"));
								slotDate.setYear(targetDate.get("year"));
								if(this.isTimeScale(relativeTimePhrase,utterance,startIndex)) {
									relativeTimePhrase.get(relativeTimePhrase.size()-1).put("end", slotDate);
								}else {
									map.put("start", slotDate);
									map.put("end", null);
									relativeTimePhrase.add(map);									
								}
							}else if(con.indexOf("@NEXT_WEEK")!=-1) {
								Map<String, SlotDATE> map = new HashMap<String, SlotDATE>();
								int thisWeekNum = getTodayWeek();
								Map<String, Integer> targetDate = getTargetDate(0,0,-1 * thisWeekNum + 8);
								SlotDATE startSlotDate = new SlotDATE();
								startSlotDate.setStartIndex(startIndex);
								startSlotDate.setEndIndex(endIndex);
								startSlotDate.setRawString(phrase);
								startSlotDate.setDay(targetDate.get("day"));
								startSlotDate.setMonth(targetDate.get("month"));
								startSlotDate.setYear(targetDate.get("year"));
								map.put("start", startSlotDate);
								
								Map<String, Integer> nextTargetDate = getTargetDate(0,0,-1 * thisWeekNum + 14);
								SlotDATE endSlotDate = new SlotDATE();
								endSlotDate.setStartIndex(startIndex);
								endSlotDate.setEndIndex(endIndex);
								endSlotDate.setRawString(phrase);
								endSlotDate.setDay(nextTargetDate.get("day"));
								endSlotDate.setMonth(nextTargetDate.get("month"));
								endSlotDate.setYear(nextTargetDate.get("year"));
								map.put("end", endSlotDate);
								relativeTimePhrase.add(map);
							}
						}else {
							Map<String, SlotDATE> map = new HashMap<String, SlotDATE>();
							SlotDATE slotDate = new SlotDATE();
							for(String op : operations) {
								slotDate = decodeControlStatement(slotDate, op);								
							}
							slotDate.setStartIndex(startIndex);
							slotDate.setEndIndex(endIndex);
							slotDate.setRawString(phrase);
							if(this.isTimeScale(relativeTimePhrase,utterance,startIndex)) {
								relativeTimePhrase.get(relativeTimePhrase.size()-1).put("end", slotDate);
							}else {
								map.put("start", slotDate);
								map.put("end", null);
								relativeTimePhrase.add(map);									
							}
						}
					}else {
						//ʱ���
						Map<String, SlotDATE> map = new HashMap<String, SlotDATE>();
						SlotDATE slotDate = new SlotDATE();
						slotDate.setStartIndex(startIndex);
						slotDate.setEndIndex(endIndex);
						slotDate.setRawString(phrase);
						for(int i = 0;i < toIndex;i++) {
							slotDate = decodeControlStatement(slotDate, operations[i]);
						}
						map.put("start", slotDate);
						
						SlotDATE nextSlotDate = new SlotDATE();
						nextSlotDate.setStartIndex(startIndex);
						nextSlotDate.setEndIndex(endIndex);
						nextSlotDate.setRawString(phrase);
						for(int i = toIndex+1;i < operations.length ;i++) {
							nextSlotDate = decodeControlStatement(nextSlotDate, operations[i]);
						}
						map.put("end", nextSlotDate);
						relativeTimePhrase.add(map);
					}
				}
			}
		}
		return relativeTimePhrase;
	}
	/**
	 *@desc:�ж��Ƿ���ʱ���
	 *@param relativeTimePhrase ���ʱ�����ʼ�
	 *@param utterance �û����
	 *@param startIndex ��ǰ����������е���ʼ�±�
	 *@return
	 *@return:boolean
	 *@trhows
	 */
	private boolean isTimeScale(List<Map<String, SlotDATE>> relativeTimePhrase, String utterance, int startIndex) {
		boolean flag = false;
		
		if(relativeTimePhrase.size()>0) {
			int tIndex = -1;
			if(utterance.indexOf("��")!=-1) tIndex = utterance.indexOf("��");
			if(utterance.indexOf("��")!=-1) tIndex = utterance.indexOf("��");
			if(utterance.indexOf("~")!=-1) tIndex = utterance.indexOf("~");
			if(relativeTimePhrase.size()==1 
					&& relativeTimePhrase.get(relativeTimePhrase.size()-1).get("end")==null
					&& tIndex!=-1
					&& tIndex>relativeTimePhrase.get(relativeTimePhrase.size()-1).get("start").getEndIndex()
					&& tIndex<startIndex) {
				flag = true;
			}			
		}
		return flag;
	}

}
