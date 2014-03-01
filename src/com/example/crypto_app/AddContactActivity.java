package com.example.crypto_app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class AddContactActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		
		addListenerOnButton();
//		ActionBar actionBar = getActionBar();
//		actionBar.setHomeButtonEnabled(true);
	}
	
	public void addListenerOnButton() {
		// Start new conversation button
		final Button addContact = (Button) findViewById(R.id.startConversation);
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
		getMenuInflater().inflate(R.menu.add_contact, menu);
		return true;
	}
	
//	// Add the back button the action bar
//	@Override
//	public boolean onOptionsItemSelected(MenuItem menuItem)
//	{       
//	    onBackPressed();
//	    return true;
//	}

}
