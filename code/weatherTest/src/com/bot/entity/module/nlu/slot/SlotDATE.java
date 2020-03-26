/**
 * 
 */
package com.bot.entity.module.nlu.slot;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *@desc:�������Ϣ
 *@author �˕D
 *@date:2020��2��6������5:56:19
 */
public class SlotDATE {
	private int day;
	private int month;
	private int year;
	private int startIndex;	//�������ԭʼ�����е���ʼλ��
	private int endIndex;	//�������ԭʼ�����еĽ���λ��
	private String rawString; //���ҽ��������Ϊ���ʱ�����ʱ�����ø�����
	
	public SlotDATE() {
		this.day = -1;
		this.month = -1;
		this.year = -1;
		this.startIndex = -1;
		this.endIndex = -1;
		this.rawString = "";
	}
	
	public SlotDATE(int day,int month,int year) {
		this.day = day;
		this.month = month;
		this.year = year;
		this.startIndex = -1;
		this.endIndex = -1;
		this.rawString = "";
	}
	
	public boolean isFilled() {
		boolean flag = false;
		if(this.day!=-1 && this.month!=-1 && this.year!=-1) {
			flag = true;
		}
			
		return flag;
	}
	
	/***
	 * 
	 *@desc:����ʾ���ڵ��ַ���ת��Ϊ������
	 *@param s
	 *@return
	 *@return:int
	 *@trhows
	 */
	public int toNum(String s) {
		int num = 0;
		for(int i = 0;i<s.length();i++) {
			if(s.charAt(i)>='0' && s.charAt(i)<='9') {
				num*=10;
				num+=s.charAt(i)-'0';
			}else {
				if(i>0 && s.charAt(i-1)!='ʮ') num*=10;
				switch(s.charAt(i)) {
				case 'һ':num+=1;break;
				case '��':num+=2;break;
				case '��':num+=3;break;
				case '��':num+=4;break;
				case '��':num+=5;break;
				case '��':num+=6;break;
				case '��':num+=7;break;
				case '��':num+=8;break;
				case '��':num+=9;break;
				case 'ʮ':if(i==0)num = 10;break;
				}				
			}
		}
		return num;
	}
	
	/***
	 * �ú������ڵ��ַ�����ʼ��
	 * @param rawDate
	 */
	public SlotDATE(String rawDate) {
		this.day = -1;
		this.month = -1;
		this.year = -1;
		
		String regex = "[�����պ�]|[\\.-/\\\\]";
		String[] dateElems = rawDate.split(regex);
		
		if(rawDate.indexOf("��")!=-1 || rawDate.indexOf("��")!=-1 || rawDate.indexOf("��")!=-1 || rawDate.indexOf("��")!=-1) {
			int index = 0;
			if(rawDate.indexOf("��")!=-1) {
				while(index<dateElems.length && dateElems[index]=="") index++;
				this.year = toNum(dateElems[index]);
				index++;
			}
			if(rawDate.indexOf("��")!=-1) {
				if(index>=dateElems.length) index = 0;
				while(index<dateElems.length && dateElems[index]=="") index++;
				this.month = toNum(dateElems[index]);
				index++;
			}
			if(rawDate.indexOf("��")!=-1 || rawDate.indexOf("��")!=-1) {
				if(index>=dateElems.length) index = 0;
				while(index<dateElems.length && dateElems[index]=="") index++;
				this.day = toNum(dateElems[index]);
				index++;
			}
			if(index<dateElems.length) {
				while(index<dateElems.length && dateElems[index]=="") index++;
				this.day = toNum(dateElems[index]);
			}
		}else {
			for(int i = dateElems.length-1 ; i >= 0; i--) {
				if(!dateElems[i].equals("")) {
					if(dateElems[i].length() < 4) {
						if(this.day==-1) {
							this.day = toNum(dateElems[i]);						
						}else if(this.month==-1) {
							this.month = toNum(dateElems[i]);
						}else if(this.year==-1){
							if(dateElems[i].length()==1) {
								this.year = toNum("200"+dateElems[i]);							
							}
							if(dateElems[i].length()==2) {
								this.year = toNum("20"+dateElems[i]);							
							}
							if(dateElems[i].length()==3) {
								this.year = toNum("2"+dateElems[i].substring(1, 3));
							}
						}
					}else{
						this.year = toNum(dateElems[i].substring(dateElems[i].length()-4,dateElems[i].length()));
					}
				}
			}
			
		}
		
		this.startIndex = -1;
		this.endIndex = -1;
		this.rawString = rawDate;
	}
	
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	
	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public int getEndIndex() {
		return endIndex;
	}

	public void setEndIndex(int endIndex) {
		this.endIndex = endIndex;
	}
	

	public String getRawString() {
		return rawString;
	}

	public void setRawString(String rawString) {
		this.rawString = rawString;
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + endIndex;
		result = prime * result + month;
		result = prime * result + ((rawString == null) ? 0 : rawString.hashCode());
		result = prime * result + startIndex;
		result = prime * result + year;
		return result;
	}

	/**
	 * ֻ�Ƚ�����
	 */
	public boolean equals(Object obj) {
		SlotDATE other = (SlotDATE) obj;
		if (day != other.day)
			return false;
//		if (endIndex != other.endIndex)
//			return false;
		if (month != other.month)
			return false;
//		if (rawString == null) {
//			if (other.rawString != null)
//				return false;
//		} else if (!rawString.equals(other.rawString))
//			return false;
//		if (startIndex != other.startIndex)
//			return false;
		if (year != other.year)
			return false;
		return true;
	}

	/**
	 * 
	 *@desc:�����yyyy-MM-dd����ʽ�������ַ���
	 *@return
	 *@return:String
	 *@trhows
	 */
	public String getFormatedDate() {
		String d = "";
		String m = "";
		
		if(this.day==-1) {
			d = "01";
		}else if(this.day < 10) {
			d = "0" + this.day;
		}else {
			d = "" + this.day;
		}
		
		if(this.month==-1) {
			m = "01";
		}else if(this.month < 10) {
			m = "0" + this.month;
		}else {
			m = "" + this.month;
		}
		
		return this.year + "-" + m + "-" + d;
	}
	
	/**
	 * 
	 *@desc:��������Ƿ�Ϻ���ʶ
	 *@return
	 *@return:boolean
	 *@trhows
	 */
	public boolean isLegal() {
		boolean flag = false;
		
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
		String[] nowDate = ft.format(dNow).split("-");
		
		//���ʱ���Ƿ�Ϻ���ʶ
		int[] daySum = {-1,31,-1,31,30,31,30,31,31,30,31,30,31};
		if(this.year%4==0 && this.year%100!=0 || this.year%400==0) {
			daySum[2] = 29;
		}else {
			daySum[2] = 28;
		}
		if(this.day>0 && this.month>=1 && this.month<=12 && this.year>0  
				&& daySum[this.month] >= this.day) {
			flag = true;
		}
		
		return flag;
	}

	/**
	 *@desc:�жϵ�ǰ�����Ƿ�����Ŀ������
	 *@param endDate
	 *@return
	 *@return:boolean
	 *@trhows
	 */
	public boolean isBefore(SlotDATE date) {
		boolean flag = false;
		if(this.day<date.day && this.month<date.month && this.year<date.year) flag = true;
		else if(this.year==date.year && this.day<date.day && this.month<date.month) flag = true;
		else if(this.year==date.year && this.month==date.month && this.day<date.day) flag = true;
		
		return flag;
	}
	
}
