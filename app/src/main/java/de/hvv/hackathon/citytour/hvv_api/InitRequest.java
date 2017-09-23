package de.hvv.hackathon.citytour.hvv_api;

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
            JSONObject body = new JSONObject();
            return body;
    }

}
