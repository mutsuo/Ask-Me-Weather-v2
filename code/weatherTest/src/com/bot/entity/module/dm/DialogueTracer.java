/**
 * 
 */
package com.bot.entity.module.dm;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bot.entity.module.dm.state.State;
import com.bot.entity.module.nlu.intent.Intent;
import com.bot.entity.module.nlu.slot.SlotDATE;
import com.bot.entity.util.StateStack;

/**
 *@desc:�Ի�����ģ�飬����ģʽ
 *@author �˕D
 *@date:2020��2��15������12:50:01
 */
public class DialogueTracer {
	private static DialogueTracer instance = new DialogueTracer();
	private StateStack stateStack = StateStack.getInstance();
	static Logger logger = Logger.getLogger(DialogueTracer.class);
	/**
	 * 
	 */
	private DialogueTracer() {
		// TODO Auto-generated constructor stub
	}
	
	public static DialogueTracer getInstance() {
		return instance;
	}
	
	/**
	 * 
	 *@desc:�жϻ����Ƿ�ı�
	 *<br>�ж����ݣ�1.���û������Ƿ�ı䣻2.��ͼ�Ƿ�ı䣻
	 *<br>true������ı䣻false�����ⲻ��
	 *@param intent
	 *@param slot
	 *@return:boolean
	 *@trhows
	 */
	public boolean isThemeChanged(int botType, Intent intent, int turnId) {
		boolean flag = false;
		if(stateStack.isEmpty() || turnId==0) {
			flag = false;
		}else {
			//�ҵ���һ����ϵͳ״̬
			int n = stateStack.size()-1;
			while(n>=0) {
				State state = stateStack.get(n);
				if(state.getStateType()!=State.STATE_START
						&& state.getStateType()!=State.STATE_END
						&& state.getStateType()!=State.STATE_INTURRUPT
						&& state.getRole()==State.ROLE_USER) {
					break;
				}
				n--;
			}
			if(n>=0) {
				State lastState = stateStack.get(n);
				stateStack.pushStateStack(lastState);
				
				if(botType==State.STATE_CHAT_BOT) {
					//����ǰ���õ������Ļ����ˣ�����һ�Ի����õ��������ͻ����ˡ�������ı�
					if(lastState.getStateType()!=State.STATE_CHAT_BOT) flag = true;
				}else if(botType==State.STATE_TASK_BOT && lastState.getStateType()==State.STATE_CHAT_BOT) {
					//����ǰ�������ͻ����ˣ������¶Ի����õ������Ļ����ˡ�������ı�
					flag = true;	
				}else {
					//����ǰ����һ״̬���õĶ��������ͻ����ˣ�����ͼ��ͬ��������ı�
					Intent lastIntent = lastState.getStateContent().getIntent();
					if(intent!=null && lastIntent!=null && !lastIntent.getIntentId().equals(intent.getIntentId())) {
						flag = true;
					}
				}				
			}
		}
		
		return flag;
	}
	
	/**
	 * 
	 *@desc:��������Ҫ���״̬
	 *<br>1. task-based�� 2. ���ղ�ѯ
	 *@return
	 *@return:State
	 *@trhows
	 */
	public State getLastStateContainComplicatedDATE() {
		State lastState = null;
		if(!stateStack.isEmpty()) {
			//�ҵ���һ����ϵͳ״̬
			int n = stateStack.size()-1;
			while(n>=0) {
				State state = stateStack.get(n);
				if(state.getRole()==State.ROLE_USER	&& state.getStateType()==State.STATE_TASK_BOT
						&& state.getStateContent().getSlot().getDate().size() == 1
						&& state.getStateContent().getSlot().getDate().get(0).get("start") != null
						&& state.getStateContent().getSlot().getDate().get(0).get("end") == null
						&& state.getStateContent().getSlot().getDate().get(0).get("start").isFilled()) {
					break;
				}
				n--;
			}
			if(n>=0) {
				logger.debug("last user state has been found");
				lastState = new State(stateStack.get(n));
//				List<Map<String, SlotDATE>> dateList = stateStack.get(n).getStateContent().getSlot().getDate();
//				if(dateList.size()==1
//						&& dateList.get(0).get("end")==null
//						&& dateList.get(0).get("start")!=null
//						&& dateList.get(0).get("start").isFilled()) {
//				}
			}
		}
		
		return lastState;
	}
	
	/**
	 * 
	 *@desc:������һ�����з���Ҫ��LOC����۵�״̬
	 *@return
	 *@return:State
	 *@trhows
	 */
	public State getLastStateContainComplicatedLOC() {
		State lastState = null;
		
		if(!stateStack.isEmpty()) {
			//�ҵ���һ����ϵͳ״̬
			int n = stateStack.size()-1;
			while(n>=0) {
				logger.debug("last user state has been found");
				State state = stateStack.get(n);
				if(state.getRole()==State.ROLE_USER	&& state.getStateType()==State.STATE_TASK_BOT
						&& !state.getStateContent().getSlot().getLoc().getName().equals("ROOT")) {
					break;
				}
				n--;
			}
			if(n>=0) {
				if(stateStack.get(n).getStateContent().getSlot().getLoc().isFilled()) {
					lastState = new State(stateStack.get(n));
				}
			}
		}
		
		return lastState;
	}
	
	public void addState(State state) {
		State newState = new State(state);
		this.stateStack.pushStateStack(newState);
	}

	/**
	 *@desc:һ�仰����
	 *@return
	 *@return:Object
	 *@trhows
	 */
	public Intent getLastIntent() {
		Intent lastIntent = null;
		if(!stateStack.isEmpty()) {
			//�ҵ���һ����ϵͳ״̬
			int n = stateStack.size()-1;
			while(n>=0) {
				State state = stateStack.get(n);
				if(state.getRole()==State.ROLE_USER	&& state.getStateType()==State.STATE_TASK_BOT) {
					break;
				}
				n--;
			}
			if(n>=0) {
				if(stateStack.get(n).getStateContent().getIntent()!=null) {
					lastIntent = stateStack.get(n).getStateContent().getIntent();
				}
			}
		}
		return lastIntent;
	}

	
	public State getLastUserState() {
		State lastState = null;
		
		if(!stateStack.isEmpty()) {
			//�ҵ���һ����ϵͳ״̬
			int n = stateStack.size()-1;
			while(n>=0) {
				State state = stateStack.get(n);
				if(state.getRole()==State.ROLE_USER) {
					break;
				}
				n--;
			}
			if(n>=0) lastState = new State(stateStack.get(n));
		}
		return lastState;
	}
}
