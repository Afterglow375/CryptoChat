package com.example.crypto_app;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class NewMessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_message);
		addListenerOnButton();
		
		// Add "Up" navigation
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	public void addListenerOnButton() {
		// Start new conversation button
		final Button addContact = (Button) findViewById(R.id.sendMessage);
		addContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Populate home screen listview with new contact/conversation
            	finish();
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_message, menu);
		return true;
	}

}
