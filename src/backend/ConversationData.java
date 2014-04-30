package backend;

import java.util.ArrayList;

import javax.crypto.spec.SecretKeySpec;

public class ConversationData {
	public Contact contact;
	public ArrayList<Message> messages;
	public SecretKeySpec key;
	public ConversationData(SecretKeySpec k, Contact c){
		this.contact=c;
		this.key=k;
		this.messages = new ArrayList<Message>();
	}
	
	public void addMessage(Message message){
		messages.add(message);
	}
	public String getLastMessage() {
		if (messages.size() == 0) {
			return "No Messages";
		}
		else {
			return messages.get(messages.size()-1).getMessage();
		}
	}
	
	public boolean isMessageEmpty(){
		return (this.messages.isEmpty());
	}
	public Contact getContact(){
		return this.contact;
	}
}
