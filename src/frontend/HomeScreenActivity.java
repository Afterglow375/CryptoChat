package frontend;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

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
}
