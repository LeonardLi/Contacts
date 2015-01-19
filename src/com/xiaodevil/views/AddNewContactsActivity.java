/**
 * 
 * @author ydli
 * 
 */
package com.xiaodevil.views;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaodevil.contacts.R;
import com.xiaodevil.database.DataHelper;
import com.xiaodevil.models.PhoneNumber;
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
	private final String ADD_SUCCEED = "已添加";
	private final String Add_FAILED ="添加失败";
	
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
				ArrayList<PhoneNumber> list =new ArrayList<>();
				PhoneNumber pho =new PhoneNumber();
				pho.setPhoneNumber(inputPhoneNumber.getText().toString());
				pho.setType(1);
				list.add(pho);
				user.setPhoneNumbers(list);
				
				//phoneNumbers.add(new String[]{inputPhoneNumber.getText().toString()});
				//user.setPhoneNumbers(phoneNumbers);
				//qq = inputQQ.getText().toString();
				
				DataHelper.getInstance().addContacts(getApplicationContext(), user);
				Intent intent = new Intent();
				intent.setClass(AddNewContactsActivity.this, MainActivity.class);
				Toast.makeText(getApplicationContext(), ADD_SUCCEED, Toast.LENGTH_SHORT).show();
				startActivity(intent);
				
			}
		});
	}
	
	
}
