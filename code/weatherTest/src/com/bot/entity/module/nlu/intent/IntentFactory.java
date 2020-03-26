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
		Intent intent = null;
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
		}
		
		return intent;
	}

}
