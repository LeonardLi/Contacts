/**
 * 
 * @author ydli
 * 
 */
package com.xiaodevil.views;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import com.xiaodevil.contacts.R;

public class AddNewContactsActivity extends Activity{
	private EditText inputName;
	private EditText inputPhoneNumber;
	private EditText inputQQ;
	private Button confirmButton;
	private String name;
	private String phoneNumber;
	private String qq;
	
	private final static String TAG = "com.xiaodevil.views.AddNewContactsActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_contact);
		Log.i(TAG,"AddNewContactsActivity");
		setupViews();
	}
	
	private void setupViews(){
		inputName = (EditText) findViewById(R.id.input_name);
		inputPhoneNumber = (EditText) findViewById(R.id.input_phone_number);
		inputQQ = (EditText) findViewById(R.id.input_qq);
		confirmButton = (Button) findViewById(R.id.confirm_button);
		
		confirmButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				name = inputName.getText().toString();
				phoneNumber = inputPhoneNumber.getText().toString();
				qq = inputQQ.getText().toString();
				
				if(name != null || phoneNumber != null || qq != null){
					
				}
				
			}
		});
	}
	
	
}
