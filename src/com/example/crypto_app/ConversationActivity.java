package com.example.crypto_app;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ConversationActivity extends ListActivity {

	static final String[] SAMPLE = new String[] {"Sample message"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.conversation_list_item, R.id.sampleMessage, SAMPLE);
		setListAdapter(new ArrayAdapter<String>(this, R.layout.activity_conversation, SAMPLE));
		ListView lv = (ListView) findViewById(android.R.id.list);
		lv.setAdapter(adapter);
		addListenerOnButton();
	}
	
	public void addListenerOnButton() {
		// Create a new message button
		final Button addContact = (Button) findViewById(R.id.newMessage);
		addContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(v.getContext(), NewMessageActivity.class);
                startActivityForResult(myIntent, 1);
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.conversation, menu);
		return true;
	}

}
