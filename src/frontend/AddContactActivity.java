package frontend;

import com.example.crypto_app.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import backend.Contact;
import backend.ConversationData;
import backend.HomeScreenData;

public class AddContactActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_contact);
		
		addListenerOnButton();
		// Add "Up" navigation
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	public void addListenerOnButton() {
		// Start new conversation button
		final Button addContact = (Button) findViewById(R.id.startConversation);
		addContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// Check for properly inputed values
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
}
