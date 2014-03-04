package com.example.cryptochat;
import java.util.ArrayList;



public class HomescreenData {
	public ArrayList<ConversationData> conversations;
	private static HomescreenData instance = null;
	
	protected HomescreenData(){
		this.conversations = new ArrayList<ConversationData>();
	}
	public static HomescreenData getInstance(){
		if(instance == null){
			instance = new HomescreenData();
		}
		return instance;
	}
	public void addConversation(ConversationData c){
		conversations.add(c);
	}
	public void removeConversation(int index){
		conversations.remove(index);
	}
	public ConversationData getConversation(int index){
		return conversations.get(index);
	}
	public boolean isConversationsEmpty(){
		return(conversations.size()==0);
	}
}
