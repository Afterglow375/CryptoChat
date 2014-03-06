package com.example.cryptochat;
import java.util.ArrayList;

public class HomeScreenData {
	public ArrayList<ConversationData> conversations;
	private static HomeScreenData instance = null;
	
	protected HomeScreenData(){
		this.conversations = new ArrayList<ConversationData>();
	}
	public static HomeScreenData getInstance(){
		if(instance == null){
			instance = new HomeScreenData();
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
