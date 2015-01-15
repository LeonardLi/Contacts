/**
 * 
 * @author ydli
 *	
 */
package com.xiaodevil.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class User implements Serializable {
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	private static final long serialVersionUID = -2894121008151688346L;
	private int id;
	private String userName;
	private String phoneNumber;
	private Map<Integer,String> phoneNumbers;
	

	private String nickname;
	private String sortKey;
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}



	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	
	public User()
	{
		phoneNumbers = new HashMap<Integer, String>();
	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}




	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	@Override
	public String toString()
	{
		String text = "";
		text += "\nid = " + id;
		text += "\nusername = " + userName;
		text += "\nphoneNumber = " + phoneNumber;
		text += "\nnickname = " + nickname;
		
		return text;
	}
}
