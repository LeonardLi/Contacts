package com.xiaodevil.utils;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaodevil.contacts.R;
import com.xiaodevil.models.PhoneNumber;

public class UserInfoAdapter extends ArrayAdapter<PhoneNumber>{
	private int resourceId;
	private class buttonViewHolder{
		TextView number;
		TextView type;
		ImageButton msgButton;
	}
	private buttonViewHolder holder;
	private PhoneNumber number;

	public UserInfoAdapter(Context context, int textViewResourceId, List<PhoneNumber> objects) {
		super(context, textViewResourceId, objects);
		this.resourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		number = this.getItem(position);
		LinearLayout layout = null;
		if(convertView == null ){
			layout = (LinearLayout) LayoutInflater.from(getContext()).inflate(
					resourceId, null);
			holder = new buttonViewHolder();
			holder.msgButton = (ImageButton)layout.findViewById(R.id.msg);
			holder.number = (TextView)layout.findViewById(R.id.user_phone_number);
			holder.type = (TextView)layout.findViewById(R.id.user_phone_number_type);
		}else{
			layout = (LinearLayout)convertView;
			holder = new buttonViewHolder();
			holder.msgButton = (ImageButton)layout.findViewById(R.id.msg);
			holder.number = (TextView)layout.findViewById(R.id.user_phone_number);
			holder.type = (TextView)layout.findViewById(R.id.user_phone_number_type);
			
		}
		
		holder.number.setText(number.getPhoneNumber());
		holder.type.setText(number.getType()+"");
		holder.msgButton.setOnClickListener(new msgButtonListener(position, parent.getContext()));
		
		return layout;
		
	}

	class msgButtonListener implements OnClickListener{
		private Context context;
		public msgButtonListener(int pos, Context con) {
			context = con;
		}
		@Override
		public void onClick(View v) {
			int vid = v.getId();
			if(vid == holder.msgButton.getId()){
				Toast.makeText(getContext(), "text", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+number.getPhoneNumber()));
				intent.putExtra("sms_body","绍林好美");
				context.startActivity(intent);
			}
			
		}
		
	}
}
