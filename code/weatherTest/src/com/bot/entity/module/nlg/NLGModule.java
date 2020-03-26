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
		}
		
		return recen;
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
		public List<String> taskBasedCommonReply(Intent intent, Slot slot) throws Exception {
			WeatherService service = WeatherService.getInstance();
			Map<String, Map<String, String>> map = service.apiQuery(intent, slot);
			
			List<String> replys = new ArrayList<String>();
			Random rand = new Random();
			for(Entry<String, Map<String, String>> entry: map.entrySet()) {
				String date = isRecent(entry.getKey());
				if(date.equals("") && entry.getKey()!=null) {
					String[] d = entry.getKey().split("-"); 
					date = d[1]+"��"+d[2]+"��";
				}
				
				String loc = slot.getLoc().getName();
				
				String weather = "";
				if(entry.getValue().get("weather")!=null) weather = entry.getValue().get("weather");
				else{
					if(entry.getValue().get("weather_day")!=null && entry.getValue().get("weather_night")!=null) {
						weather = entry.getValue().get("weather_day") + "ת" +entry.getValue().get("weather_night");					
					}else {
						if(entry.getValue().get("weather_day")!=null) weather = entry.getValue().get("weather_day");
						if(entry.getValue().get("weather_night")!=null) weather = entry.getValue().get("weather_night");
					}
				}
				
				String tempLow = "";
				if(entry.getValue().get("templow")!=null) tempLow = entry.getValue().get("templow");
							
				String tempHigh = "";
				if(entry.getValue().get("temphigh")!=null) tempHigh = entry.getValue().get("templow");
				
				String tempDay = "";
				if(entry.getValue().get("templow_day")!=null) tempDay = entry.getValue().get("templow_day");
				
				String tempNight = "";
				if(entry.getValue().get("templow_night")!=null) tempNight = entry.getValue().get("templow_night");
				
				String index = "";
				if(entry.getValue().get("index")!=null) index = entry.getValue().get("index");
				
				StringBuffer buffer = new StringBuffer();
				buffer.append(loc);
				buffer.append(date);
				buffer.append(weather);
				buffer.append(",");
				if(!tempHigh.equals("") && !tempLow.equals("")) {
					buffer.append(tempLow);
					buffer.append("�浽");
					buffer.append(tempHigh);
					buffer.append("��");
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
						}
					}
				}
				buffer.append("\n");
				if(!index.equals("")) buffer.append(index);
				
				replys.add(buffer.toString());
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
