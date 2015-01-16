package com.xiaodevil.models;

import java.io.Serializable;

public class PhoneNumber implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1799042283281468302L;
	private String phoneNumber;
	private int Type;
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public int getType() {
		return Type;
	}
	public void setType(int type) {
		Type = type;
	}
	
}
