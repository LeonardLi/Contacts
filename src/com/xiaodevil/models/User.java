/**
 * 
 * @author ydli
 *	
 */
//package com.xiaodevil.models;
package com.xiaodevil.models;

import java.io.Serializable;
import java.util.ArrayList;
//TYPE_MOBILE 
//TYPE_HOME 
//TYPE_WORK 

public class User implements Serializable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	private static final long serialVersionUID = -2894121008151688346L;
	private int id;
	private String userName;
	private ArrayList<PhoneNumber> phoneNumbers;
	private String nickname;
	private String sortKey;

	
	
	public String getSortKey() {
		return sortKey;
	}

	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result
				+ ((nickname == null) ? 0 : nickname.hashCode());
		result = prime * result
				+ ((phoneNumbers == null) ? 0 : phoneNumbers.hashCode());
		result = prime * result + ((sortKey == null) ? 0 : sortKey.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id != other.id)
			return false;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (phoneNumbers == null) {
			if (other.phoneNumbers != null)
				return false;
		} else if (!phoneNumbers.equals(other.phoneNumbers))
			return false;
		if (sortKey == null) {
			if (other.sortKey != null)
				return false;
		} else if (!sortKey.equals(other.sortKey))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}





	public User() {
	
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
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", phoneNumbers="
				+ phoneNumbers + ", nickname=" + nickname + ", sortKey="
				+ sortKey + "]";
	}

	public ArrayList<PhoneNumber> getPhoneNumbers() {
		return phoneNumbers;
	}

	public void setPhoneNumbers(ArrayList<PhoneNumber> phoneNumbers) {
		this.phoneNumbers = phoneNumbers;
	}




}
