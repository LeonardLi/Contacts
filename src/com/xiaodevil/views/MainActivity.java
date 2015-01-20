/**
 * 
 * @author ydli
 * 
 */
package com.xiaodevil.views;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
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
import com.xiaodevil.database.DataHelper;
import com.xiaodevil.models.User;
import com.xiaodevil.utils.ContactAdapter;


public class MainActivity extends ActionBarActivity {
	private IndexableListView contactsListView;
	private ContactAdapter adapter;
	private List<User> users;
	private SearchView searchview;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
	
	private Intent intent = new Intent();
	private User selectedUser = new User();
	private final static String TAG = "com.xiaodevil.views.MainActivity";
	public final static String SER_KEY = "com.xiaode.user";
	public final static String JUDGE_KEY = "isFirstTime";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"MainActivity start");
        initPreferences();
        //第一次初始化
    	if(preferences.getString(JUDGE_KEY, "true").equals("true"))
    	{
    		editor.putString(JUDGE_KEY, "false");
			editor.commit();
			DataHelper.getInstance().setAvatar(getApplicationContext());	
    	}
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
			intent.setClass(MainActivity.this, AddNewContactsActivity.class);
			startActivity(intent);
			return true;
			
		}
		if (id == R.id.action_settings) {
			intent.setClass(MainActivity.this, SettingsActivity.class);
			startActivity(intent);
			return true;
			
		}
		if (id == R.id.action_about) {
			Toast.makeText(getApplicationContext(), "action_about",Toast.LENGTH_SHORT).show();
			return true;
		}
		if (id == R.id.action_search) {
			searchview.setIconified(false);
			searchview.setSubmitButtonEnabled(true);
			searchview.setOnQueryTextListener(new OnQueryTextListener() {
				
				@Override
				public boolean onQueryTextSubmit(String s) {
					return false;
				}
				
				@Override
				public boolean onQueryTextChange(String s) {
					adapter.getFilter().filter(s);
					return true;
				}
			});
			
			MenuItemCompat.setOnActionExpandListener(item, new OnActionExpandListener() {
				
				@Override
				public boolean onMenuItemActionExpand(MenuItem arg0) {
					
					return true;
				}
				
				@Override
				public boolean onMenuItemActionCollapse(MenuItem arg0) {
						//Toast.makeText(getApplicationContext(), "over", Toast.LENGTH_SHORT).show();
						adapter.getFilter().filter("");
					return true;
				}
			});
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
	
    @Override
	public void onBackPressed(){
    	Intent intent = new Intent();
    	intent.setAction(Intent.ACTION_MAIN);
    	intent.addCategory(Intent.CATEGORY_HOME);
    	startActivity(intent);
    } 
    /**
     * 
     * 
     */
    private void setupViews(){
    	contactsListView = (IndexableListView) findViewById(R.id.contancts_list); 
    	
    	
    	
    		
    	users = DataHelper.getInstance().queryContact(getApplicationContext());
    	adapter = new ContactAdapter(this, R.layout.contact_item, users);
		if(users.size() > 0){
			setupContactsListView();
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



    
	private  void initPreferences(){	
        preferences = getSharedPreferences("settings",MODE_PRIVATE);
        editor=preferences.edit();
        editor.putString("readType", "letter");
        editor.putString(JUDGE_KEY, "true");
        editor.commit();
	}
}
