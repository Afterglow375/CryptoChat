package frontend;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import backend.Contact;
import backend.ConversationData;
import backend.HomeScreenData;
import backend.Message;

import com.example.crypto_app.R;

import crypto.Encrypt;

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

            		FileOutputStream os;		//declare outputstreams
            		String filename = "encyptedMessage"; //set filename
            		try {
            		  Log.w("myApp", "221 "+filename);
            		  os = openFileOutput(filename, Context.MODE_WORLD_READABLE);
            		  os.write(conversation.getContact().getName().getBytes());
            		  os.write("\n".getBytes());
            		  os.write(conversation.getContact().getEmail().getBytes());
            		  os.write("\n".getBytes());
            		  Log.w("myApp", "pre encypt "+message.getText().toString());
            		  os.write(Encrypt.EncryptMessage(message.getText().toString().getBytes(), conversation.getKey()));//needs to be changed to encrypted message
            		  os.close();
            		} 
            		catch (Exception e) {
            		  e.printStackTrace();
            		}
            		File textfile = new File(getFilesDir(), filename);
            		if (!textfile.exists())
                    {
            			Log.w("myApp", "222");
                        textfile.mkdirs();
                    } 
                    
            		
            		Intent i = new Intent(Intent.ACTION_SEND);
            		i.setType("message/rfc822");
            		i.putExtra(Intent.EXTRA_EMAIL, new String[]{conversation.getContact().getEmail()});
            		i.putExtra(Intent.EXTRA_SUBJECT, "CryptoChat");
            		i.putExtra(Intent.EXTRA_TEXT, "Share the attachment with the cryptochat app"); // info about what to do when email is received
            		Uri uri;
            	    textfile.setReadable(true, false);
					textfile.setWritable(true, false);
            	    uri = Uri.fromFile(textfile);
            	    i.putExtra(Intent.EXTRA_STREAM, uri);

            		try{
            			startActivity(Intent.createChooser(i, "Send encrypted message..."));
            		}
            		catch(android.content.ActivityNotFoundException e){
            			Toast.makeText(getApplicationContext(), "No Email apps installed", Toast.LENGTH_SHORT).show();
            		}
            	}
            	setResult(RESULT_OK, new Intent());
        		finish();
            }
        });
		
		}
}
