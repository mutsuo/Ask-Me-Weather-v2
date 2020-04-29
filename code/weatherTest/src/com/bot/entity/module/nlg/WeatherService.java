/**
 * 
 */
package com.bot.entity.module.nlg;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bot.entity.module.nlu.intent.Intent;
import com.bot.entity.module.nlu.slot.MyDate;
import com.bot.entity.module.nlu.slot.Slot;
import com.bot.entity.module.nlu.slot.SlotDATE;
import com.bot.entity.util.HttpUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *@desc:������Ϣ��ѯ������ģʽ
 *@author �˕D
 *@date:2020��2��14������1:12:11
 */
public class WeatherService {
	private Logger logger = Logger.getLogger(WeatherService.class);
	private static final String JISU_APPKEY = "377115eba4920e4c";// ���appkey
    private static final String JISU_URL = "https://api.jisuapi.com/weather/query";
    
    private static final String XINZHITIANQI_URL_WEATHER = "https://api.seniverse.com/v3/weather/daily.json";
    private static final String XINZHITIANWI_URL_WEATHER_NOW = "https://api.seniverse.com/v3/weather/now.json";
    private static final String XINZHITIANQI_URL_LIFE = "https://api.seniverse.com/v3/life/suggestion.json";
    private static final String XINZHITIANQI_URL_RESTRICTION = "https://api.seniverse.com/v3/life/driving_restriction.json";
    private static final String XINZHITIANQI_APPKEY = "SFSUJpL2K0ZfWbSg0";
    
    private static WeatherService instance = new WeatherService();
    
    //����Դ
    static public final int JISU = 1;
    static public final int XIN_ZHI_TIAN_QI = 2;
    
    //-------��������-------
    //��������
  	static public final String JISU_WEATHER = "weather";
  	//���¡�
  	static public final String JISU_TEMP = "temp";
  	//�������
  	static public final String JISU_TEMP_HIGH = "temphigh";
  	//�������
  	static public final String JISU_TEMP_LOW = "templow";
  	//ʪ��
  	static public final String JISU_HUMIDITY = "humidity";
  	//��ѹ
  	static public final String JISU_PRESSURE = "pressure";
  	//����
  	static public final String JISU_WIND_SPEED = "windspeed";
  	//����
  	static public final String JISU_WIND_DIRECT = "winddirect";
  	//�缶
  	static public final String JISU_WIND_POWER = "windpower";
  	
  	//-------��֪����-------
  	//��������
  	static public final String XINZHITIANQI_WEATHER_DAY = "text_day";
  	static public final String XINZHITIANQI_WEATHER_NIGHT = "text_night";
  	static public final String XINZHITIANQI_WEATHER_NOW = "text";
  	//�������
  	static public final String XINZHITIANQI_TEMP_HIGH = "high";
  	//�������
  	static public final String XINZHITIANQI_TEMP_LOW = "low";
  	//��ǰ����
  	static public final String XINZHITIANQI_TEMP = "temperature";
  	//ʪ��
  	static public final String XINZHITIANQI_HUMIDITY = "humidity";
  	//����
  	static public final String XINZHITIANQI_WIND_SPEED = "wind_speed";
  	//����
  	static public final String XINZHITIANQI_WIND_DIRECT = "wind_direction_degree";
  	//�缶
  	static public final String XINZHITIANQI_WIND_POWER = "wind_scale";
  //�յ�ָ��
  	public static final String XINZHITIANQI_AC = "ac";
  	//������ɢ����
  	public static final String XINZHITIANQI_AIR_CONDITION = "air_pollution";
  	//��ɹ
  	public static final String XINZHITIANQI_AIRING = "airing";
  	//����
  	public static final String XINZHITIANQI_ALLERGY = "allergy";
  	//ơ��
  	public static final String XINZHITIANQI_BEER = "beer";
  	//����
  	public static final String XINZHITIANQI_BOATING = "boating";
  	//ϴ��
  	public static final String XINZHITIANQI_CAR_WASHING = "car_washing";
  	//�纮
  	public static final String XINZHITIANQI_CHILL = "chill";
  	//���ʶ�
  	public static final String XINZHITIANQI_COMFORT = "comfort";
  	//Լ��ָ��
  	public static final String XINZHITIANQI_DATING = "dating";
  	//����ָ��
  	public static final String XINZHITIANQI_DRESSING = "dressing";
  	//����ָ��
  	public static final String XINZHITIANQI_FISHING = "fishing";
  	//��ðָ��
  	public static final String XINZHITIANQI_FLU = "flu";
  	//����
  	public static final String XINZHITIANQI_HAIR_DRESSING = "hair_dressing";
  	//�ŷ���
  	public static final String XINZHITIANQI_KITE_FLYING = "kiteflying";
  	//��ױ
  	public static final String XINZHITIANQI_MAKEUP = "makeup";
  	//����
  	public static final String XINZHITIANQI_MOOD = "mood";
  	//����
  	public static final String XINZHITIANQI_MORNING_SPORT = "morning_sport";
  	//����
  	public static final String XINZHITIANQI_TRAVEL = "travel";
  	//��ɡ
  	public static final String XINZHITIANQI_UMBRALA = "umbrella";
  	//��ɹ
  	public static final String XINZHITIANQI_SUNSCREEN = "sunscreen";
  	//�˶�
  	public static final String XINZHITIANQI_SPORT = "sport";
  	//����
  	public static final String XINZHITIANQI_SHOPPING = "shopping";
  	
  	//����
  	public static final String XINZHITIANQI_RESTRICTION = "restriction";

	/**
	 * 
	 */
	private WeatherService() {
		// TODO Auto-generated constructor stub
	}
	
	public static WeatherService getInstance() {
		return instance;
	}
	
	public List<WeatherInfo> apiQuery(int src, Intent intent, Slot slot) throws Exception{
		List<WeatherInfo> list = null;
		switch(src) {
		case JISU:
			list = jisuQuery(intent, slot);
			break;
		case XIN_ZHI_TIAN_QI:
			list = xinZhiTianQiQuery(intent, slot);
			break;
		}
		
		return list;
	}

	public List<WeatherInfo> jisuQuery(Intent intent, Slot slot) throws Exception{
			List<WeatherInfo> list = null;
			
			String city = slot.getLoc().getName();
			String result = null;
			String url = JISU_URL + "?appkey=" + JISU_APPKEY + "&city=" + URLEncoder.encode(city, "utf-8");
			
			result = HttpUtil.sendGet(url, "utf-8");
			JSONObject json = JSONObject.fromObject(result);
			if (json.getInt("status") != 0) {
	//			map = new HashMap<String, Map<String, String>>();
			}else {
				list = new ArrayList<WeatherInfo>();
				JSONObject resultarr = json.optJSONObject("result");
				list = queryExtractionJISU(resultarr, slot);
			}
			
			return list;
		}

	/**
	 * 
	 *@desc:��ȡ��Ϣ
	 *@param exType
	 *@param resultarr
	 *@param slot
	 *@return
	 *@return:Map<String,Map<String,String>> ���ڣ�(key,value)
	 *@trhows
	 */
	public List<WeatherInfo> queryExtractionJISU(JSONObject resultarr, Slot slot){
		Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
		List<Map<String, SlotDATE>> dateList = slot.getDate();
		List<WeatherInfo> list = new ArrayList<WeatherInfo>();
		//��ѯ��������
		if(dateList.size()==1 && dateList.get(0).get("end")==null
				&& dateList.get(0).get("start").getFormatedDate().equals(resultarr.getString("date"))) {
//			Map<String, String> tMap = new HashMap<String, String>();
			WeatherInfo wi = new WeatherInfo();
			MyDate date = new MyDate();
			date.setDay(new Integer(resultarr.getString("date").split("-")[2]));
			date.setMonth(new Integer(resultarr.getString("date").split("-")[1]));
			date.setYear(new Integer(resultarr.getString("date").split("-")[0]));
			wi.setDate(date);
			wi.setWeather(resultarr.getString(JISU_WEATHER));
			wi.setMaxTem(resultarr.getString(JISU_TEMP_HIGH));
			wi.setMinTem(resultarr.getString(JISU_TEMP_LOW));
			wi.setNowTem(resultarr.getString(JISU_TEMP));
			wi.getIndex().put("�յ�ָ��", ((JSONObject)resultarr.optJSONArray("index").get(0)).getString("detail"));
			wi.getIndex().put("�˶�ָ��", ((JSONObject)resultarr.optJSONArray("index").get(1)).getString("detail"));
			wi.getIndex().put("������ָ��", ((JSONObject)resultarr.optJSONArray("index").get(2)).getString("detail"));
			wi.getIndex().put("��ðָ��", ((JSONObject)resultarr.optJSONArray("index").get(3)).getString("detail"));
			wi.getIndex().put("ϴ��ָ��", ((JSONObject)resultarr.optJSONArray("index").get(4)).getString("detail"));
			wi.getIndex().put("������Ⱦ��ɢָ��", ((JSONObject)resultarr.optJSONArray("index").get(5)).getString("detail"));
			wi.getIndex().put("����ָ��", ((JSONObject)resultarr.optJSONArray("index").get(6)).getString("detail"));
			list.add(wi);
		}
		//��ѯ�������� �� �ǽ��յ�������
		else if (resultarr.opt("daily") != null) {
			JSONArray daily = resultarr.optJSONArray("daily");
			String[] todayDate = ((JSONObject)daily.opt(0)).getString("date").split("-");
			for(Map<String, SlotDATE> dateMap: dateList) {
				//ʱ���
				if(dateMap.get("start")!=null && dateMap.get("end")==null) {
					//����index
					String[] tDate = dateMap.get("start").getFormatedDate().split("-");
					int delt = Integer.parseInt(tDate[2]) - Integer.parseInt(todayDate[2]);
					JSONObject obj = (JSONObject) daily.opt(delt);
					
//					Map<String, String> tMap = new HashMap<String, String>();
					WeatherInfo wi = new WeatherInfo();
					MyDate date = new MyDate();
					date.setDay(new Integer(obj.getString("date").split("-")[2]));
					date.setMonth(new Integer(obj.getString("date").split("-")[1]));
					date.setYear(new Integer(obj.getString("date").split("-")[0]));
					wi.setDate(date);
					
					wi.initDay();
					wi.getDay().setWeather(((JSONObject)obj.opt("day")).getString(JISU_WEATHER));
					wi.getDay().setMaxTem(((JSONObject)obj.opt("day")).getString(JISU_TEMP_HIGH));
					wi.getDay().setWindDir(((JSONObject)obj.opt("day")).getString(JISU_WIND_DIRECT));
					wi.getDay().setWindPower(((JSONObject)obj.opt("day")).getString(JISU_WIND_POWER));
//					wi.getDay().setWindSpeed(((JSONObject)obj.opt("day")).getString(JISU_WIND_SPEED));
					
					wi.initNight();
					wi.getNight().setWeather(((JSONObject)obj.opt("night")).getString(JISU_WEATHER));
					wi.getNight().setMinTem(((JSONObject)obj.opt("night")).getString(JISU_TEMP_LOW));
					wi.getNight().setWindDir(((JSONObject)obj.opt("night")).getString(JISU_WIND_DIRECT));
					wi.getNight().setWindPower(((JSONObject)obj.opt("night")).getString(JISU_WIND_POWER));
//					wi.getNight().setWindSpeed(((JSONObject)obj.opt("night")).getString(JISU_WIND_SPEED));
					
					list.add(wi);
//					
//					if (obj.opt("day") != null) {
//						if(exType.equals(Intent.WEATHER)) {
//							tMap.put("weather_day", ((JSONObject)obj.opt("day")).getString("weather"));
//							tMap.put("templow_day", ((JSONObject)obj.opt("day")).getString("temphigh"));							
//						}
//						else if(exType.equals(Intent.TEMPERATURE)) {
//							tMap.put("templow_day", ((JSONObject)obj.opt("day")).getString("temphigh"));							
//						}else if(exType.equals(Intent.TEMPERATURE_RANGE)){
//							tMap.put("tempRange", ((JSONObject)obj.opt("day")).getString("temphigh"));
//						}else {
//							
//						}
//					}
//					if (obj.opt("night") != null) {
//						if(exType.equals(Intent.WEATHER)) {
//							tMap.put("weather_night", ((JSONObject)obj.opt("night")).getString("weather"));
//							tMap.put("templow_night", ((JSONObject)obj.opt("night")).getString("templow"));
//						}
//						else if(exType.equals(Intent.TEMPERATURE)) {
//							tMap.put("templow_night", ((JSONObject)obj.opt("night")).getString("templow"));							
//						}else if(exType.equals(Intent.TEMPERATURE_RANGE)){
//							if(tMap.get("tempRange")!=null) {
//								int dayTemp = Integer.parseInt(tMap.get("tempRange"));
//								int nightTemp = Integer.parseInt(((JSONObject)obj.opt("night")).getString("templow"));
//								tMap.put("tempRange", ""+(dayTemp>nightTemp?dayTemp-nightTemp:nightTemp-dayTemp));								
//							}else {
//								tMap.put("tempRange", ((JSONObject)obj.opt("night")).getString("templow"));
//							}
//						}else {
//							
//						}
//					}
//					map.put(dateMap.get("start").getFormatedDate(), tMap);
				}
				//ʱ���
				else if(dateMap.get("start")!=null && dateMap.get("end")!=null) {
					int startIndex = Integer.parseInt(dateMap.get("start").getFormatedDate().split("-")[2])
										- Integer.parseInt(todayDate[2]);
					int endIndex = Integer.parseInt(dateMap.get("end").getFormatedDate().split("-")[2])
							- Integer.parseInt(todayDate[2]);
					for(int i = startIndex;i<=endIndex;i++) {
						JSONObject obj = (JSONObject) daily.opt(i);
//						Map<String, String> tMap = new HashMap<String, String>();
						WeatherInfo wi = new WeatherInfo();
						
						MyDate date = new MyDate();
						date.setDay(new Integer(obj.getString("date").split("-")[2]));
						date.setMonth(new Integer(obj.getString("date").split("-")[1]));
						date.setYear(new Integer(obj.getString("date").split("-")[0]));
						wi.setDate(date);
						
						wi.initDay();
						wi.getDay().setWeather(((JSONObject)obj.opt("day")).getString(JISU_WEATHER));
						wi.getDay().setMaxTem(((JSONObject)obj.opt("day")).getString(JISU_TEMP_HIGH));
						wi.getDay().setWindDir(((JSONObject)obj.opt("day")).getString(JISU_WIND_DIRECT));
						wi.getDay().setWindPower(((JSONObject)obj.opt("day")).getString(JISU_WIND_POWER));
//						wi.getDay().setWindSpeed(((JSONObject)obj.opt("day")).getString(JISU_WIND_SPEED));
						
						wi.initNight();
						wi.getNight().setWeather(((JSONObject)obj.opt("night")).getString(JISU_WEATHER));
						wi.getNight().setMinTem(((JSONObject)obj.opt("night")).getString(JISU_TEMP_LOW));
						wi.getNight().setWindDir(((JSONObject)obj.opt("night")).getString(JISU_WIND_DIRECT));
						wi.getNight().setWindPower(((JSONObject)obj.opt("night")).getString(JISU_WIND_POWER));
//						wi.getNight().setWindSpeed(((JSONObject)obj.opt("night")).getString(JISU_WIND_SPEED));
						
						list.add(wi);
						
//						if (obj.opt("day") != null) {
//							if(exType.equals(Intent.WEATHER)) {
//								tMap.put("weather_day", ((JSONObject)obj.opt("day")).getString("weather"));
//								tMap.put("templow_day", ((JSONObject)obj.opt("day")).getString("temphigh"));							
//							}
//							else if(exType.equals(Intent.TEMPERATURE)) {
//								tMap.put("templow_day", ((JSONObject)obj.opt("day")).getString("temphigh"));							
//							}else if(exType.equals(Intent.TEMPERATURE_RANGE)){
//								tMap.put("tempRange", ((JSONObject)obj.opt("day")).getString("temphigh"));
//							}else {
//								
//							}
//						}
//						if (obj.opt("night") != null) {
//							if(exType.equals(Intent.WEATHER)) {
//								tMap.put("weather_night", ((JSONObject)obj.opt("night")).getString("weather"));
//								tMap.put("templow_night", ((JSONObject)obj.opt("night")).getString("templow"));
//							}
//							else if(exType.equals(Intent.TEMPERATURE)) {
//								tMap.put("templow_night", ((JSONObject)obj.opt("night")).getString("templow"));							
//							}else if(exType.equals(Intent.TEMPERATURE_RANGE)){
//								if(tMap.get("tempRange")!=null) {
//									int dayTemp = Integer.parseInt(tMap.get("tempRange"));
//									int nightTemp = Integer.parseInt(((JSONObject)obj.opt("night")).getString("templow"));
//									tMap.put("tempRange", ""+(dayTemp>nightTemp?dayTemp-nightTemp:nightTemp-dayTemp));								
//								}else {
//									tMap.put("tempRange", ((JSONObject)obj.opt("night")).getString("templow"));
//								}
//							}else {
//								
//							}
//						}
//    					map.put(obj.getString("date"), tMap);
					}
				}
			}
			
		}
		return list;
	}
	/**
	 *@desc:һ�仰����
	 *@param intent
	 *@param slot
	 *@return
	 *@return:List<WeatherInfo>
	 * @throws Exception 
	 *@trhows
	 */
	public List<WeatherInfo> xinZhiTianQiQuery(Intent intent, Slot slot) throws Exception {
		List<WeatherInfo> list = null;
		
		String city = slot.getLoc().getName();
		String result = null;
		
		StringBuffer url = new StringBuffer();
		if(intent.getIntentId().charAt(0)=='W') {
			url.append(XINZHITIANQI_URL_WEATHER);
		}else if(intent.getIntentId().charAt(0)=='L') {
			if(slot.getDate().size()==1) {
				String date[] = slot.getDate().get(0).get("start").getFormatedDate().split("-");
				SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
				String todayDate[] = fm.format(new Date()).split("-");
				if(date[0].equals(todayDate[0]) && date[1].equals(todayDate[1]) && date[2].equals(todayDate[2]))
					url.append(XINZHITIANQI_URL_LIFE);
			}
		}else if(intent.getIntentId().charAt(0)=='X') {
			url.append(XINZHITIANQI_URL_RESTRICTION);
		}
		url.append("?key=");
		url.append(XINZHITIANQI_APPKEY);
		url.append("&location=");
		url.append(URLEncoder.encode(city, "utf-8"));
		
		result = HttpUtil.sendGet(url.toString(), "utf-8");
		logger.debug(result);
		JSONObject json = JSONObject.fromObject(result);
		
		list = new ArrayList<WeatherInfo>();
		JSONArray resultarr = JSONArray.fromObject(json.getString("results"));
		
		JSONArray res2 = null;
		if(intent.getIntentId().charAt(0)=='W') {
			String[] firstDate = slot.getDate().get(0).get("start").getFormatedDate().split("-");
			SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
			String[] todayDate = fm.format(new Date()).split("-");
			if(firstDate[0].equals(todayDate[0]) && firstDate[1].equals(todayDate[1]) && firstDate[2].equals(todayDate[2])) {
				url = new StringBuffer();
				url.append(this.XINZHITIANWI_URL_WEATHER_NOW);
				url.append("?key=");
				url.append(XINZHITIANQI_APPKEY);
				url.append("&location=");
				url.append(URLEncoder.encode(city, "utf-8"));
				result = HttpUtil.sendGet(url.toString(), "utf-8");
				logger.debug(result);
				
				list = new ArrayList<WeatherInfo>();
				res2 = JSONArray.fromObject(JSONObject.fromObject(result).getString("results"));
			}
		}
		
		list = queryExtractionXINZHITIANQI(resultarr, res2, intent, slot);
					
		
		
		return list;
	}

	/**
	 *@desc:һ�仰����
	 *@param resultarr
	 *@param slot
	 *@return
	 *@return:List<WeatherInfo>
	 *@trhows
	 */
	private List<WeatherInfo> queryExtractionXINZHITIANQI(JSONArray json, JSONArray json2, Intent intent, Slot slot) {
		List<WeatherInfo> list = new ArrayList<WeatherInfo>();
		
		if(intent.getIntentId().charAt(0)=='W') {
			List<Map<String, SlotDATE>> dateList = slot.getDate();
			JSONArray resultarr = JSONArray.fromObject(((JSONObject)json.get(0)).getString("daily"));
			JSONObject now = null;
			if(json2!=null) now = JSONObject.fromObject(((JSONObject)json2.get(0)).getString("now"));
			//��ѯ��������
			JSONObject day1 = (JSONObject) resultarr.opt(0);
			String[] todayDate = day1.getString("date").split("-");
			for(Map<String, SlotDATE> dateMap: dateList) {
				//ʱ���
				if(dateMap.get("start")!=null && dateMap.get("end")==null) {
					//����index
					String[] tDate = dateMap.get("start").getFormatedDate().split("-");
					int delt = Integer.parseInt(tDate[2]) - Integer.parseInt(todayDate[2]);
					JSONObject obj  = (JSONObject) resultarr.opt(delt);
					logger.debug(obj.toString());
					if(obj!=null) {
						WeatherInfo wi = new WeatherInfo();
						MyDate date = new MyDate();
						if(delt==0) {
							date.setDay(Integer.parseInt(todayDate[2]));
							date.setMonth(Integer.parseInt(todayDate[1]));
							date.setYear(Integer.parseInt(todayDate[0]));
							wi.setWeather(now.getString(XINZHITIANQI_WEATHER_NOW));
							wi.setNowTem(now.getString(XINZHITIANQI_TEMP));
							
						}else {
							date.setDay(new Integer(obj.getString("date").split("-")[2]));
							date.setMonth(new Integer(obj.getString("date").split("-")[1]));
							date.setYear(new Integer(obj.getString("date").split("-")[0]));
						}
						wi.setDate(date);
						wi.initDay();
						wi.getDay().setWeather(obj.getString(XINZHITIANQI_WEATHER_DAY));
						wi.initNight();
						wi.getNight().setWeather(obj.getString(XINZHITIANQI_WEATHER_NIGHT));
						wi.setMaxTem(obj.getString(XINZHITIANQI_TEMP_HIGH));
						wi.setMinTem(obj.getString(XINZHITIANQI_TEMP_LOW));
						
						wi.setWindDir(obj.getString(XINZHITIANQI_WIND_DIRECT));
						wi.setWindPower(obj.getString(XINZHITIANQI_WIND_POWER));
						wi.setWindSpeed(obj.getString(XINZHITIANQI_WIND_SPEED));
						
						list.add(wi);
						
					}
				}
				//ʱ���
				else if(dateMap.get("start")!=null && dateMap.get("end")!=null) {
					int startIndex = Integer.parseInt(dateMap.get("start").getFormatedDate().split("-")[2])
							- Integer.parseInt(todayDate[2]);
					int endIndex = Integer.parseInt(dateMap.get("end").getFormatedDate().split("-")[2])
							- Integer.parseInt(todayDate[2]);
					for(int i = startIndex;i<=endIndex;i++) {
						JSONObject obj = (JSONObject) resultarr.opt(i);
						WeatherInfo wi = new WeatherInfo();
						
						MyDate date = new MyDate();
						date.setDay(new Integer(obj.getString("date").split("-")[2]));
						date.setMonth(new Integer(obj.getString("date").split("-")[1]));
						date.setYear(new Integer(obj.getString("date").split("-")[0]));
						wi.setDate(date);
						
						wi.initDay();
						wi.getDay().setWeather(day1.getString(XINZHITIANQI_WEATHER_DAY));
						wi.initNight();
						wi.getNight().setWeather(day1.getString(XINZHITIANQI_WEATHER_NIGHT));
						wi.setMaxTem(day1.getString(JISU_TEMP_HIGH));
						wi.setMinTem(day1.getString(JISU_TEMP_LOW));
						wi.setNowTem(day1.getString(JISU_TEMP));
						wi.setWindDir(day1.getString(XINZHITIANQI_WIND_DIRECT));
						wi.setWindPower(day1.getString(XINZHITIANQI_WIND_POWER));
						wi.setWindSpeed(day1.getString(XINZHITIANQI_WIND_SPEED));
						
						list.add(wi);
					}
				}
			}
		}//--Weather End--
		else if(intent.getIntentId().charAt(0)=='L') {
			List<Map<String, SlotDATE>> dateList = slot.getDate();
			WeatherInfo wi = new WeatherInfo();
			wi.setDate(slot.getDate().get(0).get("start").getFormatedDate());
			
			JSONObject suggestion = JSONObject.fromObject(((JSONObject)json.get(0)).getString("suggestion"));
			JSONObject res = null;
			
			if(intent.getIntentId().equals(Intent.LIFE_AC)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_AC));
			}else if(intent.getIntentId().equals(Intent.LIFE_AIR_CONDITION)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_AIR_CONDITION));
			}else if(intent.getIntentId().equals(Intent.LIFE_AIRING)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_AIRING));
			}else if(intent.getIntentId().equals(Intent.LIFE_ALLERGY)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_ALLERGY));
			}else if(intent.getIntentId().equals(Intent.LIFE_BEER)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_BEER));
			}else if(intent.getIntentId().equals(Intent.LIFE_BOATING)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_BOATING));
			}else if(intent.getIntentId().equals(Intent.LIFE_CAR_WASHING)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_CAR_WASHING));
			}else if(intent.getIntentId().equals(Intent.LIFE_CHILL)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_CHILL));
			}else if(intent.getIntentId().equals(Intent.LIFE_COMFORT)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_COMFORT));
			}else if(intent.getIntentId().equals(Intent.LIFE_DATING)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_DATING));
			}else if(intent.getIntentId().equals(Intent.LIFE_DRESSING)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_DRESSING));
			}else if(intent.getIntentId().equals(Intent.LIFE_FISHING)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_FISHING));
			}else if(intent.getIntentId().equals(Intent.LIFE_FLU)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_FLU));
			}else if(intent.getIntentId().equals(Intent.LIFE_KITE_FLYING)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_KITE_FLYING));
			}else if(intent.getIntentId().equals(Intent.LIFE_MAKEUP)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_MAKEUP));
			}else if(intent.getIntentId().equals(Intent.LIFE_MOOD)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_MOOD));
			}else if(intent.getIntentId().equals(Intent.LIFE_MORNING_SPORT)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_SPORT));
			}else if(intent.getIntentId().equals(Intent.LIFE_TRAVEL)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_TRAVEL));
			}else if(intent.getIntentId().equals(Intent.LIFE_UMBRALA)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_UMBRALA));
			}else if(intent.getIntentId().equals(Intent.LIFE_SUNSCREEN)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_SUNSCREEN));
			}else if(intent.getIntentId().equals(Intent.LIFE_SPORT)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_SPORT));
			}else if(intent.getIntentId().equals(Intent.LIFE_SHOPPING)) {
				res = JSONObject.fromObject(suggestion.getString(XINZHITIANQI_SHOPPING));
			}
			
			wi.getIndex().put("brief", res.getString("brief"));
			wi.getIndex().put("detail", res.getString("details"));
			list.add(wi);
		}//--Life Inde End--
		else if(intent.getIntentId().charAt(0)=='X') {
			List<Map<String, SlotDATE>> dateList = slot.getDate();
			JSONObject restriction = JSONObject.fromObject(((JSONObject)json.get(0)).getString("restriction"));
			JSONArray limits = JSONArray.fromObject(restriction.get("limits"));
			if(limits.toArray().length > 1) {
				//��ѯ��������
				JSONObject day1 = (JSONObject) limits.opt(0);
				String[] todayDate = day1.getString("date").split("-");
				for(Map<String, SlotDATE> dateMap: dateList) {
					//ʱ���
					if(dateMap.get("start")!=null && dateMap.get("end")==null) {
						//����index
						String[] tDate = dateMap.get("start").getFormatedDate().split("-");
						int delt = Integer.parseInt(tDate[2]) - Integer.parseInt(todayDate[2]);
						JSONObject obj = (JSONObject) limits.opt(delt);
						if(delt<=2) {
							logger.debug(obj.toString());
							if(obj!=null) {
								WeatherInfo wi = new WeatherInfo();
								MyDate date = new MyDate();
								date.setDay(new Integer(obj.getString("date").split("-")[2]));
								date.setMonth(new Integer(obj.getString("date").split("-")[1]));
								date.setYear(new Integer(obj.getString("date").split("-")[0]));
								wi.setDate(date);
								
								wi.getRestriction().put("penalty", restriction.getString("penalty"));
								wi.getRestriction().put("region", restriction.getString("region"));
								wi.getRestriction().put("remarks", restriction.getString("remarks"));
								wi.getRestriction().put("memo", obj.getString("memo"));
								
								String plateS = "";
								Object[] plates = JSONArray.fromObject(obj.get("plates")).toArray();
								boolean flag = true;
								for(Object p: plates) {
									if(flag) flag = false;
									else plateS += "��";
									plateS += p;
								}
								wi.getRestriction().put("plate", plateS);
								
								list.add(wi);
								
							}
						}
					}
					//ʱ���
					else if(dateMap.get("start")!=null && dateMap.get("end")!=null) {
						int startIndex = Integer.parseInt(dateMap.get("start").getFormatedDate().split("-")[2])
								- Integer.parseInt(todayDate[2]);
						int endIndex = Integer.parseInt(dateMap.get("end").getFormatedDate().split("-")[2])
								- Integer.parseInt(todayDate[2]);
						if(endIndex> 2 ) endIndex = 2;
						for(int i = startIndex;i<=endIndex;i++) {
							JSONObject obj = (JSONObject) limits.opt(i);
							if(obj!=null) {
								WeatherInfo wi = new WeatherInfo();
								MyDate date = new MyDate();
								date.setDay(new Integer(obj.getString("date").split("-")[2]));
								date.setMonth(new Integer(obj.getString("date").split("-")[1]));
								date.setYear(new Integer(obj.getString("date").split("-")[0]));
								wi.setDate(date);
								
								wi.getRestriction().put("penalty", restriction.getString("penalty"));
								wi.getRestriction().put("region", restriction.getString("region"));
								wi.getRestriction().put("remarks", restriction.getString("remarks"));
								wi.getRestriction().put("memo", obj.getString("memo"));
								
								String plateS = "";
								String[] plates = (String[]) JSONArray.fromObject(obj.get("plates")).toArray();
								boolean flag = true;
								for(String p: plates) {
									if(flag) flag = false;
									else plateS += "��";
									plateS += p;
								}
								wi.getRestriction().put("plate", plateS);
								
								list.add(wi);
								
							}
						}
					}
				}
			}
			
			
		}//--Car Resrtiction End--
		return list;
	}

}
