package crypto;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;

import com.example.crypto_app.R;

public class KeyCreator {
	private static KeyCreator instance = null;
	private ArrayList<String> words = new ArrayList<String>(4937);
	private ArrayList<String> keywords = new ArrayList<String>(5);
	
	// Creating the dictionary in the app's memory
	protected KeyCreator(Context context) {
		InputStream inputStream = context.getResources().openRawResource(R.raw.dictionary4937);
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		try {
			String line = reader.readLine();
			while (line != null) {
				line = reader.readLine();
				words.add(line);
			}
			reader.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static KeyCreator getInstance(Context context) {
		if(instance == null){
			instance = new KeyCreator(context);
		}
		return instance;
	}
	
	public ArrayList<String> generateWords() {
		keywords.clear();
		keywords.add(words.get(1));
		keywords.add(words.get(2));
		keywords.add(words.get(3));
		keywords.add(words.get(4));
		keywords.add(words.get(5));
		return keywords;
	}
}
