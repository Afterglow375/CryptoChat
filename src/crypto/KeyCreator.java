package crypto;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Locale;
import javax.crypto.spec.SecretKeySpec;
import android.content.Context;
import com.example.crypto_app.R;
import frontend.AddContactActivity;


public class KeyCreator {
	public static final int DICT_SIZE = 4893;
	private static KeyCreator instance = null;
	private ArrayList<String> words = new ArrayList<String>(DICT_SIZE);
	private ArrayList<String> keywords = new ArrayList<String>(5);
	private ArrayList<Integer> createdKeywordIndices = new ArrayList<Integer>(5);
	private ArrayList<Integer> enteredKeywordIndices = new ArrayList<Integer>(5);
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
		createdKeywordIndices.clear();
		for (int i = 0; i < 5; i++) {
			int wordIndex = sr.nextInt(DICT_SIZE-1);
			keywords.add(words.get(wordIndex));
			createdKeywordIndices.add(wordIndex);
		}
		return keywords;
	}
	
	// Creates a key from 10 keywords
	public SecretKeySpec createKey() {
		keywords = AddContactActivity.getEnteredKeywords();
		ArrayList<Integer> organizedKeywordIndices = organizeKeywordIndices();
		
		// Compute the key, k = k*n + a
		BigInteger key = BigInteger.valueOf(0);
		for (int i = 0; i<10; i++) {
			key = key.multiply(BigInteger.valueOf(organizedKeywordIndices.get(i))).add(BigInteger.valueOf(createdKeywordIndices.get(i)));
		}
		return new SecretKeySpec(key.toByteArray(), "AES");
	}
	
	private ArrayList<Integer> organizeKeywordIndices() {
		enteredKeywordIndices.clear();
		
		for (int i = 0; i < 5; i++) {
			String keyword = keywords.get(i).toLowerCase(Locale.US).trim();
			enteredKeywordIndices.add(words.indexOf(keyword));
		}
	
		ArrayList<Integer> organizedKeywordIndices = new ArrayList<Integer>(10);
		for (int i = 0; i < 5; i++) {
			if (createdKeywordIndices.get(i) < enteredKeywordIndices.get(i)) {
				organizedKeywordIndices.addAll(createdKeywordIndices);
				organizedKeywordIndices.addAll(enteredKeywordIndices);
				return organizedKeywordIndices;
			}
			else if (createdKeywordIndices.get(i) > enteredKeywordIndices.get(i)) {
				organizedKeywordIndices.addAll(createdKeywordIndices);
				organizedKeywordIndices.addAll(enteredKeywordIndices);
				return organizedKeywordIndices;
			}
		}
		organizedKeywordIndices.addAll(createdKeywordIndices);
		organizedKeywordIndices.addAll(enteredKeywordIndices);
		return organizedKeywordIndices;
	}		
}
