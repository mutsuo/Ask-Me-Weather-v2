/**
 * 
 */
package com.bot.entity.module.nlg;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import org.apache.log4j.Logger;

import com.bot.entity.module.dm.Policy;
import com.bot.entity.module.nlu.intent.Intent;
import com.bot.entity.module.nlu.slot.Slot;
import com.bot.entity.util.HttpUtil;

import net.sf.json.JSONObject;

/**
 *@desc:�Ի�����ģ�飬����ģʽ
 *@author �˕D
 *@date:2020��2��15������8:00:13
 */
public class NLGModule {
	private static NLGModule instance = new NLGModule();
	public static Logger logger = Logger.getLogger(NLGModule.class);
	/**
	 * 
	 */
	private NLGModule() {
	}
	
	public static NLGModule getInstance() {
		return instance;
	}

	public List<String> solve(int instr, String utterance, Intent intent, Slot slot) throws Exception {
		List<String> reply = new ArrayList<String>();
		switch(instr) {
		case Policy.STATE_OK:
			reply = taskBasedCommonReply(intent, slot);
			break;
		case Policy.STATE_ILLEGAL:
			reply.add(this.safeReply());
			break;
		case Policy.INSTRUCTION_CHAT_API:
			reply.add(replyByBotApi(utterance));
			break;
		case Policy.INSTRUCTION_ERROR_INTENT_LOST:
			reply.add(intentLostReply());
			break;
		case Policy.INSTRUCTION_ERROR_DATE_ILLEGAL:
			//����ʶ
			reply.add(dateIllegalReply());
			break;
		case Policy.INSTRUCTION_ERROR_LOC_UNSUPPORT:
			//��֧��
			reply.add(locUnsupportReply());
			break;
		case Policy.INSTRUCTION_ERROR_DATE_DAY_LOST:
			reply.add(dayLostReply(slot));
			break;
		case Policy.INSTRUCTION_ERROR_DATE_MONTH_LOST:
			reply.add("INSTRUCTION_ERROR_DATE_MONTH_LOST");
			break;
		case Policy.INSTRUCTION_ERROR_DATE_DAY_AND_MONTH_LOST:
			reply.add("INSTRUCTION_ERROR_DATE_DAY_AND_MONTH_LOST");
			break;
		case Policy.INSTRUCTION_ERROR_DATE_UNSUPPORT:
			reply.add(this.dateUnsupportedReply());
			break;
		case Policy.INSTRUCTION_ERROR_DATE_LOST:
			reply.add(this.dateLostReply(intent));
			break;
		case Policy.INSTRUCTION_ERROR_DATE_AND_LOC_LOST:
			reply.add(dateAndLocLostReply(intent));
			break;
		case Policy.INSTRUCTION_ERROR_LOC_LOST:
			reply.add(locLostReply(intent));
			break;
		}
		
		return reply;
	}
	
	/**
	 *@desc:һ�仰����
	 *@param intent
	 *@return
	 *@return:String
	 *@trhows
	 */
	private String dateLostReply(Intent intent) {
		StringBuffer reply = new StringBuffer();
		reply.append("�������");
		reply.append(intent.getIntentName());
		reply.append("ѽ");
		return reply.toString();
	}

	/**
	 * 
	 *@desc:��ȫ�ظ�
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String safeReply() {
		String[] replys = {"ԭ�����","��","������"};
		Random rand = new Random();
		return replys[rand.nextInt(replys.length-1)];
	}

	public String unsupportedReply(String date, String intentName) {
		StringBuffer sb = new StringBuffer();
		sb.append("��Ǹ��������û��");
		String recentDate = isRecent(date); 
		if(recentDate.equals("")) sb.append(date);
		else sb.append(recentDate);
		sb.append("��");
		sb.append(intentName);
		sb.append("����");
		return sb.toString();
	}
	/***
	 * 
	 *@desc:�ʺ�
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String greeting() {
		String[] replys = {"���ѽ\\(@^0^@)/","���~"};
		Random rand = new Random();
		return replys[rand.nextInt(replys.length-1)];
	}

	/**
	 *@desc:һ�仰����
	 *@return
	 *@return:String
	 *@trhows
	 */
	private String intentLostReply() {
		String[] replys = {"������Ҫѯ��ʲô��","�Ҳ�̫���������˼����","����������������","�Ǹ����Ҳ�̫�������˼"};
		Random rand = new Random();
		return replys[rand.nextInt(replys.length-1)];
	}

	/**
	 *@desc:Date�����ȱʧdayʱ�Ļظ�
	 *@param slot
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String dayLostReply(Slot slot) {
		StringBuffer reply = new StringBuffer(); 
		if(slot.getDate().size()==1 && slot.getDate().get(0).get("end")==null) {
			reply.append(slot.getDate().get(0).get("start").getMonth());
			reply.append("�µ���һ����");
		}else {
			Random rand = new Random();
			switch(rand.nextInt(1)) {
			case 0:
				reply.append("�����أ�");
				break;
			case 1:
				reply.append("������Ҿ�������ڡ���");
				break;
			}
		}
		return reply.toString();
	}

	/**
	 *@desc:����DATE���Ϸ�ʱ�Ļظ�
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String dateIllegalReply() {
		String[] replys = {"���ڶ��Ұɣ������ϸ���û����һ�죡","���¼��ţ��鷳����˵һ�飿","��˵��������ڡ�������̫���ϳ���"};
		Random rand = new Random();
		
		return replys[rand.nextInt(replys.length-1)];
	}

	/**
	 *@desc:��ȥ���ڵĻش�
	 *@return
	 *@return:String
	 *@trhows
	 */
	private String dateUnsupportedReply() {
		String[] replys = {"��Ǹ���Ҳ��ǵ��ˡ���","�Ҳ鲻����ȥ����������"};
		Random rand = new Random();
		return replys[rand.nextInt(replys.length-1)];
	}

	/**
	 *@desc:LOC����۶�ʧʱ�Ļظ�
	 *@param intent
	 *@return
	 *@return:String
	 *@trhows
	 */
	private String locLostReply(Intent intent) {
		StringBuffer reply = new StringBuffer();
		Random rand = new Random();
		switch(rand.nextInt(1)) {
		case 0:
			reply.append("��Ҫ��ѯ�����");
			reply.append(intent.getIntentName());
			reply.append("��");
			break;
		case 1:
			reply.append("�������Ҫ��ѯ�ĵص�XD");
			break;
		}
		return reply.toString();
	}

	/**
	 *@desc:DATE��LOC����۶�ȱʧ�Ļظ�
	 *@param intent
	 *@return
	 *@return:String
	 *@trhows
	 */
	private String dateAndLocLostReply(Intent intent) {
		StringBuffer reply = new StringBuffer();
		Random rand = new Random();
		switch(rand.nextInt(1)) {
		case 0:
			reply.append("�ţ���Ҫ��ʲô�ط���");
			reply.append(intent.getIntentName());
			break;
		case 1:
			reply.append("����鼸�ŵ�");
			reply.append(intent.getIntentName());
			reply.append("ѽ");
			break;
		}
		return reply.toString();
	}

	/**
	 *@desc:����LOC��֧��ʱ�Ļظ�
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String locUnsupportReply() {
		String[] replys = {"��Ǹ�����Ҳ���ʶ��˵������ط�","��Ǹ����˵������ط����ģ�","ʲô����˵�ģ�"};
		Random rand = new Random();
		
		return replys[rand.nextInt(replys.length-1)];
	}

	public String isRecent(String date) {
		String recen = "";
		String[] dayInfo = date.split("-");
		Date dNow = new Date();
		SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
		String[] todayDate = fm.format(dNow).split("-");
		if(dayInfo[0].equals(todayDate[0]) && dayInfo[1].equals(todayDate[1]) && dayInfo[2].equals(todayDate[2])) {
			recen = "����";
		}else if(dayInfo[0].equals(todayDate[0]) && dayInfo[1].equals(todayDate[1]) 
				&& Integer.parseInt(dayInfo[2])==Integer.parseInt(todayDate[2])+1) {
			recen = "����";
		}else if(dayInfo[0].equals(todayDate[0]) && dayInfo[1].equals(todayDate[1]) 
				&& Integer.parseInt(dayInfo[2])==Integer.parseInt(todayDate[2])+2) {
			recen = "����";
		}
		
		return recen;
	}

	/***
	 * 
	 *@desc:������ϻظ�
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String netErrorReply() {
		return "��Ǹ������������ϣ��޷����յ���Ӧ";
	}
	
	public List<String> taskBasedCommonReply(Intent intent, Slot slot) throws Exception {
			WeatherService service = WeatherService.getInstance();
			List<WeatherInfo> list = service.apiQuery(WeatherService.XIN_ZHI_TIAN_QI, intent, slot);
			List<String> replys = null;
			
			if(list == null) {
				replys.add(netErrorReply());
			}else {
				if(intent.getIntentId().equals(Intent.WEATHER)) replys = weatherReply(slot, list);
				else if(intent.getIntentId().equals(Intent.TEMPERATURE)) replys = tempReply(slot, list);
				else if(intent.getIntentId().equals(Intent.TEMPERATURE_RANGE)) replys = tempRangeReply(slot, list);
				else if(intent.getIntentId().charAt(0)=='L') replys = lifeIndexReply(slot, list);
				else if(intent.getIntentId().charAt(0)=='X') replys = restrictionReply(slot, list);
				else replys = otherReply(slot, list, intent);
			}
			
			
			return replys;
		}
		
		/**
	 *@desc:һ��������
	 *@param slot
	 *@param list
	 *@return
	 *@return:List<String>
	 *@trhows
	 */
	private List<String> restrictionReply(Slot slot, List<WeatherInfo> list) {
		List<String> replys = new ArrayList<String>();
		if(list.size()==0) replys.add("û����ص�������Ϣ���������ɳ���");
		for(WeatherInfo wi: list) {
			String date = isRecent(wi.getDate().getFormatedDate());
			if(date.equals("")) date = wi.getDate().getMonth()+"��"+wi.getDate().getDay()+"��";
			
			String loc = slot.getLoc().getName();
			
			StringBuffer sb = new StringBuffer();
			if(wi.getRestriction().size()==0) {
				sb.append("û����ص�������Ϣ���������ɳ���");
			}else {
				sb.append(loc);
				sb.append(date);
				sb.append("�ĳ���������Ϣ���£�\n");
				sb.append(wi.getRestriction().get("penalty"));
				sb.append("\n���з�ΧΪ");
				sb.append(wi.getRestriction().get("region"));
				sb.append("\n");
				if(wi.getRestriction().get("remark")!=null) {
					sb.append(wi.getRestriction().get("remark"));
					sb.append("\n");
				}
				sb.append(wi.getRestriction().get("memo"));
				if(wi.getRestriction().get("plates")!=null) {
					sb.append("��");
					sb.append(wi.getRestriction().get("plates"));
				}
			}
			replys.add(sb.toString());
		}
		
		return replys;
	}

		/**
	 *@desc:����ָ��
	 *@param slot
	 *@param list
	 *@return
	 *@return:List<String>
	 *@trhows
	 */
	private List<String> lifeIndexReply(Slot slot, List<WeatherInfo> list) {
		List<String> replys = new ArrayList<String>();
		StringBuffer sb = new StringBuffer();
		
		if(list.get(0).getIndex().get("detail")!=null) {
			sb.append(slot.getLoc().getName());
			sb.append("����");
			sb.append(list.get(0).getIndex().get("detail"));
			replys.add(sb.toString());
		}
		
		return replys;
	}

		/**
	 *@desc: �²�
	 *@param slot
	 *@param list
	 *@return
	 *@return:List<String>
	 *@trhows
	 */
	public List<String> tempRangeReply(Slot slot, List<WeatherInfo> list) {
		List<String> replys = new ArrayList<String>();
		
		for(WeatherInfo wi: list) {
			String date = isRecent(wi.getDate().getFormatedDate());
			if(date.equals("")) date = wi.getDate().getMonth()+"��"+wi.getDate().getDay()+"��";
			
			String loc = slot.getLoc().getName();
			
			int tempRange = -1;
			
			String tempHigh = wi.getMaxTem();
			String tempLow = wi.getMinTem();
			
			if(!tempHigh.equals("") && !tempLow.equals("")) {
				tempRange = Integer.parseInt(tempHigh) - Integer.parseInt(tempLow);
				if(tempRange > 0) {
					StringBuffer sb = new StringBuffer();
					sb.append(loc);
					sb.append(date);
					sb.append("���²�Ϊ");
					sb.append(tempRange);
					sb.append("���϶�");
					
					replys.add(sb.toString());
				}else {
					replys.add(unsupportedReply(date, "�²�"));
				}
			}else {
				replys.add(unsupportedReply(date, "�²�"));
			}
			
		}
		return replys;
	}

		/**
				 *@desc: �����ͣ����������ʱ�Ļظ�
				 *@param intent
				 *@param slot
				 *@return
				 *@return:String
				 * @throws Exception 
				 *@trhows
				 */
		//		public List<String> taskBasedCommonReply1(Intent intent, Slot slot) throws Exception {
		//			WeatherService service = WeatherService.getInstance();
		//			List<WeatherInfo> list = service.apiQuery(WeatherService.JISU, intent, slot);
		//			
		//			List<String> replys = new ArrayList<String>();
		//			Random rand = new Random();
		//			for(Entry<String, Map<String, String>> entry: map.entrySet()) {
		//				String date = isRecent(entry.getKey());
		//				if(date.equals("") && entry.getKey()!=null) {
		//					String[] d = entry.getKey().split("-"); 
		//					date = d[1]+"��"+d[2]+"��";
		//				}
		//				
		//				String loc = slot.getLoc().getName();
		//				
		//				String weather = "";
		//				if(entry.getValue().get("weather")!=null) weather = entry.getValue().get("weather");
		//				else{
		//					if(entry.getValue().get("weather_day")!=null && entry.getValue().get("weather_night")!=null) {
		//						weather = entry.getValue().get("weather_day") + "ת" +entry.getValue().get("weather_night");					
		//					}else {
		//						if(entry.getValue().get("weather_day")!=null) weather = entry.getValue().get("weather_day");
		//						if(entry.getValue().get("weather_night")!=null) weather = entry.getValue().get("weather_night");
		//					}
		//				}
		//				
		//				String tempLow = "";
		//				if(entry.getValue().get("templow")!=null) tempLow = entry.getValue().get("templow");
		//							
		//				String tempHigh = "";
		//				if(entry.getValue().get("temphigh")!=null) tempHigh = entry.getValue().get("templow");
		//				
		//				String tempDay = "";
		//				if(entry.getValue().get("templow_day")!=null) tempDay = entry.getValue().get("templow_day");
		//				
		//				String tempNight = "";
		//				if(entry.getValue().get("templow_night")!=null) tempNight = entry.getValue().get("templow_night");
		//				
		//				String index = "";
		//				if(entry.getValue().get("index")!=null) index = entry.getValue().get("index");
		//				
		//				StringBuffer buffer = new StringBuffer();
		//				buffer.append(loc);
		//				buffer.append(date);
		//				buffer.append(weather);
		//				buffer.append(",");
		//				if(!tempHigh.equals("") && !tempLow.equals("")) {
		//					buffer.append(tempLow);
		//					buffer.append("�浽");
		//					buffer.append(tempHigh);
		//					buffer.append("��");
		//				}else {
		//					if(!tempHigh.equals("")) {
		//						buffer.append("�����Ϊ");
		//						buffer.append(tempHigh);
		//						buffer.append("��");
		//					}
		//					else if(!tempLow.equals("")) {
		//						buffer.append("�����Ϊ");
		//						buffer.append(tempLow);
		//						buffer.append("��");
		//					}
		//					else if(!tempDay.equals("") && !tempNight.equals("")) {
		//						buffer.append("�������");
		//						buffer.append(tempDay);
		//						buffer.append("���϶ȣ�");
		//						buffer.append("ҹ�����");
		//						buffer.append(tempNight);
		//						buffer.append("���϶�");
		//					}else {
		//						int highTemp = -1, lowTemp = -1;
		//						if(!tempDay.equals("")) {
		//							highTemp = Integer.parseInt(tempDay);
		//	//						buffer.append("�������");
		//	//						buffer.append(tempDay);
		//	//						buffer.append("���϶�");
		//						}else if(!tempNight.equals("")){
		//							lowTemp = Integer.parseInt(tempNight);
		//	//						buffer.append("ҹ�����");
		//	//						buffer.append(tempNight);
		//	//						buffer.append("���϶�");
		//						}
		//						if(highTemp!=-1 && lowTemp!=-1) {
		//							if(highTemp < lowTemp) {
		//								int t = highTemp;
		//								highTemp = lowTemp;
		//								lowTemp = t;
		//							}
		//							buffer.append(lowTemp);
		//							buffer.append("��");
		//							buffer.append(highTemp);
		//							buffer.append("���϶�");
		//						}
		//					}
		//				}
		//				buffer.append("\n");
		//				if(!index.equals("")) buffer.append(index);
		//				
		//				replys.add(buffer.toString());
		//			}
		//			
		//			
		//			return replys;
		//		}
				
	public List<String> weatherReply(Slot slot, List<WeatherInfo> list){
		List<String> replys = new ArrayList<String>();
		for(WeatherInfo wi: list) {
			String date = isRecent(wi.getDate().getFormatedDate());
			if(date.equals("")) date = wi.getDate().getMonth()+"��"+wi.getDate().getDay()+"��";
			
			String loc = slot.getLoc().getName();
			
			String weather = "";
			if(!wi.getWeather().equals("")) weather = wi.getWeather();
			else{
				if(!wi.getDay().getWeather().equals("") && !wi.getNight().getWeather().equals("")) {
					if(!wi.getDay().getWeather().equals(wi.getNight().getWeather()))
						weather = wi.getDay().getWeather() + "ת" + wi.getNight().getWeather();
					else weather = wi.getDay().getWeather();
				}else {
					if(!wi.getDay().getWeather().equals("")) weather = wi.getDay().getWeather();
					if(!wi.getNight().getWeather().equals("")) weather = wi.getNight().getWeather();
				}
			}
			
			String tempLow = wi.getMinTem();
			String tempHigh = wi.getMaxTem();
			
			String tempDay = "";
			if(wi.getDay()!=null) wi.getDay().getMaxTem();
			
			String tempNight = "";
			if(wi.getNight()!=null) wi.getNight().getMinTem();
			
//				String index = "";
//				if(entry.getValue().get("index")!=null) index = entry.getValue().get("index");
			
			StringBuffer buffer = new StringBuffer();
			buffer.append(loc);
			buffer.append(date);
			buffer.append(weather);
			buffer.append(",");
			if(!wi.getNowTem().equals("")) {
				buffer.append(wi.getNowTem());
				buffer.append("���϶�");
			}
			else if(!tempHigh.equals("") && !tempLow.equals("")) {
				if(!tempHigh.equals(tempLow)) {
					buffer.append(tempLow);
					buffer.append("�浽");
					buffer.append(tempHigh);
					buffer.append("��");
				}else {
					buffer.append(tempLow);
					buffer.append("��");
				}
				
			}else {
				if(!tempHigh.equals("")) {
					buffer.append("�����Ϊ");
					buffer.append(tempHigh);
					buffer.append("��");
				}
				else if(!tempLow.equals("")) {
					buffer.append("�����Ϊ");
					buffer.append(tempLow);
					buffer.append("��");
				}
				else if(!tempDay.equals("") && !tempNight.equals("")) {
					buffer.append("�������");
					buffer.append(tempDay);
					buffer.append("���϶ȣ�");
					buffer.append("ҹ�����");
					buffer.append(tempNight);
					buffer.append("���϶�");
				}else {
					int highTemp = -1, lowTemp = -1;
					if(!tempDay.equals("")) {
						highTemp = Integer.parseInt(tempDay);
//						buffer.append("�������");
//						buffer.append(tempDay);
//						buffer.append("���϶�");
					}else if(!tempNight.equals("")){
						lowTemp = Integer.parseInt(tempNight);
//						buffer.append("ҹ�����");
//						buffer.append(tempNight);
//						buffer.append("���϶�");
					}
					if(highTemp!=-1 && lowTemp!=-1) {
						if(highTemp < lowTemp) {
							int t = highTemp;
							highTemp = lowTemp;
							lowTemp = t;
						}
						buffer.append(lowTemp);
						buffer.append("��");
						buffer.append(highTemp);
						buffer.append("���϶�");
					}else {
						if(highTemp!=-1) {
							buffer.append(highTemp);
							buffer.append("���϶�");
						}else if(lowTemp!=-1) {
							buffer.append(lowTemp);
							buffer.append("���϶�");
						}
					}
				}
			}
//				buffer.append("\n");
//				if(!index.equals("")) buffer.append(index);
			
			replys.add(buffer.toString());
		}
		
		return replys;
	}

	/***
	 * 
	 *@desc:������������
	 *@param slot
	 *@param list
	 *@return
	 *@return:List<String>
	 *@trhows
	 */
	public List<String> otherReply(Slot slot, List<WeatherInfo> list, Intent intent){
		List<String> replys = new ArrayList<String>();
		for(WeatherInfo wi: list) {
			String date = isRecent(wi.getDate().getFormatedDate());
			if(date.equals("")) date = wi.getDate().getMonth()+"��"+wi.getDate().getDay()+"��";
			
			String data = "";
			if(intent.getIntentName().equals(Intent.VISIBILITY)) data = wi.getVisibility();
			else if(intent.getIntentName().equals(Intent.HUMIDITY)) data = wi.getHumidity();
			else if(intent.getIntentName().equals(Intent.WIND_DIR)) data = wi.getWindDir();
			else if(intent.getIntentName().equals(Intent.WIND_SPEED)) data = wi.getWindSpeed();
			else if(intent.getIntentName().equals(Intent.WIND_POWER)) data = wi.getWindPower();
			
			if(data.equals("")) {
				replys.add(this.unsupportedReply(wi.getDate().getFormatedDate(), intent.getIntentName()));
			}else {
				StringBuffer sb = new StringBuffer();
				String loc = "";
				loc = slot.getLoc().getName();
				
				sb.append(loc);
				sb.append(date);
				sb.append("��");
				sb.append(intent.getIntentName());
				sb.append("Ϊ");
				sb.append(data);
				
				replys.add(sb.toString());
			}
		}
		
		return replys;
	}
	
	/***
	 * 
	 *@desc:�¶�
	 *@param slot
	 *@param list
	 *@return
	 *@return:List<String>
	 *@trhows
	 */
	public List<String> tempReply(Slot slot, List<WeatherInfo> list){
		List<String> replys = new ArrayList<String>();
		
		for(WeatherInfo wi: list) {
			String date = isRecent(wi.getDate().getFormatedDate());
			if(date.equals("")) date = wi.getDate().getMonth()+"��"+wi.getDate().getDay()+"��";
			
			String loc = "";
			loc = slot.getLoc().getName();
			
			String tempHigh = "";
			if(!wi.getMaxTem().equals("")) tempHigh = wi.getMaxTem();
			
			String tempLow = "";
			if(!wi.getMinTem().equals("")) tempLow = wi.getMinTem();
			
			String tempDay = "";
			if(wi.getDay()!=null && !wi.getDay().getMaxTem().equals("")) tempDay = wi.getDay().getMaxTem();
			
			String tempNight = "";
			if(wi.getNight()!=null && !wi.getNight().getMinTem().equals("")) tempNight = wi.getNight().getMinTem();
			
			String tempRange = "";
			if(!tempHigh.equals("") && !tempLow.equals("")) tempRange = tempLow + "��" + tempHigh + "���϶�";
			else if(!tempDay.equals("") && !tempNight.equals("")) tempRange = tempNight + "��" + tempDay + "���϶�";
			
			StringBuffer sb = new StringBuffer();
			sb.append(loc);
			sb.append(date);
			sb.append("���¶�Ϊ");
			sb.append(tempRange);
			sb.append("��");
			if(!wi.getNowTem().equals("")) {
				sb.append("\n��ǰ�¶�Ϊ");
				sb.append(wi.getNowTem());
				sb.append("���϶�");
			}
			replys.add(sb.toString());
		}
		
		return replys;
	}
		
	/**
	 * 
	 *@desc:����api��������Ӧ��
	 *@param utterance
	 *@return
	 *@throws Exception
	 *@return:String
	 *@trhows
	 */
	public String replyByBotApi(String utterance) throws Exception {
		String reply = "";
		String url = "http://api.qingyunke.com/api.php?key=free&appid=0&msg="+URLEncoder.encode(utterance,"UTF-8");
		try {
			String result = HttpUtil.sendGet(url, "utf-8");
			JSONObject json = JSONObject.fromObject(result);
			if(json.getInt("result")!=0) reply = safeReply();
			else reply = json.getString("content");
		}catch (Exception e) {
		}
		
		return reply;
	}
	
}
