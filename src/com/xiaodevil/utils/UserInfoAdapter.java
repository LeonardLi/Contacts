package com.xiaodevil.utils;

import java.util.List;

import com.xiaodevil.models.PhoneNumber;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class UserInfoAdapter extends ArrayAdapter<PhoneNumber>{
	private int resourceId;

	public UserInfoAdapter(Context context, int textViewResourceId, List<PhoneNumber> objects) {
		super(context, textViewResourceId, objects);
		this.resourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		PhoneNumber number = this.getItem(position);
		
		
		
		return parent;
		
	}

}
