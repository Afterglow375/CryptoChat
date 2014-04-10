package crypto;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;

import javax.crypto.spec.SecretKeySpec;

import android.content.Context;
import android.widget.TextView;

import com.example.crypto_app.R;

import frontend.AddContactActivity;

public class KeyCreator {
	public static final int DICT_SIZE = 4893;
	private static KeyCreator instance = null;
	private ArrayList<String> words = new ArrayList<String>(DICT_SIZE);
	private ArrayList<String> keywords = new ArrayList<String>(5);
	private SecureRandom sr = new SecureRandom();
	
	// Load the dictionary from memory into an ArrayList
	protected KeyCreator(Context context) {
		InputStream inputStream = context.getResources().openRawResource(R.raw.dictionary);
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
	
	// Singleton pattern to ensure only one instance
	public static KeyCreator getInstance(Context context) {
		if(instance == null){
			instance = new KeyCreator(context);
		}
		return instance;
	}
	
	// PRNG used to pick 5 random keywords from the dictionary
	public ArrayList<String> generateWords() {
		keywords.clear();
		keywords.add(words.get(sr.nextInt(DICT_SIZE-1)));
		keywords.add(words.get(sr.nextInt(DICT_SIZE-1)));
		keywords.add(words.get(sr.nextInt(DICT_SIZE-1)));
		keywords.add(words.get(sr.nextInt(DICT_SIZE-1)));
		keywords.add(words.get(sr.nextInt(DICT_SIZE-1)));
		return keywords;
	}
	
	// Creates a key from 10 keywords
	public SecretKeySpec createKey() {
		AddContactActivity.getEnteredKeywords();
		BigInteger key = BigInteger.valueOf(1);
		ArrayList<Integer> keywordIndices = new ArrayList<Integer>(10);
		keywords = AddContactActivity.getEnteredKeywords();
		for (int i = 9; i>=0; i--) {
			key = key.multiply(BigInteger.valueOf(keywordIndices.get(i)));
		}
		return new SecretKeySpec(key.toByteArray(), "AES");
	}
}
