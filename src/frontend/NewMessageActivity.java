package frontend;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import backend.Contact;
import backend.ConversationData;
import backend.HomeScreenData;
import backend.Message;

import com.example.crypto_app.R;

public class NewMessageActivity extends Activity {
	private int conversationPosition;
	private ConversationData conversation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_message);
		addListenerOnButton();
		
		Intent myIntent = getIntent();
		conversationPosition = myIntent.getIntExtra("position", -1);
		conversation = HomeScreenData.getInstance().conversations.get(conversationPosition);
		
		// Add "Up" navigation
		ActionBar actionBar = getActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	public void addListenerOnButton() {
		// Start new conversation button
		final Button addContact = (Button) findViewById(R.id.sendMessage);
		addContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Check for proper input
            	EditText message = (EditText) findViewById(R.id.new_message);
            	if (message.getText().toString().equals("")) {
        			Toast.makeText(getApplicationContext(), "You cannot send a blank message.", Toast.LENGTH_SHORT).show();
        		}
            	else {
            		Contact contact = new Contact("Home User", "Home Email");
            		Message messageToSend = new Message(contact, message.getText().toString(), new Time(Time.getCurrentTimezone()));
            		conversation.addMessage(messageToSend);
            		setResult(RESULT_OK, new Intent());
            		finish();
            	}
            }
        });
	}
}
