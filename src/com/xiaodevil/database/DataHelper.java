/**
 * @author JSL
 */
package com.xiaodevil.database;

import java.util.ArrayList;
import java.util.Random;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;

import com.xiaodevil.models.PhoneNumber;
import com.xiaodevil.models.User;
import com.xiaodevil.views.UserInfoActivity;

public class DataHelper {
	private static final String TAG = "com.example.test.DataHelper";

	private DataHelper() {

	}

	private static DataHelper dataHelper = null;

	public static DataHelper getInstance() {
		if (dataHelper == null) {
			dataHelper = new DataHelper();
		}
		return dataHelper;
	}

	/**
	 * 
	 * @param context
	 * @param use
	 */
	public void addContacts(Context context, User user) {

		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver resolver = context.getContentResolver();
		ContentValues values = new ContentValues();
		long contact_id = ContentUris.parseId(resolver.insert(uri, values));
		uri = Uri.parse("content://com.android.contacts/data");
		values.put("raw_contact_id", contact_id);
		values.put(Data.MIMETYPE, "vnd.android.cursor.item/name");
		values.put("data1", user.getUserName());
		resolver.insert(uri, values);
		values.clear();
		if (user.getTeam() != null) {
			values.put("raw_contact_id", contact_id);
			values.put(Data.MIMETYPE, "vnd.android.cursor.item/organization");
			values.put("data1", user.getTeam());
			resolver.insert(uri, values);
			values.clear();
		}
		if (user.getEmail() != null) {
			values.put("raw_contact_id", contact_id);
			values.put(Data.MIMETYPE, "vnd.android.cursor.item/email_v2");
			values.put("data1", user.getEmail());
			resolver.insert(uri, values);
			values.clear();
		}
		Random rdm = new Random(System.currentTimeMillis());
		int index = Math.abs(rdm.nextInt())%5;
		int index2 = Math.abs(rdm.nextInt())%5;
		for (int i = 0; i < user.getPhoneNumbers().size(); i++) {
			values.put("raw_contact_id", contact_id);
			values.put(Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
			values.put("data2", user.getPhoneNumbers().get(i).getType());
			values.put("data1", user.getPhoneNumbers().get(i).getPhoneNumber());
			values.put("data14",UserInfoActivity.color[index] );
			values.put("data15",UserInfoActivity.avatar[index2] );
			resolver.insert(uri, values);
			values.clear();
		}
	}

	/**
	 * 
	 * @param context
	 * @param na
	 */
	public void deletContact(Context context, User use) {
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver.query(uri, new String[] { Data._ID },
				"display_name =?", new String[] { use.getUserName() }, null);
		if (cursor.moveToFirst()) {
			int id = cursor.getInt(0);
			resolver.delete(uri, "display_name=?",
					new String[] { use.getUserName() });
			uri = Uri.parse("content://com.android.contacts/data");
			resolver.delete(uri, "raw_contact_id=?", new String[] { id + "" });
		}

	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public ArrayList<User> queryContact(Context context) {
		ArrayList<User> users = new ArrayList<>();
		Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
		Cursor cursor = context.getContentResolver().query(
				uri,
				new String[] { "display_name", "sort_key",
						ContactsContract.CommonDataKinds.Phone.NUMBER,
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
						ContactsContract.CommonDataKinds.Phone.DATA2,
						ContactsContract.CommonDataKinds.Phone.DATA14,
						ContactsContract.CommonDataKinds.Phone.DATA15, }, null,
				null, "sort_key");
		if (cursor.moveToFirst()) {
			do {
				String pre_name = null;
				String email = null;
				String team = null;
				String name = cursor.getString(0);
				String sortKey = getSortKey(cursor.getString(cursor
						.getColumnIndex("sort_key")));
				String number = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				int contactID = cursor
						.getInt(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
				int type = cursor
						.getInt(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA2));
				String col = cursor
						.getString(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA14));
				int ava = cursor
						.getInt(cursor
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA15));

				User user = new User();
				user.setUserName(name);
				user.setSortKey(sortKey);
				user.setBgColor(col);
				user.setAvatarId(ava);
				ArrayList<PhoneNumber> nums = new ArrayList<>();
				do {
					PhoneNumber num = new PhoneNumber();
					num.setPhoneNumber(number);
					num.setType(type);
					nums.add(num);
					pre_name = name;
					if (cursor.moveToNext()) {
						name = cursor.getString(0);
						sortKey = getSortKey(cursor.getString(cursor
								.getColumnIndex("sort_key")));
						number = cursor
								.getString(cursor
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						type = (int) cursor
								.getLong(cursor
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA2));
					} else
						break;
				} while (pre_name.equals(name));
				user.setPhoneNumbers(nums);
				users.add(user);
				cursor.moveToPrevious();
			} while (cursor.moveToNext());
			cursor.close();
		}
		return users;
	}

	/**
	 * 
	 * @param context
	 * @param use
	 * @param use1
	 */
	public void updateContact(Context context, User use, User use1) {
		deletContact(context, use);
		addContacts(context, use1);
	}

	/**
	 * 
	 * @param context
	 * @param na
	 * @return
	 */
	public int findContactId(Context context, String na) {
		int raw_id = -1;
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		ContentResolver resolver = context.getContentResolver();
		Cursor cursor = resolver.query(uri, new String[] { Data._ID },
				"display_name=?", new String[] { na }, null);
		if (cursor.moveToFirst())
			raw_id = cursor.getInt(0);
		return raw_id;
	}

	/**
	 * 
	 * @param context
	 */
	public void getAllContact(Context context) {
		ContentResolver contentResolver = context.getContentResolver();
		Cursor cursor = contentResolver.query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		while (cursor.moveToNext()) {
			StringBuilder sb = new StringBuilder();
			String name = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			sb.append("contactID=").append(contactId).append(",Name=")
					.append(name);
			Cursor phones = contentResolver.query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
							+ contactId, null, null);
			while (phones.moveToNext()) {
				String phoneNuber = phones
						.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				sb.append(",phone=").append(phoneNuber);
			}
			phones.close();
		}
		cursor.close();

	}

	/**
	 * 
	 * @param sortKeyString
	 * @return
	 */
	public void setAvatar(Context context)
	{
		Uri uri = Uri.parse("content://com.android.contacts/data");
		Cursor cursor = context.getContentResolver().query(uri,new String[]{Data.RAW_CONTACT_ID},"mimetype=?",new String[]{"vnd.android.cursor.item/phone_v2"},null);
		while(cursor.moveToNext()){
			Random rdm = new Random(System.currentTimeMillis());
			int index = Math.abs(rdm.nextInt())%5;
			int index2 = Math.abs(rdm.nextInt())%5;
			ContentValues values =new ContentValues();
			values.put("data14", UserInfoActivity.color[index]);
			values.put("data15", UserInfoActivity.avatar[index2]);
			int contactID =cursor.getInt(0);
			Cursor cur = context.getContentResolver().query(uri,new String[]{Data._ID},
					"mimetype=? and raw_contact_id=?",new String[]{"vnd.android.cursor.item/phone_v2",contactID+""},null);
			while(cur.moveToNext()){
				int id = cur.getInt(0);
				context.getContentResolver().update(uri,
						values, " _id=?" ,new String[]{id+""});
			}
			cur.close();			
		}
		cursor.close();
	}

	private String getSortKey(String sortKeyString) {
		String key = sortKeyString.substring(0, 1).toUpperCase();

		if (key.matches("[A-Z]")) {

			return key;
		}
		return "#";
	}

}
