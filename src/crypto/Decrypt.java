package crypto;

import javax.crypto.spec.SecretKeySpec;
import android.util.Log;


public class Decrypt{
	
	public static String decryptEmailAttachment(SecretKeySpec k, byte[] encryptedtext){
		Log.w("myApp", "in decryptEmailAttachment "+ new String(encryptedtext));
		Log.w("myApp", "end decryptEmailAttachment");
		return new String(encryptedtext);
	}
}
