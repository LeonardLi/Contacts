package com.xiaodevil.utils;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.xiaodevil.contacts.R;
import com.xiaodevil.models.User;

public class ContactAdapter extends ArrayAdapter<User> implements SectionIndexer {
		
	private static final String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private int resourceId;
	public ContactAdapter(Context context, int textViewResourceId,List<User> objects) {
		super(context, textViewResourceId, objects);
		this.resourceId = textViewResourceId;

	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		User user = this.getItem(position);
		LinearLayout layout = null;
		if (convertView == null) {
			layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(
					resourceId, null);
		} else {
			layout = (LinearLayout) convertView;
		}
		TextView name = (TextView) layout.findViewById(R.id.name);
		TextView avatar = (TextView) layout.findViewById(R.id.avatar_letter);
		name.setText(user.getUserName());
		try{
		String s = user.getUserName().substring(0, 1);
		System.out.println(s);
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		
		//avatar.setText(user.getSortKey());
		avatar.setText(user.getUserName().substring(0,1));
		return layout;
	}

	@Override
	public int getPositionForSection(int section) {
		// If there is no item for current section, previous section will be selected
		for (int i = section; i >= 0; i--) {
			for (int j = 0; j < getCount(); j++) {
				if (i == 0) {
					// For numeric section
					for (int k = 0; k <= 9; k++) {
						if (StringMatcher.match(String.valueOf(getItem(j).getSortKey()), String.valueOf(k)))
							return j;
					}
				} else {
					if (StringMatcher.match(String.valueOf(getItem(j).getSortKey()), String.valueOf(mSections.charAt(i))))
						return j;
				}
			}
		}
		return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}

	@Override
	public Object[] getSections() {
		String[] sections = new String[mSections.length()];
		for (int i = 0; i < mSections.length(); i++)
			sections[i] = String.valueOf(mSections.charAt(i));
		return sections;
	}

}
