package com.xiaodevil.views;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.xiaodevil.contacts.R;
import com.xiaodevil.database.DataHelper;
import com.xiaodevil.models.PhoneNumber;
import com.xiaodevil.models.User;
import com.xiaodevil.utils.PhoneNumberAdapter;

public class ModifyContactActivity extends ActionBarActivity {
	
	private EditText inputName;
	private User user;
	private User oldUser;
	private List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
	private ArrayList<PhoneNumber> newPhoneNumbers = new ArrayList<PhoneNumber>();
	private static ListView phoneNumberList;
	private PhoneNumberAdapter mAdapter;
	private Intent intent;
	private final static String TAG = "com.xiaodevil.views.ModifyContactActivity";
	private final String MODIFY_SUCCEED = "修改成功";
	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_new_contact);
		intent = getIntent();
		user = (User) intent.getSerializableExtra(UserInfoActivity.SER_KEY);
		oldUser = user;
		phoneNumbers = user.getPhoneNumbers();
		mAdapter = new PhoneNumberAdapter(this, R.layout.phone_number_item, phoneNumbers);
		setupViews();
	}
	
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.add_contact_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();
		if(id == R.id.action_confirm){
			writeData();
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	private void setupViews(){
		inputName = (EditText) findViewById(R.id.input_name);
		inputName.setText(user.getUserName());
		phoneNumberList = (ListView) findViewById(R.id.phone_number_list);
		phoneNumberList.setAdapter(mAdapter);
		resetListViewHeight();
		
	}
	
	private void writeData(){

		newPhoneNumbers.addAll( mAdapter.getAllItem());
		user.setPhoneNumbers(newPhoneNumbers);
		user.setUserName(inputName.getText().toString());
		if(user.getUserName().equals("")){
			Toast.makeText(getApplicationContext(), "请输入姓名", Toast.LENGTH_SHORT).show();
		}else{
		DataHelper.getInstance().updateContact(getApplicationContext(), oldUser, user);
		Intent intent = new Intent();
		intent.setClass(ModifyContactActivity.this, MainActivity.class);
		Toast.makeText(getApplicationContext(), MODIFY_SUCCEED, Toast.LENGTH_SHORT).show();
		startActivity(intent);
		}
		
	}
	
	public static void resetListViewHeight(){
		PhoneNumberAdapter adapter = (PhoneNumberAdapter)phoneNumberList.getAdapter();
		if(adapter == null){
			return;
		}
		int totalHeight = 0;
		for(int i = 0; i < adapter.getCount(); i++){
			View listItem = adapter.getView(i, null, phoneNumberList);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		
		ViewGroup.LayoutParams params = phoneNumberList.getLayoutParams();
		params.height = totalHeight + (phoneNumberList.getDividerHeight()*(adapter.getCount() - 1));
		phoneNumberList.setLayoutParams(params);
	}
}
