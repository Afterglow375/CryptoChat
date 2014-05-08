package frontend;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.crypto.spec.SecretKeySpec;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import backend.Contact;
import backend.HomeScreenData;
import backend.Message;
import com.example.crypto_app.R;
import crypto.Decrypt;

public class HomeScreenActivity extends ListActivity {
	private HomeScreenAdapter adapter;
	private static final int SELECT_CONVERSATION = 0;
	private static final int ADD_CONTACT = 1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent myIntent=getIntent();
		String action = myIntent.getAction();
	    if (Intent.ACTION_VIEW.equals(action)) {
	    	handleSendattachment(myIntent); 
	    }
	    
		setContentView(R.layout.activity_home_screen);
		
		// Create a new adapter for the ListView
		adapter = new HomeScreenAdapter(this);
		ListView lv = (ListView) findViewById(android.R.id.list);
		lv.setAdapter(adapter);
		
		// Handle ListView item clicks
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent myIntent = new Intent(view.getContext(), ConversationActivity.class);
				myIntent.putExtra("position", position);
				startActivityForResult(myIntent, SELECT_CONVERSATION);
			}
		});
		addListenersOnButtons();
	    
	}
	
	public void addListenersOnButtons() {
		// Add new contact button
		final ImageButton addContact = (ImageButton) findViewById(R.id.addContact);
		addContact.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(v.getContext(), AddContactActivity.class);
                startActivityForResult(myIntent, ADD_CONTACT);
            }
        });
		
		// Search button
		final ImageButton search = (ImageButton) findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// TODO: Properly implement the search for contact/conversation
            	Toast.makeText(getApplicationContext(), "Search to be added.", Toast.LENGTH_SHORT).show();  
            }
        });
		
		// Settings button
		final ImageButton settings = (ImageButton) findViewById(R.id.settings);
		settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	// TODO: Possibly add settings for the home screen?
            	Toast.makeText(getApplicationContext(), "Settings to be added.", Toast.LENGTH_SHORT).show();
            }
        });
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == ADD_CONTACT) {
			if (resultCode == RESULT_OK) {
				adapter.notifyDataSetChanged();
				Toast.makeText(getApplicationContext(), "New contact added.", Toast.LENGTH_SHORT).show();
			}
		}
		else if (requestCode == SELECT_CONVERSATION) {
				adapter.notifyDataSetChanged();
		}
	}
	
	// Do stuff with the attachment that was shared in.
	
	private void handleSendattachment(Intent myIntent) {
		Log.w("myApp", "in send attch");
		Uri u = myIntent.getData();
		File file = new File(getFilesDir(), u.getPath());
		String decryptedMessage ="";
		String name ="";
		String email ="";
		String encryptedText = "";
		if (!file.exists())
        {
			Log.w("myApp", "333");
            file.mkdirs();
        } 
		if (file != null) {
			try{
				InputStream is= this.getBaseContext().getContentResolver().openInputStream(u);
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				name = reader.readLine();
				email = reader.readLine();
				String line = reader.readLine();;
				while (line != null){
					encryptedText = encryptedText+ ""+line;
					line=reader.readLine();
				}
				reader.close();
				is.close();
			}catch(Exception e){
				Log.w("myApp", "error somethigns wrong");
			}
            Log.w("myApp", "2 "+encryptedText);
            Contact contact = new Contact(name, email);
            Time n = new Time();
            n.setToNow();	
            HomeScreenData homescreendata= HomeScreenData.getInstance();
            for (int k=0; k<homescreendata.conversations.size(); k++){
            	if(name.equals(homescreendata.conversations.get(k).getContact().getName())){
            		SecretKeySpec key = homescreendata.conversations.get(k).getKey();
            		decryptedMessage = Decrypt.decryptEmailAttachment(key, encryptedText.getBytes());
            		Message messageToAttach = new Message(contact, decryptedMessage, n);
            		homescreendata.conversations.get(k).addMessage(messageToAttach);
            		k = homescreendata.conversations.size();
            	}
            }
            Log.w("myApp", "end of handleattachment");
		}
	
	}
}
