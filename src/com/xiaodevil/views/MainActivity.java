/**
 * 
 * @author ydli
 * 
 */
package com.xiaodevil.views;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.xiaodevil.contacts.R;
import com.xiaodevil.models.User;
import com.xiaodevil.utils.ContactAdapter;


public class MainActivity extends ActionBarActivity {
	private IndexableListView contactsListView;
	private ContactAdapter adapter;
	private List<User> users = new ArrayList<User>();
	private SearchView searchview;

	
	
	private Intent intent = new Intent();
	private User selectedUser = new User();
	private final static String TAG = "com.xiaodevil.views.MainActivity";
	private Cursor cursor;
	private Handler handler = new Handler();
	public final static String SER_KEY = "com.xiaode.user";
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"MainActivity start");

        setupViews();
       
     
        contactsListView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle mBundle = new Bundle();
				selectedUser = (User)adapter.getItem(arg2);
				mBundle.putSerializable(SER_KEY, selectedUser);
				intent.putExtras(mBundle);
				intent.setClass(MainActivity.this, UserInfoActivity.class);
				startActivity(intent);
			}
		});
        
    }
    @SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
    	getMenuInflater().inflate(R.menu.main, menu);
    	
    	MenuItem searchItem = menu.findItem(R.id.action_search);
    	searchview = (SearchView)MenuItemCompat.getActionView(searchItem);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_add) {
			//Toast.makeText(getApplicationContext(), "action_settings",Toast.LENGTH_SHORT).show();
			intent.setClass(MainActivity.this, AddNewContactsActivity.class);
			startActivity(intent);
			return true;
			
		}
		if (id == R.id.action_settings) {
			Toast.makeText(getApplicationContext(), "action_settings",Toast.LENGTH_SHORT).show();
			return true;
			
		}
		if (id == R.id.action_about) {
			Toast.makeText(getApplicationContext(), "action_about",Toast.LENGTH_SHORT).show();
			return true;
		}
		if (id == R.id.action_search) {
			//Toast.makeText(getApplicationContext(), "action_about",Toast.LENGTH_SHORT).show();
			searchview.setIconified(false);
			searchview.setSubmitButtonEnabled(true);
			searchview.setOnQueryTextListener(new OnQueryTextListener() {
				
				@Override
				public boolean onQueryTextSubmit(String s) {
					Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
					return false;
				}
				
				@Override
				public boolean onQueryTextChange(String s) {
					
					return false;
				}
			});
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
    
    /**
     * 
     * 
     */
    public void setupViews(){
    	contactsListView = (IndexableListView) findViewById(R.id.contancts_list);  	
    	adapter = new ContactAdapter(this, R.layout.contact_item, users);
    	
    	Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    	cursor = getContentResolver().query(uri, 
    			new String[]{"display_name","sort_key","data1"}, 
    			null, 
    			null, 
    			"sort_key");    	
    	if(cursor.moveToFirst()){
    		do{
    			String name = cursor.getString(0);
    			String sortKey = getSortKey(cursor.getString(cursor.getColumnIndex("sort_key")));
    			String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
    			User user = new User();
    			user.setUserName(name);
    			user.setSortKey(sortKey);
    			user.setPhoneNumber(phoneNumber);
    			users.add(user);
    		}while(cursor.moveToNext());
    		startManagingCursor(cursor);


    		if(users.size() > 0){
    			setupContactsListView();
    		}
    		
    	}
    }

    /**
     * 
     * 
     */
    private void setupContactsListView(){
    	
    	contactsListView.setAdapter(adapter);
    	contactsListView.setFastScrollEnabled(true); 	
    }
    /**
     * 
     *
     *@param sortKeyString
     *@return
     *
     */
    private String getSortKey(String sortKeyString){
    	String key = sortKeyString.substring(0,1).toUpperCase();

    	if(key.matches("[A-Z]")){
    		
    		return key;
    	}
    	return "#";
    }
    private void searchContacts(String s){
    	
    }
    
	
}
