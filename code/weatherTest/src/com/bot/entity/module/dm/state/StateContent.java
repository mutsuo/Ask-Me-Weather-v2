/**
 * 
 */
package com.bot.entity.module.dm.state;

import com.bot.entity.module.nlu.intent.Intent;
import com.bot.entity.module.nlu.slot.Slot;

/**
 *@desc:״̬��Ϣ��
 *@author �˕D
 *@date:2020��2��10������7:28:49
 */
public class StateContent {
	private String rawUtterance = "";
	private Slot slot;
	private Intent intent;

	/**
	 * 
	 */
	public StateContent() {
	}

	public String getRawUtterance() {
		return rawUtterance;
	}

	public void setRawUtterance(String rawUtterance) {
		this.rawUtterance = rawUtterance;
	}

	public Slot getSlot() {
		return slot;
	}

	public void setSlot(Slot slot) {
		this.slot = slot;
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}
	
	

}
