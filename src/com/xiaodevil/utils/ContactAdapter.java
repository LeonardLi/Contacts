package com.xiaodevil.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.xiaodevil.contacts.R;
import com.xiaodevil.models.User;

public class ContactAdapter extends BaseAdapter implements SectionIndexer,Filterable{
		
	private static final String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private String[] color = {"#3d315b","#444b6e","#708b75","#9ab875","#b0d7ff","#ec6623"};
	private int resourceId;
	private List<User> mObjects;
	private ArrayList<User> mOriginalValues;
	private ContactFilter mFilter;
	private final Object mLock = new Object();
	private Context context;	
	private SharedPreferences preferences;
	private final String TAG = "com.xiaodevil.utils.ContactAdapter";
	
	public ContactAdapter(Context context,int resourceid, List<User> list){
		mObjects = list;
		this.context = context;
		this.resourceId = resourceid;
		preferences = this.context.getSharedPreferences("settings", Context.MODE_PRIVATE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		User user = this.getItem(position);
		LinearLayout layout = null;
		if (convertView == null) {
			layout = (LinearLayout) LayoutInflater.from(context).inflate(
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
		Log.e(TAG,preferences.getString("readType","letter"));
		if(preferences.getString("readType","letter").equals("letter"))
		{
			
			avatar.setText(user.getSortKey());
		}
		if(preferences.getString("readType","letter").equals("hanzi"))
		{
			avatar.setText(user.getUserName().substring(0,1));
		}
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

	
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ContactFilter();
        }
        return mFilter;
    }
	private class ContactFilter extends Filter{		
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
            	 synchronized (mLock) { 
                    mOriginalValues = new ArrayList<User>(mObjects);
            	 }
            }

            if (prefix == null || prefix.length() == 0) {
                ArrayList<User> list;
                synchronized (mLock) {
                    list = new ArrayList<User>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();

            } else {
                String prefixString = prefix.toString().toLowerCase();

                ArrayList<User> values;
                synchronized (mLock) {
                    values = new ArrayList<User>(mOriginalValues);
                }

                final int count = values.size();
                final ArrayList<User> newValues = new ArrayList<User>();

                for (int i = 0; i < count; i++) {
                    final User value = values.get(i);
                    final String valueText = value.getUserName().toString().toLowerCase();

                    // First match against the whole, non-splitted value
                    if (valueText.startsWith(prefixString)) {
                        newValues.add(value);
                    } else {
                        final String[] words = valueText.split(" ");
                        final int wordCount = words.length;

                        // Start at index 0, in case valueText starts with space(s)
                        for (int k = 0; k < wordCount; k++) {
                            if (words[k].startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }

                results.values = newValues;
                results.count = newValues.size();
            }
           
            return results;
        }
			       
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
            mObjects = (List<User>) results.values;          
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
	}
	@Override
	public int getCount() {
		return mObjects.size();
	}

	@Override
	public User getItem(int position) {
		return mObjects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	
}
