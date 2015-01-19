package com.xiaodevil.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.xiaodevil.contacts.R;

public class SettingsActivity extends ActionBarActivity{
	
	private SharedPreferences settings;
	private SharedPreferences.Editor editor;
	private Context useCount  = null;
	
	private RadioGroup radioGroup=null;
	private RadioButton letterButton=null;
	private RadioButton hanziButton=null;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		useCount=this.getApplicationContext();
		
		
		settings = useCount.getSharedPreferences("settings", MODE_PRIVATE);
		editor = settings.edit();
		
		radioGroup=(RadioGroup)findViewById(R.id.radio_group);
		letterButton=(RadioButton)findViewById(R.id.letter_button);
		hanziButton=(RadioButton)findViewById(R.id.hanzi_button);
		
		if(settings.getString("readType", "hanzi").equals("hanzi"))
			hanziButton.setChecked(true);
		if(settings.getString("readType", "hanzi").equals("letter"))
			letterButton.setChecked(true);
		
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				if(checkedId==letterButton.getId())
				{
					//set sharepreference to leeter
					editor.putString("readType", "letter");
					System.out.println("letter");
				}
				if(checkedId==hanziButton.getId())
				{
					System.out.println("hanzi");
					//set sharepreference to hanzi
					editor.putString("readType", "hanzi");		
				}
				editor.commit();
			}
		});
		
	
	}
	
	
}
