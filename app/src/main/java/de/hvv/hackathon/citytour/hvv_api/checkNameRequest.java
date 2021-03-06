package de.hvv.hackathon.citytour.hvv_api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;


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
            switch (point.getMode())
            {
            case 1:
                body.put("theName", this.point.getCombinedNameAsBody());
                break;
            case 2:
                JSONObject coord = new JSONObject();
                coord.put("x",point.getCoordinateX());
                coord.put("y",point.getCoordinateY());
                body.put("coordinate",coord);
                break;
            }
            body.put("maxList", 5);
            body.put("tariffDetails", false);
            body.put("allowTypeSwitch", false);
            return body;
        } catch (JSONException e) {
            return null;
        }
    }

}
