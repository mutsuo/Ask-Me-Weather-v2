/**
 * 
 */
package com.bot.entity.module.nlu.intent;

/**
 *@desc:��ͼ�����࣬����ģʽ
 *@author �˕D
 *@date:2020��2��13������5:33:49
 */
public class IntentFactory {
	private static IntentFactory instance = new IntentFactory();
	/**
	 * 
	 */
	private IntentFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static IntentFactory getInstance() {
		return instance;
	}
	
	public Intent getIntent(String id) {
		if(id.equals("")) return null;
		Intent intent = new Intent();
		intent.setIntentId(id);
		if(id.equals(Intent.WEATHER)) {
			intent = new IntentWeather();
			intent.setIntentId(Intent.WEATHER);
			intent.setIntentName("����");
		}
		else if(id.equals(Intent.TEMPERATURE)) {
			intent = new IntentTemperature();
			intent.setIntentId(Intent.TEMPERATURE);
			intent.setIntentName("�¶�");
		}
		else if(id.equals(Intent.TEMPERATURE_RANGE)) {
			intent = new IntentTempRange();
			intent.setIntentId(Intent.TEMPERATURE_RANGE);
			intent.setIntentName("�²�");
		}
		else if(id.equals(Intent.HUMIDITY)) {
			intent = new IntentHumidity();
			intent.setIntentId(Intent.HUMIDITY);
			intent.setIntentName("ʪ��");
		}
		else if(id.equals(Intent.VISIBILITY)) {
			intent = new IntentVisibility();
			intent.setIntentId(Intent.VISIBILITY);
			intent.setIntentName("�ܼ���");
		}else if(id.equals(Intent.WIND_DIR)) {
//			intent = new Intent();
//			intent.setIntentId(id);
			intent.setIntentName("����");
		}else if(id.equals(Intent.WIND_POWER)) {
			intent.setIntentName("����");
		}else if(id.equals(Intent.LIFE_AC)) {
			intent.setIntentName("�յ�ָ��");
		}else if(id.equals(Intent.LIFE_AIR_CONDITION)) {
			intent.setIntentName("������ɢ����");
		}else if(id.equals(Intent.LIFE_AIRING)) {
			intent.setIntentName("��ɹָ��");
		}else if(id.equals(Intent.LIFE_ALLERGY)) {
			intent.setIntentName("����ָ��");
		}else if(id.equals(Intent.LIFE_BEER)) {
			intent.setIntentName("ơ��ָ��");
		}else if(id.equals(Intent.LIFE_BOATING)) {
			intent.setIntentName("����ָ��");
		}else if(id.equals(Intent.LIFE_CAR_WASHING)) {
			intent.setIntentName("ϴ��ָ��");
		}else if(id.equals(Intent.LIFE_CHILL)) {
			intent.setIntentName("�纮ָ��");
		}else if(id.equals(Intent.LIFE_COMFORT)) {
			intent.setIntentName("���ʶ�");
		}else if(id.equals(Intent.LIFE_DATING)) {
			intent.setIntentName("Լ��ָ��");
		}else if(id.equals(Intent.LIFE_DRESSING)) {
			intent.setIntentName("����ָ��");
		}else if(id.equals(Intent.LIFE_FISHING)) {
			intent.setIntentName("����ָ��");
		}else if(id.equals(Intent.LIFE_FLU)) {
			intent.setIntentName("��ðָ��");
		}else if(id.equals(Intent.LIFE_HAIR_DRESSING)) {
			intent.setIntentName("����ָ��");
		}else if(id.equals(Intent.LIFE_KITE_FLYING)) {
			intent.setIntentName("�ŷ���");
		}else if(id.equals(Intent.LIFE_MAKEUP)) {
			intent.setIntentName("��ױָ��");
		}else if(id.equals(Intent.LIFE_MOOD)) {
			intent.setIntentName("����ָ��");
		}else if(id.equals(Intent.LIFE_MORNING_SPORT)) {
			intent.setIntentName("����ָ��");
		}else if(id.equals(Intent.LIFE_TRAVEL)) {
			intent.setIntentName("����ָ��");
		}else if(id.equals(Intent.LIFE_UMBRALA)) {
			intent.setIntentName("��ɡָ��");
		}else if(id.equals(Intent.LIFE_SUNSCREEN)) {
			intent.setIntentName("��ɹָ��");
		}else if(id.equals(Intent.LIFE_SPORT)) {
			intent.setIntentName("�˶�ָ��");
		}else if(id.equals(Intent.LIFE_SHOPPING)) {
			intent.setIntentName("����ָ��");
		}else if(id.equals(Intent.LIFE_RESTRICTION)) {
			intent.setIntentName("��������");
		}
		
		return intent;
	}

}
