/**
 * 
 */
package com.bot.entity.module.nlu.intent;

/**
 *@desc:��ͼ�ӿ���
 *@author �˕D
 *@date:2020��2��6������4:32:38
 */
public class Intent {
	//1. ������
	public static final String WEATHER = "W01";
	public static final String TEMPERATURE = "W02";
	public static final String TEMPERATURE_RANGE = "W03";
	public static final String HUMIDITY = "W04";
	public static final String VISIBILITY = "W05";
	public static final String WIND_DIR = "W06";
	public static final String WIND_SPEED = "W07";
	public static final String WIND_POWER = "W08";
	
	//2. ������
	//2-1. ����ָ��
	//�յ�ָ��
	public static final String LIFE_AC = "L01";
	//������ɢ����
	public static final String LIFE_AIR_CONDITION = "L02";
	//��ɹ
	public static final String LIFE_AIRING = "L03";
	//����
	public static final String LIFE_ALLERGY = "L04";
	//ơ��
	public static final String LIFE_BEER = "L05";
	//����
	public static final String LIFE_BOATING = "L06";
	//ϴ��
	public static final String LIFE_CAR_WASHING = "L07";
	//�纮
	public static final String LIFE_CHILL = "L08";
	//���ʶ�
	public static final String LIFE_COMFORT = "L09";
	//Լ��ָ��
	public static final String LIFE_DATING = "L10";
	//����ָ��
	public static final String LIFE_DRESSING = "L11";
	//����ָ��
	public static final String LIFE_FISHING = "L12";
	//��ðָ��
	public static final String LIFE_FLU = "L13";
	//����
	public static final String LIFE_HAIR_DRESSING = "L14";
	//�ŷ���
	public static final String LIFE_KITE_FLYING = "L15";
	//��ױ
	public static final String LIFE_MAKEUP = "L16";
	//����
	public static final String LIFE_MOOD = "L17";
	//����
	public static final String LIFE_MORNING_SPORT = "L18";
	//����
	public static final String LIFE_TRAVEL = "L19";
	//��ɡ
	public static final String LIFE_UMBRALA = "L20";
	//��ɹ
	public static final String LIFE_SUNSCREEN = "L21";
	//�˶�
	public static final String LIFE_SPORT = "L22";
	//����
	public static final String LIFE_SHOPPING = "L23";
	
	//����
	public static final String LIFE_RESTRICTION = "X0";
	
	private String intentName;
	private String intentId;
	
	public Intent() {
		
	}
	
	public String generateReply() {
		return null;
	}

	public String getIntentName() {
		return intentName;
	}

	public String getIntentId() {
		return intentId;
	}

	public void setIntentName(String intentName) {
		this.intentName = intentName;
	}

	public void setIntentId(String intentId) {
		this.intentId = intentId;
	}
	
	
}
