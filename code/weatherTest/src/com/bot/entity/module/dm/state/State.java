/**
 * 
 */
package com.bot.entity.module.dm.state;

/**
 *@desc:״̬��
 *@author �˕D
 *@date:2020��2��10������7:28:57
 */
public class State {
	private StateContent stateContent = new StateContent();
	private int turnId;	//�ִκ�
	private int dialogId;	//�Ự���
	private int stateType;	//״̬����
	private int role;	//��ɫ
	
	//ϵͳ״̬��0~5
	public final static int STATE_START = 0;	//��ʼ
	public final static int STATE_END = 1;	//��������
	public final static int STATE_INTURRUPT = 2;	//�쳣��ֹ
	//���������ͣ�6~9
	public final static int STATE_TASK_BOT = 6;
	public final static int STATE_CHAT_BOT = 7;
	//��ɫ��10~
	public final static int ROLE_USER = 10;
	public final static int ROLE_BOT = 11;
	public final static int ROLE_SYS = 12;

	/**
	 * 
	 */
	public State() {
		this.turnId = 0;
		this.dialogId = 0;
		this.stateType = -1;
		this.role = -1;
	}
	
	public State(State state) {
		this.turnId = state.turnId;
		this.dialogId = state.dialogId;
		this.stateType = state.stateType;
		this.role = state.role;
		this.stateContent = state.getStateContent();
	}

	public StateContent getStateContent() {
		return stateContent;
	}

	public void setStateContent(StateContent stateContent) {
		this.stateContent = stateContent;
	}

	public int getTurnId() {
		return turnId;
	}

	public void setTurnId(int turnId) {
		this.turnId = turnId;
	}

	public int getDialogId() {
		return dialogId;
	}

	public void setDialogId(int dialogId) {
		this.dialogId = dialogId;
	}

	public int getStateType() {
		return stateType;
	}

	public void setStateType(int stateType) {
		this.stateType = stateType;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}
	
}
