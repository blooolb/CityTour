package de.hvv.hackathon.citytour.hvv_api;

import org.json.JSONException;
import org.json.JSONObject;

public class InitRequest {
	
	private String ReqUri = "/init";
	
	public InitRequest()
	{
	}
	
    public String getRequestUri() {
		return ReqUri;
	}

	public JSONObject getBody() {
        try {
            JSONObject body = new JSONObject();
            return body;
        } catch (JSONException e) {
            return null;
        }
    }

}
