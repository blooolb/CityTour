package de.hvv.hackathon.citytour.hvv_api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Anfrage {
	
	private Signatur Nutzer;
	private JSONObject body;
	private String StrBody;
	private String InitUri;
	private int responseCode = 0;
	private String responseBody;
	
	public Anfrage (Signatur Nutzer, JSONObject body, String InitUri)
	{
		this.Nutzer = Nutzer;
		this.body = body;
		this.StrBody = body.toString();
		this.InitUri = InitUri;
	}
	
	public void senden () throws Exception
	{
		String url = "https://api-hack.geofox.de/gti/public" + InitUri;
		URL obj = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
		
		try {
			con.setRequestMethod("POST");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
			con.setRequestProperty("geofox-auth-type", "HmacSHA1");
			con.setRequestProperty("geofox-auth-user", Nutzer.authUser);
			con.setRequestProperty("geofox-auth-signature", Nutzer.generateSignature(body/*new JSONObject()*/));
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(StrBody);
			wr.flush();
			wr.close();
			
			responseCode = con.getResponseCode();
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		
		responseBody = response.toString();
	}
	

	public int getResponseCode()
	{
		return responseCode;
	}
	
	public JSONObject getResponseBody()throws JSONException
	{
		return new JSONObject(responseBody);
	}
}
