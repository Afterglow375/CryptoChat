package crypto;

import javax.crypto.spec.SecretKeySpec;

import android.util.Log;

public class Encrypt {
	public static byte[] EncryptMessage(byte[] m, SecretKeySpec s){
		Log.w("myApp", "in encrypt"+m.toString());
		return m;
	}
}
