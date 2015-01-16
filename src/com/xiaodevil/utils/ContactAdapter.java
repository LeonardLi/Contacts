package com.xiaodevil.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.xiaodevil.contacts.R;
import com.xiaodevil.models.User;

public class ContactAdapter extends ArrayAdapter<User> implements SectionIndexer,Filterable{
		
	private static final String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String[] color = {"#3d315b","#444b6e","#708b75","#9ab875","#b0d7ff","#ec6623"};
	private int resourceId;
	private List<User> list;
	public ContactAdapter(Context context, int textViewResourceId,List<User> objects) {
		super(context, textViewResourceId, objects);
		this.resourceId = textViewResourceId;
		this.list = objects;
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
		ImageButton avatar_bg = (ImageButton) layout.findViewById(R.id.avatar);
		Random rdm = new Random(System.currentTimeMillis());
		int index = Math.abs(rdm.nextInt())%6;
		avatar_bg.setBackgroundColor(android.graphics.Color.parseColor(color[index]));
		
		name.setText(user.getUserName());
		
		
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
	
	private class ContactFilter extends Filter{
		private List<User> original;
		
		public ContactFilter(List<User> list){
			this.original = list;
		}
		@SuppressLint("DefaultLocale")
		@Override
		protected FilterResults performFiltering(CharSequence constraint) {
			FilterResults results = new FilterResults();
			if(constraint == null || constraint.length() == 0){
				results.values = original;
				results.count = original.size();
			}else{
				List<User> mList = new ArrayList<User>();
				for(User p : original){
					if(p.getUserName().toUpperCase().startsWith(constraint.toString().toUpperCase())){
						mList.add(p);
						}
				}
				results.values = mList;
				results.count = mList.size();
			}
			
			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			list = (List<User>)results.values;
			notifyDataSetChanged();
			
		}
		
	}

}
