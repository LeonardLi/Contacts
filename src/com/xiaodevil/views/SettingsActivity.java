package com.xiaodevil.views;

import com.xiaodevil.contacts.R;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class SettingsActivity extends Activity{
	private SharedPreferences settings;
	private SharedPreferences.Editor editor;
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
		settings = getSharedPreferences("setting",Context.MODE_PRIVATE);
		editor = settings.edit();
	}
}
