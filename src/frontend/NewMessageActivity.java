package frontend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	public void addListenerOnButton() {
		// Start sendMessage button
		final ImageButton sendMessage = (ImageButton) findViewById(R.id.sendMessage);
		sendMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Check for proper input
            	EditText message = (EditText) findViewById(R.id.new_message);
            	if (message.getText().toString().equals("")) {
        			Toast.makeText(getApplicationContext(), "You cannot send a blank message.", Toast.LENGTH_SHORT).show();
        		}
            	else {
            		Contact contact = new Contact("Home User", "Home Email");
            		Time now = new Time();
            		now.setToNow();
            		Message messageToSend = new Message(contact, message.getText().toString(), now);
            		conversation.addMessage(messageToSend);
            		setResult(RESULT_OK, new Intent());
            		finish();
            		//TODO: write a encrypt method for messages.  String encrptedMessage = messageToSend.encrypt();
            		Intent i = new Intent(Intent.ACTION_SEND);
            		i.setType("message/rfc822");
            		i.putExtra(Intent.EXTRA_EMAIL, new String[]{conversation.getContact().getEmail()});
            		i.putExtra(Intent.EXTRA_SUBJECT, "CryptoChat");
            		//TODO still need encrypt method i.putExtra(Intent.EXTRA_TEXT, encryptedMessage);
            		i.putExtra(Intent.EXTRA_TEXT, messageToSend.getMessage()); // for now using normal not encrypted text.
            		try{
            			startActivity(Intent.createChooser(i, "Send encrypted message..."));
            		}
            		catch(android.content.ActivityNotFoundException e){
            			Toast.makeText(getApplicationContext(), "No Email apps installed", Toast.LENGTH_SHORT).show();
            		}
            	}
              
            }
        });
		}
}
