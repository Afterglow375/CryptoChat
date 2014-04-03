package frontend;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crypto_app.R;

public class ConversationActivity extends ListActivity {
	private ConversationAdapter adapter;
	public static int conversationPosition = -2;
	private static final int NEW_MESSAGE = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_conversation);
		
		// Create a new adapter for the ListView
		Intent myIntent = getIntent();
		if (conversationPosition == -2) {
			conversationPosition = myIntent.getIntExtra("position", -1);
		}
		adapter = new ConversationAdapter(this, conversationPosition);
		ListView lv = (ListView) findViewById(android.R.id.list);
		lv.setAdapter(adapter);
		addListenerOnButton();
		
		// Add "Up" navigation
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	public void addListenerOnButton() {
		// Create a new message button
		final Button addContact = (Button) findViewById(R.id.newMessage);
		addContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(v.getContext(), NewMessageActivity.class);
            	myIntent.putExtra("position", conversationPosition);
                startActivityForResult(myIntent, NEW_MESSAGE);
            }
        });
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == NEW_MESSAGE) {
			if (resultCode == RESULT_OK) {
				adapter.notifyDataSetChanged();
				Toast.makeText(getApplicationContext(), "Message sent.", Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_message, menu);
		return true;
	}
}
