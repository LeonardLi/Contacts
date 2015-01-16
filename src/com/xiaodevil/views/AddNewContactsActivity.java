/**
 * 
 * @author ydli
 * 
 */
package com.xiaodevil.views;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.xiaodevil.contacts.R;
import com.xiaodevil.database.DataHelper;
import com.xiaodevil.models.User;

public class AddNewContactsActivity extends Activity{
	private EditText inputName;
	private EditText inputPhoneNumber;
	private EditText inputQQ;
	private Button confirmButton;
	private String name;
	private String phoneNumber;
	private String qq;
	private User user;
	private ArrayList<String[]> phoneNumbers = new ArrayList<String[]>();
	
	private final static String TAG = "com.xiaodevil.views.AddNewContactsActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_contact);
		Log.i(TAG,"AddNewContactsActivity");
		user = new User();
		setupViews();
	}
	
	private void setupViews(){
		inputName = (EditText) findViewById(R.id.input_name);
		inputPhoneNumber = (EditText) findViewById(R.id.input_phone_number);
		confirmButton = (Button) findViewById(R.id.confirm_button);
		
		confirmButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				user.setUserName(inputName.getText().toString());
				phoneNumbers.add(new String[]{inputPhoneNumber.getText().toString()});
				//user.setPhoneNumbers(phoneNumbers);
				qq = inputQQ.getText().toString();
				
				DataHelper.getInstance().addContacts(getApplicationContext(), user);
				
				
			}
		});
	}
	
	
}
