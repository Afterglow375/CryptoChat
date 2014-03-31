package backend;

import java.util.ArrayList;

public class ConversationData {
	public Contact contact;
	public ArrayList<Message> messages;
	public byte[] key;
	public ConversationData(byte[] k, Contact c){
		this.contact=c;
		this.key=k;
		this.messages = new ArrayList<Message>();
	}
	
	public void addMessage(Message message){
		messages.add(message);
	}
	public Message getLastMessage(){
		int i = this.messages.size();
		return this.messages.get(i-1);
	}
	
	public boolean isMessageEmpty(){
		return (this.messages.isEmpty());
	}
	public Contact getContact(){
		return this.contact;
	}
}
