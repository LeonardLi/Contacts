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
	private String type;
	private RadioGroup radioGroup=null;
	private RadioButton letterButton=null;
	private RadioButton hanziButton=null;
	public static final  String KEY_SETTINGS = "settings";
	public static final  String KEY_READTYPE = "readType";
	public static final  String KEY_LETTER = "letter";
	public static final  String KEY_HANZI = "hanzi";
	
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		useCount=this.getApplicationContext();
		
		
		settings = useCount.getSharedPreferences(KEY_SETTINGS, MODE_PRIVATE);
		editor = settings.edit();
		
		radioGroup=(RadioGroup)findViewById(R.id.radio_group);
		letterButton=(RadioButton)findViewById(R.id.letter_button);
		hanziButton=(RadioButton)findViewById(R.id.hanzi_button);
		type = settings.getString(KEY_READTYPE, KEY_HANZI);
		if(type.equals(KEY_HANZI))
			hanziButton.setChecked(true);
		if(type.equals(KEY_LETTER))
			letterButton.setChecked(true);
		
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				if(checkedId==letterButton.getId())
				{
					//set sharepreference to leeter
					editor.putString(KEY_READTYPE, KEY_LETTER);
				}
				if(checkedId==hanziButton.getId())
				{
					//set sharepreference to hanzi
					editor.putString(KEY_READTYPE, KEY_HANZI);		
				}
				editor.commit();
			}
		});
		
	
	}
	
	
}
