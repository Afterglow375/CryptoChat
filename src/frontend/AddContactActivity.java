package frontend;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import backend.Contact;
import backend.ConversationData;
import backend.HomeScreenData;

import com.example.crypto_app.R;

import crypto.KeyCreator;

public class AddContactActivity extends Activity {
	private KeyCreator keycreator;
	private ArrayList<String> keywords = new ArrayList<String>(5);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		
		addListenerOnButton();
		keycreator = KeyCreator.getInstance(this);
		updateKeywords();
		
		// Add "Up" navigation
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}
	
	public void addListenerOnButton() {
		// Generate new keywords button
		final ImageButton newKeywords = (ImageButton) findViewById(R.id.newKeywords);
		newKeywords.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	updateKeywords();
            }
        });
		
		// Start new conversation button
		final ImageButton addContact = (ImageButton) findViewById(R.id.startConversation);
		addContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Check for properly inputted values
            	EditText editName = (EditText) findViewById(R.id.edit_name);
            	EditText editEmail = (EditText) findViewById(R.id.edit_email);
            	EditText word1 = (EditText) findViewById(R.id.enterKeyword1);
            	EditText word2 = (EditText) findViewById(R.id.enterKeyword2);
            	EditText word3 = (EditText) findViewById(R.id.enterKeyword3);
            	EditText word4 = (EditText) findViewById(R.id.enterKeyword4);
            	EditText word5 = (EditText) findViewById(R.id.enterKeyword5);
            	if (editName.getText().toString().equals("")) {
        			Toast.makeText(getApplicationContext(), "You must specify the contact's name.", Toast.LENGTH_SHORT).show();
        		}
        		else if (editEmail.getText().toString().equals("")) {
        			Toast.makeText(getApplicationContext(), "You must specify the contact's email.", Toast.LENGTH_SHORT).show();
        		}
        		else if (word1.getText().toString().equals("") || word2.getText().toString().equals("") || word3.getText().toString().equals("")
        				|| word4.getText().toString().equals("") || word5.getText().toString().equals("")) {
        			Toast.makeText(getApplicationContext(), "You must enter all 5 words.", Toast.LENGTH_SHORT).show();
        		}
        		else { // Else no errors
        			Contact c = new Contact(editName.getText().toString(), editEmail.getText().toString());
            		
            		// TODO: PROPERLY CREATE THE KEY HERE
            		byte[] sampleKey = new byte[1];
            		
            		ConversationData conversation = new ConversationData(sampleKey, c);
            		HomeScreenData.getInstance().prependConversation(conversation);
            		// TODO: UPDATE NEW CONTACT IN INTERNAL STORAGE
            		
            		setResult(RESULT_OK, new Intent());
                	finish();
        		}
            }
        });
	}
	
	// Creates 5 new random keywords and updates on screen
	public void updateKeywords() {
		keywords = keycreator.generateWords();
		TextView word1 = (TextView) findViewById(R.id.createdKeyword1);
		TextView word2 = (TextView) findViewById(R.id.createdKeyword2);
		TextView word3 = (TextView) findViewById(R.id.createdKeyword3);
		TextView word4 = (TextView) findViewById(R.id.createdKeyword4);
		TextView word5 = (TextView) findViewById(R.id.createdKeyword5);
		word1.setText(keywords.get(0));
		word2.setText(keywords.get(1));
		word3.setText(keywords.get(2));
		word4.setText(keywords.get(3));
		word5.setText(keywords.get(4));
	}
}
