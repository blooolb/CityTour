package de.hvv.hackathon.citytour.hvv_api;

import java.util.Locale;

import javax.swing.JOptionPane;

import org.json.JSONException;
import org.json.JSONObject;

import hvv_api.BaseRequest;
import hvv_api.SDName;


public class checkNameRequest extends BaseRequest{
	
	private String ReqUri = "/checkName";
	private SDName point;
	  
	
	public checkNameRequest(SDName point, String filterType, Locale locale, int ApiVersion)
	{
		super(filterType, locale, ApiVersion);
		this.point = point;
	}
	
    public String getRequestUri() {
		return ReqUri;
	}
	
    public JSONObject getBody() {
        try {
            JSONObject body = super.getBody();
            body.put("theName", this.point.getCombinedNameAsBody());
            body.put("maxList", 5);
            body.put("tariffDetails", false);
            body.put("allowTypeSwitch", false);
            return body;
        } catch (JSONException e) {
            return null;
        }
    }

}
