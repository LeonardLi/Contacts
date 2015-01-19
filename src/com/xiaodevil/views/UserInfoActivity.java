/**
 * 
 * @author ydli
 * 
 */
package com.xiaodevil.views;

import java.util.Random;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaodevil.contacts.R;
import com.xiaodevil.database.DataHelper;
import com.xiaodevil.models.PhoneNumber;
import com.xiaodevil.models.User;
import com.xiaodevil.utils.UserInfoAdapter;

public class UserInfoActivity extends ActionBarActivity {

	private final static String TAG = "com.xiaodevil.views.UserInfoActivity";
	private ListAdapter listAdapter;
	private TextView ContactName;
	private ImageView Infobackground;
	private ListView UserInfo;
	private User user;
	private Intent intent;
	private final String[] color = { "#eae8ff", "#d8d5d8", "#adacb5",
			"#2d3142", "#b0d7ff" };
	private final String CONFIRM_MSG = "确认删除么？";
	private final String CONFIRM_MSG_YES = "是";
	private final String CONFIRM_MSG_NO = "否";
	private final String CONFIRM_MSG_DELETED = "已删除";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contacts_info);
		Log.i(TAG, "UserInfoActivity start");
		ContactName = (TextView) findViewById(R.id.name);
		Infobackground = (ImageView) findViewById(R.id.info_avatar_bg);
		Random rdm = new Random(System.currentTimeMillis());
		int index = Math.abs(rdm.nextInt()) % 5;
		Infobackground.setBackgroundColor(android.graphics.Color
				.parseColor(color[index]));

		// dia = (Button)findViewById(R.id.dia);
		// msg = (Button)findViewById(R.id.msg);

		intent = this.getIntent();

		setupViews();

		UserInfo.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_CALL);
				PhoneNumber selectedNumber = (PhoneNumber) listAdapter
						.getItem(position);
				intent.setData(Uri.parse("tel:"
						+ selectedNumber.getPhoneNumber()));
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.contact_info_menu, menu);
		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int ItemId = item.getItemId();
		if (ItemId == R.id.action_modify) {
			modifyContacts();
		}
		if (ItemId == R.id.action_delete) {
			deleteContacts();
		}
		if (ItemId == R.id.action_share) {
			ShareContacts();
		}
		return false;
	}

	private void setupViews() {

		ContactName = (TextView) findViewById(R.id.name);
		Infobackground = (ImageView) findViewById(R.id.info_avatar_bg);
		UserInfo = (ListView) findViewById(R.id.user_phone_number_list);
		Random rdm = new Random(System.currentTimeMillis());
		int index = Math.abs(rdm.nextInt()) % 4;
		Infobackground.setBackgroundColor(android.graphics.Color
				.parseColor(color[index]));

		user = (User) intent.getSerializableExtra(MainActivity.SER_KEY);
		if (user != null) {
			ContactName.setText(user.getUserName());
		}

		listAdapter = new UserInfoAdapter(this, R.layout.userinfo_item,
				user.getPhoneNumbers());
		UserInfo.setAdapter(listAdapter);
	}

	private boolean modifyContacts() {
		return false;
	}

	private boolean deleteContacts() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(CONFIRM_MSG)
				.setCancelable(true)
				.setPositiveButton(CONFIRM_MSG_YES,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								DataHelper.getInstance().deletContact(
										getApplicationContext(), user);
								Toast.makeText(getApplicationContext(),
										CONFIRM_MSG_DELETED, Toast.LENGTH_SHORT)
										.show();
								intent.setClass(UserInfoActivity.this,
										MainActivity.class);
								startActivity(intent);

							}
						})
				.setNegativeButton(CONFIRM_MSG_NO,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
		return true;
	}

	private boolean ShareContacts() {
		return false;
	}
}
