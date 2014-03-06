package com.example.crypto_app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class HomeScreenActivity extends ListActivity {
	private HomeScreenAdapter adapter;
	private static final int SELECT_CONVERSATION = 0;
	private static final int ADD_CONTACT = 1;
	static final String[] SAMPLE = new String[] {"Sample contact/conversation"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		
		// Create a new adapter for the ListView
		adapter = new HomeScreenAdapter(this);
		ListView lv = (ListView) findViewById(android.R.id.list);
		lv.setAdapter(adapter);
		
		//listView.setTextFilterEnabled(true);
		
		// Handle ListView item clicks
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent myIntent = new Intent(view.getContext(), ConversationActivity.class);
				startActivityForResult(myIntent, SELECT_CONVERSATION);
			}
		});
		
		addListenersOnButtons();
	}
	
	public void addListenersOnButtons() {
		// Add new contact button
		final Button addContact = (Button) findViewById(R.id.addContact);
		addContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(v.getContext(), AddContactActivity.class);
                startActivityForResult(myIntent, ADD_CONTACT);
            }
        });
		
		// Search button
		final Button search = (Button) findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Toast.makeText(getApplicationContext(), "Search to be added.", Toast.LENGTH_SHORT).show();  // setTextFilterEnabled
            }
        });
		
		// Add new contact button
		final Button settings = (Button) findViewById(R.id.settings);
		settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Toast.makeText(getApplicationContext(), "Settings to be added.", Toast.LENGTH_SHORT).show();
            }
        });
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_screen, menu);
		return true;
	}

}
