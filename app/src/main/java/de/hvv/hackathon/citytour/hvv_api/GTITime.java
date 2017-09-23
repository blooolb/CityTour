package de.hvv.hackathon.citytour.hvv_api;

import org.json.JSONException;
import org.json.JSONObject;


public class GTITime {
	
    private String date;
    private String time;
	
	public GTITime(String date, String time)
	{
		this.date = date;
		this.time = time;
	}
	
	public String getDate() {
		return this.date;
	}
	
	public String getTime() {
		return this.time;
	}
	
	public JSONObject getBody() {
        try {
        	JSONObject body = new JSONObject();
            body.put("date", this.date);
            body.put("time", this.time);                   
            return body;
        } catch (JSONException e) {
            return null;
        }
    }

}
