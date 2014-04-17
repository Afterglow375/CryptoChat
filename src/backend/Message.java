package backend;
import android.text.format.Time;

public class Message {
	public Contact sender;
	public String text;
	public Time datetime;
	public Message(Contact c, String t, Time dt){
		this.sender = c;
		this.text = t;
		this.datetime = dt;
	}
	public String getMessage(){
		return this.text;
	}
	public String getDate(){
		return Integer.toString(this.datetime.month) + "\"" + Integer.toString(this.datetime.monthDay);
	}
	public String getTime(){
		String amOrPm = "AM";
		String hour = Integer.toString(this.datetime.hour);
		String minutes = Integer.toString(this.datetime.minute);
		if (this.datetime.hour > 12) {
			amOrPm = "PM";
			hour = Integer.toString(this.datetime.hour-12);
		}
		else if (this.datetime.hour == 0) {
			hour = "12";
		}
		if (this.datetime.minute < 10) {
			minutes = "0" + minutes;
		}
		return hour + ":" + minutes + " " + amOrPm;
	}
}
