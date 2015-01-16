/**
 * @author JSL
 */
package com.xiaodevil.database;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;
import android.util.Log;

import com.xiaodevil.models.PhoneNumber;
import com.xiaodevil.models.User;

public class DataHelper {
	private static final String TAG = "com.example.test.DataHelper";

	// public static final int TYPE_HOME = 1;
	// public static final int TYPE_MOBILE = 2;
	// public static final int TYPE_WORK = 3;
	// public static final int TYPE_FAX_WORK = 4;
	// public static final int TYPE_FAX_HOME = 5;
	// public static final int TYPE_PAGER = 6;
	// public static final int TYPE_OTHER = 7;
	// public static final int TYPE_CALLBACK = 8;
	// public static final int TYPE_CAR = 9;
	// public static final int TYPE_COMPANY_MAIN = 10;
	// public static final int TYPE_ISDN = 11;
	// public static final int TYPE_MAIN = 12;
	// public static final int TYPE_OTHER_FAX = 13;
	// public static final int TYPE_RADIO = 14;
	// public static final int TYPE_TELEX = 15;
	// public static final int TYPE_TTY_TDD = 16;
	// public static final int TYPE_WORK_MOBILE = 17;
	// public static final int TYPE_WORK_PAGER = 18;
	// public static final int TYPE_ASSISTANT = 19;
	// public static final int TYPE_MMS = 20;

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
		Log.e("into", user.getPhoneNumbers().size() + "");
		long contact_id = ContentUris.parseId(resolver.insert(uri, values));
		uri = Uri.parse("content://com.android.contacts/data");
		values.put("raw_contact_id", contact_id);
		values.put(Data.MIMETYPE, "vnd.android.cursor.item/name");
		values.put("data1", user.getUserName());
		resolver.insert(uri, values);
		values.clear();

		for (int i = 0; i < user.getPhoneNumbers().size(); i++) {
			values.put("raw_contact_id", contact_id);
			values.put(Data.MIMETYPE, "vnd.android.cursor.item/phone_v2");
			values.put("data2", user.getPhoneNumbers().get(i).getType());
			values.put("data1", user.getPhoneNumbers().get(i).getPhoneNumber());
			resolver.insert(uri, values);
			values.clear();
		}
		// ArrayList<String[]> ph = user.getPhoneNumbers();
		// String[][] test = (String[][]) user.getPhoneNumbers().toArray(
		// new String[0][0]);
		// if (test.length > 0) {
		// for (int i = 0; i < test.length; i++)
		// for (int j = 0; j < test[i].length; j++) {
		// if (!test[i][j].equals("")) {
		// values.put("raw_contact_id", contact_id);
		// values.put(Data.MIMETYPE,
		// "vnd.android.cursor.item/phone_v2");
		// values.put("data2", i + 1 + "");
		// values.put("data1", test[i][j]);
		// Log.e(TAG, values.toString());
		// resolver.insert(uri, values);
		// values.clear();
		// }
		// }
		// }

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
		Cursor cursor = context.getContentResolver().query(uri,
				new String[] { "display_name", "sort_key", "data1" }, null,
				null, "sort_key");
		if (cursor.moveToFirst()) {
			do {
				String name = cursor.getString(0);
				String sortKey = getSortKey(cursor.getString(cursor
						.getColumnIndex("sort_key")));
				// String phoneNumber =
				// cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				User user = new User();
				user.setUserName(name);
				user.setSortKey(sortKey);
				// user.setPhoneNumber(phoneNumber);
				int id = findContactId(context, name);
				Cursor phones = context.getContentResolver().query(
						ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
						new String[] {
								ContactsContract.CommonDataKinds.Phone.NUMBER,
								ContactsContract.CommonDataKinds.Phone.DATA2 },
						ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
								+ id, null, null);
				ArrayList<PhoneNumber> nums = new ArrayList<>();
				while (phones.moveToNext()) {
					PhoneNumber num = new PhoneNumber();
					num.setPhoneNumber(phones.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
					num.setType((int) phones.getLong(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA2)));
					nums.add(num);
				}
				phones.close();
				user.setPhoneNumbers(nums);
				users.add(user);
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
		// int columnNumber = cursor.getColumnCount();
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
			// Log.i(TAG, sb.toString());
		}
		cursor.close();

	}
/**
 * 
 * @param sortKeyString
 * @return
 */
	private String getSortKey(String sortKeyString) {
		String key = sortKeyString.substring(0, 1).toUpperCase();

		if (key.matches("[A-Z]")) {

			return key;
		}
		return "#";
	}

}
