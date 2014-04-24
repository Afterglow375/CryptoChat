package frontend;

import com.example.crypto_app.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import backend.ConversationData;
import backend.HomeScreenData;

public class HomeScreenAdapter extends ArrayAdapter<ConversationData> {
	private Context context;
	
	public HomeScreenAdapter(Context context) {
		super(context, R.layout.homescreen_list_item, HomeScreenData.getInstance().conversations);
		this.context = context;
	}
	
	// Populate the ListView with conversations
	@Override
	public View getView(int position, View convertView, ViewGroup parent) { 
		View myView = convertView;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			myView = (RelativeLayout) inflater.inflate(R.layout.homescreen_list_item, parent, false);
		}

		ConversationData conversation = (ConversationData) getItem(position);
			
		TextView name = (TextView) myView.findViewById(R.id.homescreen_item_name);
		TextView lastMessage = (TextView) myView.findViewById(R.id.homescreen_last_message);
		name.setText(conversation.contact.getName());
		lastMessage.setText(conversation.getLastMessage());
		
		return myView;
	}
}
