package com.example.cryptochat;
import java.util.Calendar;



public class Message {
	Contact sender;
	String text;
	Calendar datetime;
	public Message(String t, Calendar dt){
		this.text = t;
		this.datetime = dt;
	}
	public String getMessage(){
		return this.text;
	}
	public Calendar getDate(){
		return this.datetime;
	}
	public Calendar getTime(){
		return this.datetime;
	}
}
