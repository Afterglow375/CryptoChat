package frontend;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import backend.Contact;
import backend.ConversationData;
import backend.HomeScreenData;
import backend.Message;

import com.example.crypto_app.R;

public class HomeScreenActivity extends ListActivity {
	private HomeScreenAdapter adapter;
	private static final int SELECT_CONVERSATION = 0;
	private static final int ADD_CONTACT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		
		// Create a new adapter for the ListView
		adapter = new HomeScreenAdapter(this);
		ListView lv = (ListView) findViewById(android.R.id.list);
		lv.setAdapter(adapter);
		Intent myIntent=getIntent();
		String action = myIntent.getAction();
	    if (Intent.ACTION_SEND.equals(action)) {
	    	handleSendattachment(myIntent); 
	    }
	    else{
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
            	Toast.makeText(getApplicationContext(), "Search to be added.", Toast.LENGTH_SHORT).show();  // setTextFilterEnabled
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
	
		//Do stuff with the attachment that was shared in.
		private void handleSendattachment(Intent myIntent) {
			File file = new File(((Uri) myIntent.getParcelableExtra(Intent.EXTRA_STREAM)).getPath());
			String file_string ="";
			String name ="";
			String email ="";
		    if (file != null) {
		        try {
					InputStream is= openFileInput(file.getPath());
					byte[] attach = null;
					is.read(attach);
					is.close();
					boolean firstline = true;
					boolean secondline= true;
					for(int i = 0; i < attach.length; i++){
						if( firstline){
							if ((attach[i]) + attach[i+1]+"" =="/n"){
								firstline=false; 
							}
							name += (char)attach[i];
						}
						else if(secondline){
							if ((attach[i] + attach[i+1])+"" =="/n"){
									secondline=false;
							}
							email+=(char)attach[i];
						}
						else{
					     file_string += (char)attach[i];
						}
					}
					Contact contact = new Contact(name, email);
		    		Time n = new Time();
		    		n.setToNow();
		    		//TODO write decrypte method and decrypte file_string
		    		Message messageToAttach = new Message(contact, file_string, n);
		    		HomeScreenData homescreendata= HomeScreenData.getInstance();
		    		ConversationData conversation;
		    		for (int k=0; k<homescreendata.conversations.size(); k++){
		    			if(name == homescreendata.conversations.get(k).getContact().getName()){
		    				conversation = homescreendata.conversations.get(k);
		    				conversation.addMessage(messageToAttach);
		    				k = homescreendata.conversations.size();
		    			}
		    		}
		        }
		        catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		        catch (IOException e) {
					e.printStackTrace();
				} 
		    }
		    Contact contact = new Contact(name, email);
			Time n = new Time();
			n.setToNow();
			//TODO write decrypte method and decrypte file_string
			Message messageToAttach = new Message(contact, file_string, n);
			HomeScreenData homescreendata= HomeScreenData.getInstance();
			ConversationData conversation;
			for (int k=0; k<homescreendata.conversations.size(); k++){
				if(name.equals(homescreendata.conversations.get(k).getContact().getName())){
					conversation = homescreendata.conversations.get(k);
					conversation.addMessage(messageToAttach);
					k = homescreendata.conversations.size();
				}
			}
		}
	}


