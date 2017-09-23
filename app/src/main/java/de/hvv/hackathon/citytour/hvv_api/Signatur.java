// From http://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/
package de.hvv.hackathon.citytour.hvv_api;

import android.util.Base64;

import org.json.JSONObject;

import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Signatur {

	//Variablen
	String authUser;
	private String authKey;
	
	//Konstruktor	
	public Signatur (String authUser, String authKey)
	{
		this.authUser = authUser;
		this.authKey = authKey;
		
	}
	
	//Publik	
	public String generateSignature(JSONObject data) {
        Charset passwordEncoding = Charset.forName("UTF-8");
        String algorithm = "HmacSHA1";
        byte[] key = authKey.getBytes(passwordEncoding);
        SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
        try {
            Mac mac = Mac.getInstance(algorithm);
            mac.init(keySpec);
            byte[] signature = mac.doFinal(data.toString().getBytes());
            System.out.println(Base64.encodeToString(signature,Base64.NO_WRAP));
            return Base64.encodeToString(signature,Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }	

	}
}